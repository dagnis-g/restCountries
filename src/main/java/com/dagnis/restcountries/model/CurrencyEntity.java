package com.dagnis.restcountries.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
@Data
public class CurrencyEntity {

    @Id
    private String code;
    private String name;
    private String symbol;

//    @ManyToMany(mappedBy = "currencies", cascade = CascadeType.MERGE)
//    private List<Country> countries;
}
