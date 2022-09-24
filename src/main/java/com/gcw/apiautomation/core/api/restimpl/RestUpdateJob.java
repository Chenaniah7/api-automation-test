package com.gcw.apiautomation.core.api.restimpl;

import com.gcw.apiautomation.core.api.rest.AbstractRestJob;
import com.gcw.apiautomation.core.domain.entity.Entity;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.stream.Collectors;

public class RestUpdateJob extends AbstractRestJob{
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUpdateJob.class.getName());

    public void perform(Entity entity) {
        if (entity.getEndPoint() == null){
            final String msg = "POST api endpoint can't be null";
            RestUpdateJob.LOGGER.error(msg,new IllegalArgumentException());
            return;
        }

        final RequestSpecification httpRequest = RestAssured.given()
                .baseUri(entity.getBaseUri())
                .basePath(entity.getBasePath())
                .headers(entity.getResponseHeaders())
                .pathParams(entity.getPathParams())
                .queryParams(entity.getQueryParams())
                .cookies(entity.getCookies())
                .log()
                .all();

        if (StringUtils.isNoneBlank(entity.getResponsePayload())){
            httpRequest.body(entity.getRequestPayload());
        }


        final ValidatableResponse response = httpRequest.when().put(entity.getEndPoint()).then().log().all();




    }
}
