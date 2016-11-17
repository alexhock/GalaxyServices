package com.codertools.models;

/**
 * Created by ah14aeb on 16/11/2016.
 */
public class OutGalaxy {

    double separation = -1d;

    private int fieldId;
    private int id;
    private int numPatches;
    private double ra;
    private double dec;
    private int[] classifications;
    private double[] proxes;
    private int[] nnIndexes;
    //private double[] nnProxes;
    //private double[] skeltonGalaxy;
    private double f160w;
    private double f125w;
    private double f814w;
    private double f606w;
    private double f435w;
    private double kronRadius;
    private double zPhoto;



    public void setF160w(double f160w) { this.f160w = f160w; }
    public void setF125w(double f125w) { this.f125w = f125w; }
    public void setF606w(double f606w) { this.f606w = f606w; }
    public void setF814w(double f814w) { this.f814w = f814w; }
    public void setF435w(double f435w) { this.f435w = f435w; }
    public void setKronRadius(double kronRadius) { this.kronRadius = kronRadius; }
    public void setzPhoto(double zPhoto) { this.zPhoto = zPhoto; }

    public OutGalaxy() { }

    public OutGalaxy(Galaxy gal) {
        this.fieldId = gal.getFieldId();
        this.id = gal.getObjectId();
        this.numPatches = gal.getNumPatches();
        this.ra = gal.getRa();
        this.dec = gal.getDec();
        //this.classifications = gal.getClassifications();
        //this.proxes = gal.getProxes();
        //this.nnIndexes = gal.getNNIndexes();
        //this.nnProxes = gal.getNnProxes();
        this.f160w = gal.getF160w();
        this.f125w = gal.getF125w();
        this.f814w = gal.getF814w();
        this.f606w = gal.getF606w();
        this.f435w = gal.getF435w();
        this.kronRadius = gal.getKronRadius();
        this.zPhoto = gal.getzPhoto();

        //this.skeltonGalaxy = gal.getSkeltonGalaxy();
    }

    public int getFieldId() { return this.fieldId; }
    public int getObjectId() { return this.id; }
    public int getNumPatches() { return this.numPatches; }
    public double getRa() { return this.ra; }
    public double getDec() { return this.dec; }
    public int[] getClassifications() { return this.classifications; }
    public double[] getProxes() { return this.proxes; }
    public int[] getNnIndexess() { return this.nnIndexes; }
    public double getSeparation() { return this.separation; }
    public void setSeparation(double separation) { this.separation = separation; }

    public double getF160w() { return f160w; }
    public double getF125w() { return f125w; }
    public double getF814w() { return f814w; }
    public double getF606w() { return f606w; }
    public double getF435w() { return f435w; }
    public double getKronRadius() { return kronRadius; }
    public double getzPhoto() { return zPhoto; }
}
