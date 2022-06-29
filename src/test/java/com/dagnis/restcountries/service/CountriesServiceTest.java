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
    void shouldAddCountryToDb() {
        Currency currency = new Currency("EUR", "Euro", "E");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("Austria", "Vienna", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        List<CountryEntity> countriesFound = countryRepository.findAll();
        Assertions.assertEquals(countriesFound.size(), 1);
    }

    @Test
    void shouldAddCurrencyToDb() {
        Currency currency = new Currency("GBP", "Pound", "G");
        List<Currency> currencies = new ArrayList<>();
        currencies.add(currency);
        Country country = new Country("UK", "London", currencies, 900000, 1234);

        List<Country> countries = new ArrayList<>();
        countries.add(country);
        countriesService.insertCountriesIntoDatabase(countries);

        List<CurrencyEntity> currenciesFound = currencyRepository.findAll();
        Assertions.assertEquals(currenciesFound.size(), 1);
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

}