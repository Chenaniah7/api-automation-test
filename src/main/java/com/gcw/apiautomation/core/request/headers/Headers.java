package com.gcw.apiautomation.core.request.headers;

import java.util.Map;

public class Headers  {

    private Map<String, Object> headersMap;

    public void setHeadersMap(final Map<String,Object> headersMap){
        this.headersMap = headersMap;
    }

    public Map<String, Object> getHeadersMap(){
        return  this.headersMap;
    }
}
