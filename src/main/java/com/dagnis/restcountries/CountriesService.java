package com.dagnis.restcountries;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CountriesService {

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
        log.info("Getting countries from API");
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.com/v2/regionalbloc/eu";

        ResponseEntity<Country[]> response = restTemplate.getForEntity(uri, Country[].class);
        Country[] countries = response.getBody();
        return Arrays.asList(Objects.requireNonNull(countries));
    }

    private List<Country> getCountriesFromFile() throws IOException {
        log.info("Getting countries from FILE");
        ObjectMapper objectMapper = new ObjectMapper();
        Path filePath = Path.of(PATH_TO_SAVE_FILE);
        return Arrays.asList(objectMapper.readValue(filePath.toFile(), Country[].class));
    }
}
