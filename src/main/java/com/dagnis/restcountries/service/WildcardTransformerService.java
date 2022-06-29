package com.dagnis.restcountries.service;

import org.springframework.stereotype.Service;

@Service
public class WildcardTransformerService {

    public String transform(String wildcard) {
        StringBuilder transformedWildcard = new StringBuilder();
        char[] wildcardChars = wildcard.toCharArray();
        for (char i : wildcardChars) {
            if (i == '*') {
                transformedWildcard.append(".");
            }
            transformedWildcard.append(i);
        }
        return transformedWildcard.toString().toLowerCase();
    }
}
