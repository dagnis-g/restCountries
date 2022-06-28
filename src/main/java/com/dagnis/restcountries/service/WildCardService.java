package com.dagnis.restcountries.service;

import com.dagnis.restcountries.model.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class WildCardService {

    private List<Country> getEuAllCountriesFromApi() {
        log.info("Getting countries from API");
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://restcountries.com/v2/regionalbloc/eu";

        ResponseEntity<Country[]> response = restTemplate.getForEntity(uri, Country[].class);
        Country[] countries = response.getBody();
        return Arrays.asList(Objects.requireNonNull(countries));
    }

    public List<Country> filterByWildcardPatter(String wildcard) {
        return getEuAllCountriesFromApi().stream()
                .filter((Country c) -> c.getName().matches(wildcard))
                .toList();
    }

}
