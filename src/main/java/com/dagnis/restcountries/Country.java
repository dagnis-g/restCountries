package com.dagnis.restcountries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private String name;
    private String capital;
    private List<Map<String, String>> currencies;
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
