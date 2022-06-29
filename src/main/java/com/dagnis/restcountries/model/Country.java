package com.dagnis.restcountries.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String capital;
    //    @OneToMany(mappedBy = "country")
    //    private List<Currency> currencies;

    @ManyToMany
    @JoinTable(
            name = "country_currency",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "code")
    )
    private List<Currency> currencies;

    private Integer population;
    private Integer area;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @PrePersist
    private void onCreate() {
        created = new Date();
    }

    public Integer getPopulationDensity() {
        if (population != null && area != null) {
            return population / area;
        } else {
            return 0;
        }
    }
}
