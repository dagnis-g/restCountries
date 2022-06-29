package com.dagnis.restcountries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency")
public class Currency {

    @Id
    private String code;
    private String name;
    private String symbol;
    //    @ManyToOne
    //    @JoinColumn(name = "country_id", nullable = false)
    //    private Country country;

    @ManyToMany(mappedBy = "currencies", cascade = CascadeType.MERGE)
    private List<Country> countries;

}


