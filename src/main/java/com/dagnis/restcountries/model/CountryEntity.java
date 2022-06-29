package com.dagnis.restcountries.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String capital;

    @ManyToMany
    @JoinTable(
            name = "country_currency",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "code")
    )
    private Set<CurrencyEntity> currencies = new HashSet<>();

    private Integer population;
    private Integer area;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @PrePersist
    private void onCreate() {
        created = new Date();
    }

    public void addCurrency(CurrencyEntity currencyEntity) {
        this.currencies.add(currencyEntity);
    }
}
