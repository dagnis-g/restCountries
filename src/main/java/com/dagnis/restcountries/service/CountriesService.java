package com.dagnis.restcountries.service;

import com.dagnis.restcountries.model.Country;
import com.dagnis.restcountries.model.CountryEntity;
import com.dagnis.restcountries.model.Currency;
import com.dagnis.restcountries.model.CurrencyEntity;
import com.dagnis.restcountries.repository.CountryRepository;
import com.dagnis.restcountries.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService {

    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;

    @Value("${spring.sample.path-property}")
    String PATH_TO_SAVE_FILE;

    @Value("${spring.sample.source-property}")
    String SOURCE;

    public void writeCountriesListToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Country> countries = getAllEuCountriesFromApi();

        Path filePath = Path.of(PATH_TO_SAVE_FILE);
        objectMapper.writeValue(filePath.toFile(), countries);
    }

    public List<Country> getTop10ByPopulation() throws IOException {
        return switch (SOURCE) {
            case "file" -> getCountriesFromFile().stream()
                    .filter((Country c) -> c.getPopulation() != null)
                    .sorted(Comparator.comparing(Country::getPopulation).reversed())
                    .limit(10)
                    .toList();
            case "api" -> getAllEuCountriesFromApi().stream()
                    .filter((Country c) -> c.getPopulation() != null)
                    .sorted(Comparator.comparing(Country::getPopulation).reversed())
                    .limit(10)
                    .toList();
            default -> null;
        };

    }

    public List<Country> getTop10ByArea() throws IOException {
        return switch (SOURCE) {
            case "file" -> getCountriesFromFile().stream()
                    .filter((Country c) -> c.getArea() != null)
                    .sorted(Comparator.comparing(Country::getArea).reversed())
                    .limit(10)
                    .toList();
            case "api" -> getAllEuCountriesFromApi().stream()
                    .filter((Country c) -> c.getArea() != null)
                    .sorted(Comparator.comparing(Country::getArea).reversed())
                    .limit(10)
                    .toList();
            default -> null;
        };
    }

    public List<Country> getTop10ByPopulationDensity() throws IOException {
        return switch (SOURCE) {
            case "file" -> getCountriesFromFile().stream()
                    .filter((Country c) -> c.getPopulationDensity() != 0)
                    .sorted(Comparator.comparing(Country::getPopulationDensity).reversed())
                    .limit(10)
                    .toList();
            case "api" -> getAllEuCountriesFromApi().stream()
                    .filter((Country c) -> c.getPopulationDensity() != 0)
                    .sorted(Comparator.comparing(Country::getPopulationDensity).reversed())
                    .limit(10)
                    .toList();
            default -> null;
        };
    }

    private List<Country> getAllEuCountriesFromApi() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.com/v2/regionalbloc/eu";

        if (checkIfDataNotStale()) {
            log.info("Getting countries from DB");
            return countryRepository.findAll()
                    .stream()
                    .map(entity -> modelMapper.map(entity, Country.class))
                    .collect(Collectors.toList());
        } else {
            countryRepository.deleteAll();
            currencyRepository.deleteAll();

            ResponseEntity<Country[]> response = restTemplate.getForEntity(uri, Country[].class);
            Country[] countries = response.getBody();
            log.info("Getting countries from API");

            var countriesList = List.of(Objects.requireNonNull(countries));
            insertCountriesIntoDatabase(countriesList);
            return countriesList;
        }
    }
    
    private List<Country> getCountriesFromFile() throws IOException {
        log.info("Getting countries from FILE");
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Path.of(PATH_TO_SAVE_FILE);
        return Arrays.asList(objectMapper.readValue(filePath.toFile(), Country[].class));
    }

    @VisibleForTesting
    void insertCountriesIntoDatabase(List<Country> countries) {
        for (Country country : countries) {
            var countryEntity = modelMapper.map(country, CountryEntity.class);

            for (Currency currency : country.getCurrencies()) {
                Optional<CurrencyEntity> optionalCurrencyEntity = currencyRepository.findById(currency.getCode());

                CurrencyEntity currencyEntity;
                if (optionalCurrencyEntity.isEmpty()) {
                    currencyEntity = modelMapper.map(currency, CurrencyEntity.class);
                    currencyEntity = currencyRepository.save(currencyEntity);
                } else {
                    currencyEntity = optionalCurrencyEntity.get();
                }

                countryEntity.addCurrency(currencyEntity);
            }

            countryRepository.save(countryEntity);
        }
    }

    @VisibleForTesting
    boolean checkIfDataNotStale() {
        long DAY = 24 * 60 * 60 * 1000;
        Optional<CountryEntity> country = countryRepository.findFirstByOrderByCreatedAsc();
        return country.filter(value -> value.getCreated().getTime() > System.currentTimeMillis() - DAY).isPresent();
    }

}
