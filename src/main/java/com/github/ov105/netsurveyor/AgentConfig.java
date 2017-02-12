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

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;
import org.springframework.context.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class to configure net surveyor using Spring
 */

@Configuration
@PropertySource ( "classpath:/com/gitub.ov105/netsurveyor/agent.properties")
public class AgentConfig {

    @Bean
    public Source getPcap4jLiveSource() {
        String nicAddress = "0.0.0.0";
        InetAddress addressObj = null;
        try {
            PcapNetworkInterface pcap4jDevice;
            addressObj = InetAddress.getByName(nicAddress);
        } catch (UnknownHostException ex) {
            String errMsg = String.format("Failed to create InetAddress for nicAddress: %s, error %s", nicAddress, ex.getMessage());
        }
        Source source = new Pcap4jLiveSource( addressObj );
        return source;
    }
     @Bean
    public Source getPcap4jFileSource() {
         Source source = new Pcap4jPcapSource( "");
         return source;

     }

}
