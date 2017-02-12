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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.EOFException;
import java.util.concurrent.TimeoutException;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapHandle.TimestampPrecision;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

/**
 *  Class to create Carton objects from packets in a pcap file.
 */
public class Pcap4jPcapSource extends Source {
    Path pcapPath;
    Logger logger;

    /**
     * constructor
     * @param pcapPathString - path to pcap file
     */
    public Pcap4jPcapSource ( String pcapPathString ) {
        logger = LoggerFactory.getLogger ( this.getClass() );
        this.pcapPath = Paths.get ( pcapPathString );
        if ( ! Files.isRegularFile ( this.pcapPath ) ) {
            String errMsg = String.format ( "%s is not a regular file.", pcapPathString );
            logger.error ( errMsg );
            throw new RuntimeException ( errMsg );
        }
    }

    /**
     * Open and process packets in pcap file located at pcapPath.
     */
    public void start() {
        PcapHandle handle;
        int MAX_COUNT = 10;
        try {
          handle = Pcaps.openOffline ( pcapPath.toAbsolutePath().toString(), TimestampPrecision.NANO);
        } catch (PcapNativeException e) {
            String errMsg = String.format ( "Error with pcap file: %s, %s", pcapPath.toAbsolutePath().toString(), e.getMessage() );
            logger.error ( errMsg );
            throw new RuntimeException ( errMsg );
        }

        for ( int i=0; i < MAX_COUNT; i++ ) {
            try {
                Packet packet = handle.getNextPacketEx();
                Pcap4JCarton carton = new Pcap4JCarton(packet);
                send ( carton );
            }
            catch (TimeoutException e) {
                logger.warn("file should never timeout");
            }
            catch (EOFException e) {
                logger.trace ( String.format ( "got EOF at count: %s", i ));
                break;
            } catch (PcapNativeException | NotOpenException e) {
                String errMsg = String.format("Pcap error: '%s', for file: %s", e.getMessage(), pcapPath.toAbsolutePath().toString());
                logger.error(errMsg);
                throw new RuntimeException(errMsg);
            }
        }
    }
}
