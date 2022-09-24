package com.gcw.testautomation.support.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhoneAddress {

    private String province;

    private String city;

    @JsonProperty("areacode")
    private String areaCode;

    private String zip;

    private String company;

    private String card;

}
