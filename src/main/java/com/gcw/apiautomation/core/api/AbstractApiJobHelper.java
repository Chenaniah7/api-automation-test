package com.gcw.apiautomation.core.api;

import com.gcw.apiautomation.core.domain.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 封装一些用来组装请求的方法，如删除headers，添加请求参数，清理请求参数等等
 */
public class AbstractApiJobHelper extends ApiJob {
    private  static final Logger LOGGER = LoggerFactory.getLogger(AbstractApiJobHelper.class.getName());


    //清空全部headers
    public void clearHeaders(){
        Map<String,Object> emptyRequestHeaders =  new HashMap<>();
        this.getEntity().setRequestHeaders(emptyRequestHeaders);
    }

    //删除多个headers
    public void removeHeaders(final List<String> headersNames){
        final Entity entity = this.getEntity();
        final Iterator<String> iterator = headersNames.iterator();
        final Map<String,Object> requestHeaderMap = entity.getRequestHeaders();

        while (iterator.hasNext()){
            final String headerName = iterator.next();
            requestHeaderMap.remove(headerName);
        }
        entity.setRequestHeaders(requestHeaderMap);
    }

    //删除单个header
    public void removeHeader(final String headerName){
        this.getEntity().getRequestHeaders().remove(headerName);
    }


    //修改header的值
    public void updateHeader(final String header, final String value){
        this.getEntity().getRequestHeaders().put(header,value);
    }

    //修改多个headers的值
    public void updateHeaders(final Map<String,Object> headers){
        this.getEntity().getRequestHeaders().putAll(headers);
    }

    //删除全部请求参数
    public void clearQueryParams(){
        final Entity entity = this.getEntity();
        final Map<String,Object> emptyQueryParams =  new HashMap<>();
        entity.setQueryParams(emptyQueryParams);
    }

    //更新单个queryParam参数
    public void updateQueryParam(String key, Object value){
        this.getEntity().getQueryParams().put(key, value);
    }


     //更新多个queryParams
    public void updateQueryParams(Map<String,Object> queryParams){
        this.getEntity().getQueryParams().putAll(queryParams);
    }


    public void updatePathParam(String key, Object value){
        this.getEntity().getPathParams().put(key, value);
    }


    //删除单个路径参数
    public void removePathParam(final String pathParam){
        this.getEntity().getPathParams().remove(pathParam);
    }

    //清空全部路径参数
    public void cleanPathParams(){
        final Map<String,Object> pathParams = new HashMap<>();
        this.getEntity().setPathParams(pathParams);
    }


    //删除全部cookies
    public void clearCookies(){
        final Entity entity = this.getEntity();
        final Map<String,Object> emptyCookies =  new HashMap<>();
        entity.setCookies(emptyCookies);

        final String msg = String.format("Delete request headers: updated headers map is %s",entity.getCookies());
        AbstractApiJobHelper.LOGGER.info(msg);
    }




}
