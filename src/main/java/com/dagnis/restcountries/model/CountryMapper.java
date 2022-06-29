package com.dagnis.restcountries.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country map(CountryEntity entity);
}
