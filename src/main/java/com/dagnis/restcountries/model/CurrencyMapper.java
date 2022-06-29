package com.dagnis.restcountries.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyEntity map(Currency currency);
}
