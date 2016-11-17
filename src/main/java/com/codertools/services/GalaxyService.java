package com.codertools.services;

import com.codertools.cache.GalaxyDataStore;
import com.codertools.models.Galaxy;
import com.codertools.models.GalaxyKey;
import com.codertools.models.GalaxyNearFind;
import com.codertools.models.OutGalaxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ah14aeb on 11/11/2016.
 */
@Component
public class GalaxyService {

    @Inject
    private GalaxyDataStore dataStore;


    public List<OutGalaxy> doRaDecSearch(double ra, double dec, double maxSeparation) {

        List<OutGalaxy> galaxies = new ArrayList<>();

        List<GalaxyNearFind> raGalaxies = dataStore.find(ra, dec, maxSeparation);

        // fast forward through them
        for(GalaxyNearFind galaxyNF : raGalaxies) {
            GalaxyKey key = galaxyNF.getGalaxyKey();
            Galaxy galaxy = dataStore.getGalaxy(key.getKey());
            OutGalaxy outGalaxy = new OutGalaxy(galaxy);
            outGalaxy.setSeparation(galaxyNF.getSeparation());
            galaxies.add(outGalaxy);
        }

        return galaxies;
    }

}
