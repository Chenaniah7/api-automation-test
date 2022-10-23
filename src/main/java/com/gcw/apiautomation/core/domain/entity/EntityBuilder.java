package com.gcw.apiautomation.core.domain.entity;

import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.apiautomation.core.utility.Constants;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
public class EntityBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityBuilder.class.getName());

    private static String entity = null;

    private static String accountType = null;

    static {
        EntityBuilder.entity = System.getProperty(Constants.ENTITY);
        final String msg = String.format("Entity: %s",EntityBuilder.entity);
        EntityBuilder.LOGGER.info(msg);

        if (EntityBuilder.entity == null || "".equals(EntityBuilder.entity)){
            EntityBuilder.LOGGER.error("Entity name can't be null or empty",new IllegalArgumentException());
        }
    }

    private EntityBuilder(){}


    public static Entity buildEntity(){
        loadConfig(EntityBuilder.entity);
        return new Entity(EntityBuilder.entity);
    }

    private static void loadConfig(final String countryCode){
        ConfigProvider.config(countryCode);
    }
}
