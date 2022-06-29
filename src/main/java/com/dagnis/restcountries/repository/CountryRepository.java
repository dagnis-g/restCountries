package com.dagnis.restcountries.repository;

import com.dagnis.restcountries.model.Country;
import com.dagnis.restcountries.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

    Optional<CountryEntity> findFirstByOrderByCreatedAsc();
}
