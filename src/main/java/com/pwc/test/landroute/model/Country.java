package com.pwc.test.landroute.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Country {
    @JsonProperty("cca3")
    private String countryCode;

    @JsonProperty("borders")
    private List<String> countryBorders;
}