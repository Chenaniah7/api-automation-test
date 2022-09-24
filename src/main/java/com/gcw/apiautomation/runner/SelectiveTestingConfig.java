package com.gcw.apiautomation.runner;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.enums.ConfigKeys;
import com.gcw.apiautomation.core.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SelectiveTestingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectiveTestingConfig.class.getName());

    private SelectiveTestingConfig(){}

    static String getReleaseReason(){
        String releaseReasonMissingMsg = String.format("Release reason must not be null in config file, it must be one of these: %s", Constants.RELEASE_REASON_LIST);
        String releaseReason = Optional.ofNullable(ConfigProvider.getConfig().getString("release-reason")).map(Objects::toString).orElseThrow(() ->
        {return new IllegalArgumentException(releaseReasonMissingMsg);});

        if (!Constants.RELEASE_REASON_LIST.contains(releaseReason)){
            throw new IllegalArgumentException("The release reason is now supported, allowed release reason value are "+Constants.RELEASE_REASON_LIST);
        }

        LOGGER.info("Release reason is set to: " + releaseReason);
        String a = "sds";
        return releaseReason;
    }
}
