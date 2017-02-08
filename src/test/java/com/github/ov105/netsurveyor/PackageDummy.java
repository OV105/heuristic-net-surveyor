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

import org.bson.Document;
/**
 * Created by tlee on 1/31/17.
 */
public class PackageDummy<String> extends Package<String> {
    public Document getDocument() {
        Document doc = new Document("key1", "value1");
        doc.append("key2", "value2");
        return doc;
    }
}
