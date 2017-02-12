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
