package com.dagnis.restcountries;

import com.dagnis.restcountries.service.WildcardTransformerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestCountriesApplicationTests {

    @Autowired
    WildcardTransformerService wildcardTransformerService;

    @Test
    void transformShouldAddPointBeforeStarAndLowercaseIt() {

        String userEnteredWildcard = "s*N";
        String transformedWildcard = wildcardTransformerService.transform(userEnteredWildcard);
        String expectedWildcard = "s.*n";

        Assertions.assertEquals(expectedWildcard, transformedWildcard);
    }


}
