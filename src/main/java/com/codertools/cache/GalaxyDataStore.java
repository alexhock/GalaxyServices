package com.codertools.cache;

import com.codertools.models.Galaxy;
import com.codertools.models.GalaxyKey;
import com.codertools.models.GalaxyKeyComparator;
import com.codertools.models.GalaxyNearFind;
import com.codertools.utils.AstrometryUtils;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.*;

/**
 * Created by ah14aeb on 11/11/2016.
 */
@Component
public class GalaxyDataStore {

    private static ArrayList<Galaxy> allGalaxies;
    private static ArrayList<GalaxyKey> galPositions;
    private static HashMap<String, Galaxy> galaxyMap;
    //private static HashMap<Integer, ArrayList<Galaxy>> level1 = new HashMap<Integer, ArrayList<Galaxy>>();

    public GalaxyDataStore()
    {

    }

    public void setGalaxies(ArrayList<Galaxy> galaxies) {
        GalaxyDataStore.allGalaxies = galaxies;
    }

    public void setGalaxiesMap(HashMap<String, Galaxy> galMap) {
        GalaxyDataStore.galaxyMap = galMap;
    }

    public void createIndexes() {

        ArrayList<GalaxyKey> tgalPositions = new ArrayList<>();
        for(int i=0; i<allGalaxies.size();i++) {
            Galaxy gal = allGalaxies.get(i);
            GalaxyKey key = new GalaxyKey(i, gal.getFieldId(), gal.getObjectId(), gal.getRa(), gal.getDec());
            tgalPositions.add(key);
        }

        // sort the galaxies by ra
        tgalPositions.sort(new GalaxyKeyComparator());

        galPositions = tgalPositions;
    }

    public Galaxy getGalaxy(String key) {
        return galaxyMap.get(key);
    }
/*
    public List<Galaxy> getGalaxyWithClass(int levelId, int classId) {
        return level1.get(classId);
    }
*/
    public List<Galaxy> getNNs(int[] nns) {
        List<Galaxy> galaxies = new ArrayList<>();
        for(int index : nns) {
            galaxies.add(allGalaxies.get(index));
        }
        return galaxies;
    }

    public List<GalaxyNearFind> find(double ra, double dec, double maxSeparation) {

        ArrayList<GalaxyNearFind> nearestGalaxies = new ArrayList<>();

        // get index for sorted RA
        int raIndex = getIndexOfNearest(galPositions, ra, dec);

        // loop back
        int indexFrom = raIndex;
        double galSeparation = 0.0d;
        double raSep = ra;
        int checkCount = 0;
        while (indexFrom > 0 && Math.abs(raSep - ra) <= maxSeparation)
        {
            GalaxyKey tempPos = galPositions.get(indexFrom);

            // if within dec then
            if (Math.abs(tempPos.getDec() - dec) <= maxSeparation) {
                // calc the galaxy separation
                galSeparation = AstrometryUtils.calcAngularSeparation(ra, dec, tempPos.getRa(), tempPos.getDec());
                if (galSeparation <= maxSeparation)
                    nearestGalaxies.add(new GalaxyNearFind(galSeparation, tempPos));
                checkCount++;
            }

            raSep = tempPos.getRa();
            indexFrom--;
        }

        indexFrom = raIndex + 1;
        galSeparation = 0.0d;
        raSep = ra;
        while (indexFrom < galPositions.size() && Math.abs(raSep - ra) <= maxSeparation)
        {
            GalaxyKey tempPos = galPositions.get(indexFrom);

            if (Math.abs(tempPos.getDec() - dec) <= maxSeparation) {
                galSeparation = AstrometryUtils.calcAngularSeparation(ra, dec, tempPos.getRa(), tempPos.getDec());
                if (galSeparation <= maxSeparation)
                    nearestGalaxies.add(new GalaxyNearFind(galSeparation, tempPos));
                checkCount++;
            }
            raSep = tempPos.getRa();
            indexFrom++;
        }

        System.out.println(checkCount);
        return nearestGalaxies;
    }

    private int getIndexOfNearest(ArrayList<GalaxyKey> sortedByRa, double ra, double dec) {

        GalaxyKey key = new GalaxyKey(ra, dec);
        int indexFrom = Collections.<GalaxyKey>binarySearch(sortedByRa, key, new GalaxyKeyComparator());
        if (indexFrom < 0) {
            int indexOfNearest = ~indexFrom;
            if (indexOfNearest == sortedByRa.size())
                return sortedByRa.size() - 1;

            if (indexOfNearest == 0)
                indexFrom = 0;
            else
                // from time is between (indexOfNearest - 1) and indexOfNearest
                indexFrom = indexOfNearest;
        }
        return indexFrom;
    }
}

/*
    public void addGalaxy(int id, Galaxy galaxy) {
        store.put(id, galaxy);
    }

    public void addClassification(int classId, ArrayList<Galaxy> classGalaxies) {
        level1.put(classId, classGalaxies);
    }
*/

/*
int binary_search(int A[], int key, int imin, int imax)
{
  // test if array is empty
  if (imax < imin)
    // set is empty, so return value showing not found
    return KEY_NOT_FOUND;
  else
    {
      // calculate midpoint to cut set in half
      int imid = midpoint(imin, imax);

      // three-way comparison
      if (A[imid] > key)
        // key is in lower subset
        return binary_search(A, key, imin, imid-1);
      else if (A[imid] < key)
        // key is in upper subset
        return binary_search(A, key, imid+1, imax);
      else
        // key has been found
        return imid;
    }
}
 */