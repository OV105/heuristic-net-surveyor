/********************************************************************\
 * Copyright (c) 2017 Timothy H. Lee
 * All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License v3.0
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED. See the GNU General Public License v3.0 for
 * more details.
 \********************************************************************/
package com.github.ov105.netsurveyor;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract parent for classes that produce Packages which are sent to
 * Observer classes.
 */
public abstract class Source {
    private List<Observer> observers;
    protected Logger logger;

    public Source() {
        logger = LoggerFactory.getLogger(this.getClass());
        observers = new ArrayList<Observer>();
    }

    /**
     * Add class that implements the Observer interface
     * @param obs
     */
    public void addObserver ( Observer obs ) {
        logger.info("Adding observer: %s", obs);
        observers.add(obs);
    }

    /**
     * Remove class that implements the Observer interface
     * @param obs
     */
    public void removeObserver ( Observer obs ) {
        logger.info("Removing observer: %s", obs);
        if ( ! observers.remove(obs) ) {
            logger.warn("Observer not registered %s.", obs);
        }
    }

    /**
     * Send object that implements Carton to all
     * registered Observers
     * @param item
     */
    public void send ( Carton item ) {
        for (Observer observer :  observers) {
            logger.trace("Conveying item %s", item);
            observer.convey ( item );
        }
    }

    /**
     * Abstract method for class specific initialization.
     * Should contain or call main event loop.
     */
    public abstract void start();
}
