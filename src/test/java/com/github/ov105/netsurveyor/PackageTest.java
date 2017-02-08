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
import org.bson.Document;

import java.time.Instant;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Class to test Package class
 */
public class PackageTest extends TestCase {
    public void testConstructor() throws Exception {
        Package pkg = new PackageDummy<String>();
        assertThat ( pkg, instanceOf( Package.class ) );
    }

    public void testuid() throws Exception {
        Package pkg = new PackageDummy<String>();
        UUID uuid = pkg.getUuid();
        String uuidText = uuid.toString();
        assertEquals(36, uuidText.length());
    }

    public void testData() throws Exception {
        Package pkg = new PackageDummy<String>();
        String data = "Foo";
        pkg.setData(data);
        assertEquals(data, pkg.getData());
    }

    public void testGetTimestamp() throws Exception {
        Instant timestamp = Instant.now();
        Package pkg = new PackageDummy<String>();
        assertEquals(timestamp.getEpochSecond(), pkg.getTimestamp().getEpochSecond());

    }

    public void testGetTimestampMilli() throws Exception {
        Instant before = Instant.now();
        Package pkg = new PackageDummy<String>();
        Instant after = Instant.now();
        assertTrue(before.toEpochMilli() <= pkg.getTimestampMilli());
        assertTrue(after.toEpochMilli() >= pkg.getTimestampMilli());

    }

    public void testGetDocument() throws Exception {
        Package pkg = new PackageDummy<String>();
        Document doc = pkg.getDocument();
        assertEquals("value1", doc.get("key1"));
    }

}