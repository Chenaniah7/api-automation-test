package com.gcw.apiautomation.core.api.rest;

import com.gcw.apiautomation.core.domain.entity.Entity;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class AbstractRestJob {

    private ValidatableResponse validatableResponse;

    public abstract void perform(Entity entity);

    public ValidatableResponse getValidatableResponse() {
        return validatableResponse;
    }

    public void setValidatableResponse(ValidatableResponse validatableResponse) {
        this.validatableResponse = validatableResponse;
    }
}
