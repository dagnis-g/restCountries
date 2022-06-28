package com.dagnis.restcountries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Country extends JpaRepository<Country, Integer> {

}
