package com.dagnis.restcountries.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country map(CountryEntity entity);

    @Mapping(source = "currencies", target = "currencies", ignore = true)
    CountryEntity map(Country country);
}
