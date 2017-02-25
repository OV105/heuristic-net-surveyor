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

import com.sun.javafx.binding.Logging;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class to configure net surveyor using Spring
 */

@Configuration
@PropertySource ( "classpath:${agent.properties.filename:agent.properties}")
public class AgentConfig {
    public static final String nicAddressKey = "nic.address";
    public static final String nicNameKey = "nic.name";
    public static final String pcapFilepathKey = "pcap.file.path";

    private Logger logger;

	@Autowired
	private Environment env;

	public AgentConfig() {
	    super();
	    logger = LoggerFactory.getLogger ( this.getClass() );
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name="pca4jLiveSourceBean")
    public Source getPcap4jLiveSource() {
        InetAddress addressObj = null;
        try {
            PcapNetworkInterface pcap4jDevice;
            addressObj = InetAddress.getByName(env.getProperty(nicAddressKey));
        } catch (UnknownHostException ex) {
            String errMsg = String.format("Failed to create InetAddress for nicAddress: %s, error %s", env.getProperty(nicAddressKey), ex.getMessage());
        }
        Source source = new Pcap4jLiveSource( addressObj );
        return source;
    }

    /**
     * Create pcap source, requires pcapFilepathKey be set.
     * @return
     */
    @Bean(name="pca4jFileSourceBean")
    public Source getPcap4jFileSource() {
        Source source = null;
        try {
            source = new Pcap4jPcapSource ( env.getProperty ( pcapFilepathKey ) );
            logger.info ( String.format ( "Created new Pcap4jPcapSource for file: {}", env.getProperty ( pcapFilepathKey ) ) );
        }
        catch (RuntimeException ex) {
            logger.error ( String.format ( "Failed to create Pcap4jPcapSource for file: {}, Error: {} ", env.getProperty ( pcapFilepathKey ), ex.getMessage()));
        }
         return source;
     }

     public String getNicAddress() {
        return env.getProperty(nicAddressKey);
     }

    public Environment getEnvironment() {
        return env;
    }
}
