package com.dagnis.restcountries.repository;

import com.dagnis.restcountries.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {
}
