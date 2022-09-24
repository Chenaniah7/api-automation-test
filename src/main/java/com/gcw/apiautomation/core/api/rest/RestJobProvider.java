package com.gcw.apiautomation.core.api.rest;

import com.gcw.apiautomation.core.api.AbstractApiJobHelper;
import com.gcw.apiautomation.core.api.restimpl.RestCreateJob;
import com.gcw.apiautomation.core.api.restimpl.RestDeleteJob;
import com.gcw.apiautomation.core.api.restimpl.RestReadJob;
import com.gcw.apiautomation.core.api.restimpl.RestUpdateJob;
import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.enums.ConfigKeys;
import io.restassured.response.ValidatableResponse;

/**
 * 用来操作http请求，比如发送请求，获取响应码，获取相应数据等等
 */
public class RestJobProvider extends AbstractApiJobHelper {
    //POST
    public void postPayload(){
        this.setRestJob(new RestCreateJob());
        this.getRestJob().perform(this.getEntity());
    }

    //GET
    public void getResource(){
        this.setRestJob(new RestReadJob());
        this.getRestJob().perform(this.getEntity());
    }

    //PUT
    public void putPayload(){
        this.setRestJob(new RestUpdateJob());
        this.getRestJob().perform(this.getEntity());
    }

    //DELETE
    public void deleteResource(){
        this.setRestJob(new RestDeleteJob());
        this.getRestJob().perform(this.getEntity());
    }

    public String getResponseJson(){
        return this.getEntity().getResponsePayload();
    }

    public ValidatableResponse getValidatableResponse(){
        return this.getRestJob().getValidatableResponse();
    }

    public int getResourceCode(){
        return this.getEntity().getResponseCode();
    }

    public String getRequestBody(){
        return this.getEntity().getRequestPayload();
    }

    public void setRequestBody(final String requestBody){
        this.getEntity().setResponsePayload(requestBody);
    }

    public void setEndPoint(final String endpoint){
        if ('/' == endpoint.charAt(0)){
            this.getEntity().setEndPoint(endpoint);
        }else {
            this.getEntity().setEndPoint("/"+endpoint);
        }
    }

    public void switchBaseUri(final String key){
        final String baseUri = ConfigProvider.getConfig(ConfigKeys.API_BASE_URI.toString()).getString(key);
        this.getEntity().setBaseUri(baseUri);
    }

    public void setBaseUri(String uri){
        this.getEntity().setBaseUri(uri);
    }

    public void setBasePath(String path){
        this.getEntity().setBasePath(path);
    }

    public void switchBasePath(final String key){
        final String basePath = ConfigProvider.getConfig(ConfigKeys.API_BASE_PATH.toString()).getString(key);
        this.getEntity().setBasePath(basePath);
    }



}
