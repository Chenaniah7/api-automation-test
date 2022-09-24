package com.gcw.apiautomation.core.enums;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.utility.Constants;

public enum ApiResources {
    BASE_URI{
        @Override
        public String toString() {
            String baseUri = System.getProperty(Constants.FULL_HOST_URL);

            if (baseUri == null || "".equals(baseUri)){
                baseUri = System.getenv(Constants.FULL_HOST_URL);
            }

            if (baseUri == null || "".equals(baseUri)){
                baseUri = ConfigProvider.getConfig(ConfigKeys.API_BASE_URI.toString()).getString(ConfigKeys.DEFAULT.toString());
            }
            return baseUri;
        }
    },

    BASE_PATH{
        @Override
        public String toString() {
            final String entity =System.getProperty(Constants.ENTITY);
            return ConfigProvider.config(entity).getConfig(ConfigKeys.API_BASE_PATH.toString()).getString(ConfigKeys.DEFAULT.toString());
        }
    }
}
