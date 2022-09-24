package com.gcw.apiautomation.core.request.headers;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.enums.ConfigKeys;
import com.gcw.apiautomation.core.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class HeadersAssembler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadersAssembler.class.getName());

    private static final String TOKEN_WARNING_MSG = "Generation of Dynamic e2e trust token will not be attempted";

    private final Headers headers = new Headers();
    public HeadersAssembler(final String entity) {
        this.assembleHeaders(entity);
    }


    public Map<String, Object> getHeadersMap() {
        return this.headers.getHeadersMap();
    }

    public void setHeadersMap(final Map<String, Object> headersMap) {
        this.headers.setHeadersMap(headersMap);
    }

    private void assembleHeaders(final String entity) {
        Map<String, Object> headers = getDefaultHeaders();

        if (headers == null) {
            HeadersAssembler.LOGGER.info("Not found the default headers");
        }

        this.setHeadersMap(headers);

        final String msg = String.format("Headers map built: '%s'", this.headers.getHeadersMap());
        HeadersAssembler.LOGGER.info(msg);
    }


    private Map<String, Object> getDefaultHeaders() {
        Map<String, Object> headers = new HashMap<>();
        if (ConfigProvider.getConfig().hasPath(ConfigKeys.HEADERS.toString())) {
            headers = ConfigProvider.getConfig().getObject(ConfigKeys.HEADERS.toString()).unwrapped();
        }
        return headers;
    }


}
