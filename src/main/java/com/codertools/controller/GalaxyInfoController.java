package com.codertools.controller;

import com.codertools.cache.GalaxyDataStore;
import com.codertools.models.Galaxy;
import com.codertools.models.GalaxyNearFind;
import com.codertools.models.OutGalaxy;
import com.codertools.services.GalaxyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by ah14aeb on 11/11/2016.
 */
@RestController
public class GalaxyInfoController {

    @Inject
    GalaxyService galaxyService;
    //@Inject    GalaxyDataStore dataStore;

    @RequestMapping("/info")
    public Galaxy getGalaxyInfo(@RequestParam(value="id", defaultValue="-1") String id) {
        return new Galaxy(id);
    }

    @RequestMapping("/nearsearch")
    public Collection<OutGalaxy> getGalaxiesFromRADEC(
            @RequestParam(value="ra", defaultValue = "-1") String sra,
            @RequestParam(value="dec", defaultValue = "-1") String sdec,
            @RequestParam(value="maxSep", defaultValue = "-1") String smaxSep) {

        if (sra == null || sra.isEmpty())
            throw new IllegalArgumentException("RA is missing");
        if (sdec == null || sdec.isEmpty())
            throw new IllegalArgumentException("DEC is missing");
        if (smaxSep == null || smaxSep.isEmpty())
            throw new IllegalArgumentException("Max Separation is missing");

        // validate
        double ra = -1d;
        double dec = -1d;
        double maxSep = -1d;
        try {
            ra = Double.parseDouble(sra);
            dec = Double.parseDouble(sdec);
            maxSep = Double.parseDouble(smaxSep);
        } catch(NumberFormatException ex) {
            throw new NumberFormatException("Invalid field entries");
        }

        if (maxSep > 2) {
            throw new IllegalArgumentException("Max separation is too large. Reduce to below 2");
        }

        List<OutGalaxy> outGalaxies = galaxyService.doRaDecSearch(ra, dec, maxSep);

        return outGalaxies;
    }

    @RequestMapping("/classification")
    public Collection<Galaxy> getGalaxiesFromClassification(
            @RequestParam(value="level", defaultValue = "-1") int level,
            @RequestParam(value="class", defaultValue = "-1") int classification) {

        return new ArrayList<Galaxy>();
    }

    //https://blog.jayway.com/2014/10/19/spring-boot-error-responses/
    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

}
