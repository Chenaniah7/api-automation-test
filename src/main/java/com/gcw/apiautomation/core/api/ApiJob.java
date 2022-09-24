package com.gcw.apiautomation.core.api;

import com.gcw.apiautomation.core.api.rest.AbstractRestJob;
import com.gcw.apiautomation.core.domain.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiJob.class.getName());

    private Entity entity;

    private AbstractRestJob restJob = null;



    public Entity getEntity() {
        return this.entity;
    }

    public Entity setEntity(final Entity entity){
        if (entity == null){
            final String msg = "Entity can't be null";
            ApiJob.LOGGER.error(msg, new IllegalArgumentException());
        }
        return this.entity = entity;
    }


    public AbstractRestJob getRestJob() {
        return restJob;
    }

    public void setRestJob(AbstractRestJob restJob) {
        this.restJob = restJob;
    }
}
