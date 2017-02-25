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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by tlee on 2/25/17.
 */
public class AgentConfigTest extends TestCase {

    public void testConstructor() throws Exception {
        AgentConfig config = new AgentConfig();
        assertThat(config, instanceOf(AgentConfig.class));
    }

    public void testNicAddress() throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext ( AgentConfig.class );
        AgentConfig config = ctx.getBean ( AgentConfig.class );
        assertEquals("127.0.0.1", config.getNicAddress());
        System.setProperty ( "agent.properties.filename", "unittest.properties");
        ctx = new AnnotationConfigApplicationContext ( AgentConfig.class );
        config = ctx.getBean ( AgentConfig.class );
        assertEquals("127.0.0.2", config.getNicAddress());
    }

    public void testPcap4jPcapSource() throws Exception {
        System.setProperty ( "agent.properties.filename", "unittest-single.properties");
        ApplicationContext ctx = new AnnotationConfigApplicationContext ( AgentConfig.class );
        Pcap4jPcapSource source = (Pcap4jPcapSource) ctx.getBean ( "pca4jFileSourceBean" );
        assertThat ( source, instanceOf ( Pcap4jPcapSource.class ));
        assertEquals ( "src/test/resources/dns-query-single.pcap", source.getPcapPath().toString() );
    }
}
