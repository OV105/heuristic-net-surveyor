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

import org.springframework.context.annotation.*;

/**
 * Class to configure net surveyor using Spring
 */

@Configuration
@PropertySource ( "classpath:/com/gitub.ov105/netsurveyor/agent.properties")
public class AgentConfig {

    @Bean
    public Source getPcap4jSource() {
        Source source = new Pcap4jPcapSource( "");
        return source;
    }

}
