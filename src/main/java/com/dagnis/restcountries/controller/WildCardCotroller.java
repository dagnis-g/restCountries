package com.dagnis.restcountries.controller;

import com.dagnis.restcountries.model.Country;
import com.dagnis.restcountries.service.WildCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WildCardCotroller {

    private final WildCardService wildCardService;

    public WildCardCotroller(WildCardService wildCardService) {
        this.wildCardService = wildCardService;
    }

    @GetMapping("/wild-card")
    public List<Country> getWildCardCountries(@RequestParam String wildcard) {
        return wildCardService.filterByWildcardPatter(wildcard);
    }
}
