package com.dagnis.restcountries.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    @Id
    @Column(name = "country_id")
    private Integer id;

    private String name;

    private String capital;
    
    private List<Currency> currencies;
    private Integer population;
    private Integer area;

    public Integer getPopulationDensity() {
        if (population != null && area != null) {
            return population / area;
        } else {
            return 0;
        }
    }
}
