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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tlee on 2/11/17.
 */
public class ObserverMock implements Observer {
    public List<Carton> cartons;

    public ObserverMock() {
        cartons = new ArrayList<Carton>();
    }
    public void convey ( Carton carton ) {
        cartons.add ( carton );
    }
}
