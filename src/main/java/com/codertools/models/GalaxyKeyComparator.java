package com.codertools.models;

import java.util.Comparator;

/**
 * Created by ah14aeb on 16/11/2016.
 */
public class GalaxyKeyComparator implements Comparator<GalaxyKey> {

    public GalaxyKeyComparator() { }

    // compare ra
    public int compare(GalaxyKey key1, GalaxyKey key2) {

        //if (key1.getFieldId() == key2.getFieldId() && key1.getObjectId() == key2.getObjectId())
        //    return 0;

        Double ra1 = key1.getRa();
        Double ra2 = key2.getRa();

        Double dec1 = key1.getDec();
        Double dec2 = key2.getDec();

        int retval = ra1.compareTo(ra2);
        if(retval != 0)
            return retval;

        // ra is equal so compare dec
        return dec1.compareTo(dec2);
    }

}
