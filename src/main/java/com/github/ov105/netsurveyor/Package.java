/******************************************************************\
 * Copyright (c) 2017 Timothy H. Lee
 * All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License v3.0
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 \*****************************************************************/
package com.github.ov105.netsurveyor;
import java.util.UUID;
import java.time.Instant;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.bson.Document;

/**
 *  Class to encapsulate data for processing by class
 *  that implements Observer
 */
public abstract class Package<T> {
    private UUID uuid;
    private T data;
    private Instant timestamp;
    protected Logger logger;

    public Package() {
        logger = LoggerFactory.getLogger ( Package.class );
        timestamp = Instant.now();
        setUuid( UUID.randomUUID() );
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        logger.trace ( "Setting uuid to: {}", uuid);
        this.uuid = uuid;
    }

    public T getData() {
        return data;
    }

    public void setData ( T data ) {
        this.data = data;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Long getTimestampMilli() {
        return timestamp.toEpochMilli();
    }

    public abstract Document getDocument();

}
