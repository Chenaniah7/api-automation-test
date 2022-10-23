package com.gcw.apiautomation.core.config;


import com.gcw.apiautomation.core.enums.ConfigKeys;
import com.gcw.apiautomation.core.utility.Constants;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class ConfigProvider {

    public static final String CONFIG_BASE_PATH="src/test/resources/config/";
    public static final String CONFIG_FILE_NAME_PREFIX="application-";
    public static final String CONFIG_FILE_EXTENSION=".conf";

    public static final String DEFAULT_CONFIG_FILE_NAME=ConfigProvider.CONFIG_BASE_PATH+"application.conf";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigProvider.class.getName());

    private static Config conf;
    private static final Object lock2 = new Object();

    private ConfigProvider(){};

    public static  synchronized Config config(final String entity){
        if (entity == null || "".equals(entity)){
            ConfigProvider.LOGGER.error("Entity name can't be null or empty", new IllegalArgumentException());
            return null;
        }

        final File defaultConfigFile =  new File(ConfigProvider.DEFAULT_CONFIG_FILE_NAME);
        final Config defaultConfig = ConfigFactory.parseFile(defaultConfigFile);

        final String otherConfigFileName = ConfigProvider.CONFIG_BASE_PATH+ConfigProvider.CONFIG_FILE_NAME_PREFIX
                +entity+ConfigProvider.CONFIG_FILE_EXTENSION;
        final String msg =String.format("Config file to be loaded: '%s",otherConfigFileName);
        ConfigProvider.LOGGER.info(msg);

        final File otherConfigFile =new File(otherConfigFileName);
        if (!otherConfigFile.exists()){
            throw new IllegalArgumentException(String.format("Config file %s not found for entity %s",otherConfigFile,entity));
        }

        //make a config with other config file
        final Config otherConfig =ConfigFactory.parseFile(otherConfigFile);

        //override default stack with other config
        final Config combinedConfig = otherConfig.withFallback(defaultConfig);
        ConfigProvider.conf = ConfigFactory.load(combinedConfig);

//        final Config envConfig = ConfigProvider.conf.getObject(System.getProperty(Constants.ENV)).toConfig();
//        ConfigProvider.conf = envConfig.withFallback(ConfigProvider.conf);

        return ConfigProvider.conf;
    }

    public static Config getConfig(){
            if (ConfigProvider.conf == null){
                return null;
            }
            return ConfigProvider.conf;

    }

    public static Config getConfig(final String key){
        return ConfigProvider.conf.getConfig(key);
    }



    public static String getPayloadPath(final String fileName){
        final Config relativePaths = ConfigProvider.conf.getConfig(ConfigKeys.RELATIVE_PATHS.toString());
        String relativePathToPayload = null;

        if (relativePaths == null || fileName == null){
            ConfigProvider.LOGGER.error("relative paths to payload not found in config");
            ConfigProvider.LOGGER.info("E.g. application.conf should have ‘relative-paths.payload = src/test/resource/payload'");
        }else {
            relativePathToPayload = relativePaths.getString(ConfigKeys.PAYLOAD.toString());
            relativePathToPayload = relativePathToPayload.concat(fileName);
        }

        return relativePathToPayload;
    }

    public static String getSchemaPath(final String schemaFileName){
        final Config relativePaths = ConfigProvider.conf.getConfig(ConfigKeys.RELATIVE_PATHS.toString());
        String relativePathToSchema = null;

        if (relativePaths == null || schemaFileName == null){
            ConfigProvider.LOGGER.error("relative paths to schema not found in config");
            ConfigProvider.LOGGER.info("E.g. application.conf should have ‘relative-paths.schemas = src/test/resource/schemas'");
        }else {
            relativePathToSchema = relativePaths.getString(ConfigKeys.SCHEMAS.toString());
            relativePathToSchema = relativePathToSchema.concat(schemaFileName);
        }

        return relativePathToSchema;
    }

}
