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

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.util.NifSelector;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
//import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 *  Class to read events from live NIC
 */
public class Pcap4jLiveSource extends Source {
    private String nicName = null;
    private InetAddress nicAddress = null;
    private int snapLen; //bytes
    private int readTimeout; //ms
    private int maxCount;
    private String filter;
    private PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
    private PcapNetworkInterface pcap4jDevice;

    public Pcap4jLiveSource() {
        super();
        filter = "";
        snapLen = 65536;
        readTimeout = 10;
        maxCount = -1; //never exit loop
    }

    /**
     * Constructor with named NIC to read from
     * @param nicName
     */
    public Pcap4jLiveSource ( String nicName ) {
        this();
        this.nicName = nicName;
    }

    /**
     * Constructor with IP address of NIC to read from
     * @param nicAddress
     */
    public Pcap4jLiveSource ( InetAddress nicAddress ) {
        this();
        this.nicAddress = nicAddress;
    }

    /**
     * Set pcap4jDevice based on either nicName or nicAddress, or first available NIC if neither is set.
     * @throws RuntimeException
     */
    void configureDevice() throws RuntimeException {
        pcap4jDevice = null;
        try {
            if (nicName != null) {
                logger.info(String.format("Attempting to get pcap device for: %s", nicName));
                pcap4jDevice = Pcaps.getDevByName(nicName);
            } else if (nicAddress != null) {
                logger.info(String.format("Attempting to get pcap device for: %s", nicAddress));
                pcap4jDevice = Pcaps.getDevByAddress ( nicAddress );
            } else {
                logger.info(String.format("nicName & nicAddress not set attempting selectNetworkInterface"));
                pcap4jDevice = new NifSelector().selectNetworkInterface();
            }
        }
        catch (PcapNativeException|IOException ex) {
            String errMsg = "Failed to created pcap device, error: " + ex.getMessage();
            logger.error ( errMsg );
            throw new RuntimeException ( errMsg );
        }
        if (pcap4jDevice == null) {
            String errMsg = "pcap4jDevice is null";
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    @Override
    public void start() throws RuntimeException {
        configureDevice();
        PcapHandle handle = null;
        try {
            handle = pcap4jDevice.openLive(snapLen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);
            logger.info ( String.format ( "open live device: %s", pcap4jDevice.getDescription() ) );
            handle.setFilter( filter, BpfProgram.BpfCompileMode.OPTIMIZE );
        } catch (PcapNativeException|NotOpenException  ex) {
            String errMsg = String.format ( "Failed to open device %s, error: %s", pcap4jDevice.getDescription(), ex.getMessage());
            logger.error ( errMsg );
            throw new RuntimeException ( errMsg );
        }


        int num = 0;
        while (true) {
          try {
              Packet packet = handle.getNextPacketEx();
              Pcap4JCarton carton = new Pcap4JCarton(packet);
              send ( carton );
              if (maxCount > 0) {
                  num++;
                  if (num >= maxCount){
                      logger.info ( String.format( "Ending loop reached maxCount %s", maxCount ) );
                      break;
                  }
              }
          }
          catch (TimeoutException e) {
              logger.trace ( "Timedout");
          }
          catch (EOFException|PcapNativeException|NotOpenException ex) {
              String errMsg = "Error reading packets: " + ex.getMessage();
              logger.error ( errMsg );
              throw new RuntimeException ( errMsg );
          }
        }

        handle.close();
    }

    /**
     * snapLen getter
     * @return
     */
    public int getSnapLen() {
        return snapLen;
    }

    /**
     * snapLen setter
     * @param snapLen
     */
    public void setSnapLen(int snapLen) {
        logger.info ( String.format ( "Setting snapLen to: %s", snapLen));
        this.snapLen = snapLen;
    }

    /**
     * readTimeout getter
     * @return
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * readTimeout setter
     * @param readTimeout
     */
    public void setReadTimeout(int readTimeout) {
        logger.info ( String.format ( "Setting readTimeout to: %s", readTimeout));
        this.readTimeout = readTimeout;
    }

    /**
     * maxCount getter
     * @return
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * maxCount setter
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        logger.info ( String.format ( "Setting maxCount to: %s", maxCount));
        this.maxCount = maxCount;
    }

    /**
     * filter getter
     * @return
     */
    public String getFilter() {
        return filter;
    }

    /**
     * filter setter
     * @param filter
     */
    public void setFilter(String filter) {
        logger.info ( String.format ( "Setting filter to: %s", filter));
        this.filter = filter;
    }
}
