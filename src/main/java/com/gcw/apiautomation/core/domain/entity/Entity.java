package com.gcw.apiautomation.core.domain.entity;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.enums.ApiResources;
import com.gcw.apiautomation.core.enums.ConfigKeys;
import com.gcw.apiautomation.core.request.headers.HeadersAssembler;
import com.typesafe.config.Config;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Data
public class Entity {

    private static final Logger LOGGER = LoggerFactory.getLogger(Entity.class.getName());

    private String baseUri = "http://localhost";

    private String basePath = "";

    private String endPoint = "";

    private Map<String, Object>requestHeaders = new HashMap<>();

    private Map<String, Object>pathParams = new HashMap<>();

    private Map<String, Object>queryParams = new HashMap<>();

    private Map<String, Object>formParams = new HashMap<>();

    private Map<String, Object>cookies = new HashMap<>();

    private String requestPayload;

    private String responsePayload;

    private int responseCode;

    private Map<String,String> responseCookies;

    private Map<String,Object> responseHeaders;


    public Entity(){}

    public Entity(final String entity){
        this.setBaseUri(ApiResources.BASE_URI.toString());
        this.setBasePath(ApiResources.BASE_PATH.toString());
        this.buildHeadersMap(entity);
    }

    private void buildHeadersMap(final String entity){
        if (entity == null){
            Entity.LOGGER.error("Entity name can't be null ", new IllegalArgumentException());
            return;
        }

        if (this.isBuildHeaders()) {
            final HeadersAssembler headersAssembler = new HeadersAssembler(entity);
            this.requestHeaders = headersAssembler.getHeadersMap();
        }
    }


    private boolean isBuildHeaders(){
        /*
        判断config文件里是否有设置通用的headers
         */
        final String headers = ConfigKeys.HEADERS.toString();
        final Config conf = ConfigProvider.getConfig();
        boolean buildHeaders = false;

        if (conf.hasPath(headers)){
            buildHeaders = true;
        }


        if (!buildHeaders){
            Entity.LOGGER.info("no headers config found for static request headers!!!");
            Entity.LOGGER.info("no token-type config found!");
            Entity.LOGGER.info("default empty request headers '{}' will be used");
        }
        return buildHeaders;
    }
}
