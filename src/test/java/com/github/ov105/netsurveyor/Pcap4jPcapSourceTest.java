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

import junit.framework.TestCase;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Class to test Pcap4jPcapSource
 */
public class Pcap4jPcapSourceTest extends TestCase {

    public void testConstructor() throws Exception {
        URL pcapUrl = this.getClass().getResource("dns-query-single.pcap");
        File pcapFile = new File( pcapUrl.toURI() );
        Pcap4jPcapSource source = new Pcap4jPcapSource ( pcapFile.toString() );
        assertThat ( source, instanceOf ( Pcap4jPcapSource.class ));
    }

    public void testSingleDNS() throws Exception {
        URL pcapUrl = this.getClass().getResource("dns-query-single.pcap");
        File pcapFile = new File( pcapUrl.toURI() );
        Pcap4jPcapSource source = new Pcap4jPcapSource ( pcapFile.toString() );
        ObserverMock obs = new ObserverMock();
        source.addObserver ( obs );
        source.start();
        assertEquals ( 1, obs.cartons.size() );
    }

    public void testSingleSix() throws Exception {
        URL pcapUrl = this.getClass().getResource("dns-six.pcap");
        File pcapFile = new File( pcapUrl.toURI() );
        Pcap4jPcapSource source = new Pcap4jPcapSource ( pcapFile.toString() );
        ObserverMock obs = new ObserverMock();
        source.addObserver ( obs );
        source.start();
        assertEquals ( 6, obs.cartons.size() );
    }
}
