package com.dagnis.restcountries.controller;

import com.dagnis.restcountries.model.Country;
import com.dagnis.restcountries.service.CountriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class CountriesController {
    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/write")
    public void writeCountriesToFile() throws IOException {
        countriesService.writeCountriesListToFile();
    }

    @GetMapping("/top-population")
    public List<Country> getTop10Population() throws IOException {
        return countriesService.getTop10ByPopulation();
    }

    @GetMapping("/top-area")
    public List<Country> getTop10Area() throws IOException {
        return countriesService.getTop10ByArea();
    }

    @GetMapping("/top-density")
    public List<Country> getTop10PopulationDensity() throws IOException {
        return countriesService.getTop10ByPopulationDensity();
    }

}
