package com.dagnis.restcountries.service;

import com.dagnis.restcountries.model.Country;
import com.dagnis.restcountries.model.CountryEntity;
import com.dagnis.restcountries.model.Currency;
import com.dagnis.restcountries.model.CurrencyEntity;
import com.dagnis.restcountries.repository.CountryRepository;
import com.dagnis.restcountries.repository.CurrencyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class CountriesServiceTest {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CountriesService countriesService;

    @Autowired
    CurrencyRepository currencyRepository;

    @AfterEach
    public void clearDatabase() {
        countryRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    @Test
    void shouldAddCountryAndCurrencyToDb() {
        String code = "EUR";
        String currencyName = "Euro";
        String symbol = "E";
        Currency currency = new Currency(code, currencyName, symbol);
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);

        String countryName = "Austria";
        String capital = "Vienna";
        Integer population = 900000;
        Integer area = 1234;

        Country country = new Country(countryName, capital, currencies, population, area);
        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);


        List<CurrencyEntity> currenciesFound = currencyRepository.findAll();
        Assertions.assertEquals(currenciesFound.size(), 1);
        CurrencyEntity currencyFound = currenciesFound.get(0);
        Assertions.assertEquals(code, currencyFound.getCode());
        Assertions.assertEquals(currencyName, currencyFound.getName());
        Assertions.assertEquals(symbol, currencyFound.getSymbol());

        List<CountryEntity> countriesFound = countryRepository.findAll();
        CountryEntity countryFound = countriesFound.get(0);
        Assertions.assertEquals(countryName, countryFound.getName());
        Assertions.assertEquals(capital, countryFound.getCapital());
        Assertions.assertEquals(population, countryFound.getPopulation());
        Assertions.assertEquals(area, countryFound.getArea());
        Assertions.assertNotNull(countryFound.getId());
        Assertions.assertNotNull(countryFound.getCreated());
    }

    @Test
    void shouldBeFalseIfDataOlderThan24h() {
        long DAY = 24 * 60 * 60 * 1000;

        Currency currency = new Currency("GBP", "Pound", "G");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("UK", "London", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        List<CountryEntity> countryEntities = countryRepository.findAll();
        CountryEntity countryEntity = countryEntities.get(0);
        countryEntity.setCreated(new Date(System.currentTimeMillis() - DAY * 2));
        countryRepository.save(countryEntity);

        Assertions.assertFalse(countriesService.checkIfDataNotStale());
    }

    @Test
    void shouldBeTrueIfDataNewerThan24h() {

        Currency currency = new Currency("GBP", "Pound", "G");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("UK", "London", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        Assertions.assertTrue(countriesService.checkIfDataNotStale());
    }

    @Test
    void shouldNotThrowOnDeletingFromDatabase() {
        Currency currency = new Currency("EUR", "Euro", "E");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("Austria", "Vienna", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        countryRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    @Test
    void countryShouldHaveCurrency() {
        Currency currency = new Currency("EUR", "Euro", "E");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("Austria", "Vienna", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        List<CountryEntity> countryEntities = countryRepository.findAll();
        CountryEntity countryEntity = countryEntities.get(0);
        Assertions.assertEquals(countryEntity.getCurrencies().size(), 1);

    }

}