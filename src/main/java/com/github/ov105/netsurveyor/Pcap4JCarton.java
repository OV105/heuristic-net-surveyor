package com.github.ov105.netsurveyor;
import org.bson.Document;

/**
 * Created by tlee on 2/6/17.
 */
public class Pcap4JCarton<Packet> extends Carton<Packet> {
    /**
     * no argument constructor.
     */
    public Pcap4JCarton() {
        super();
    }

    /**
     * constructor with data
     * @param data pcap4j packet
     */
    public Pcap4JCarton ( Packet data ) {
        super ( data );
    }

    /**
     * Extract data from pcap4j packet and return in bson document
     * @return bson document
     */
    public Document getDocument() {
        return null;
    }
}
