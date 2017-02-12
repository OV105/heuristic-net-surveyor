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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AgentTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AgentTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AgentTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testNetSurveyor()
    {
        assertTrue( true );
    }
}
