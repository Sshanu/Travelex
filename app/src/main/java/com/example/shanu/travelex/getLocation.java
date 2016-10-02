package com.example.shanu.travelex;

/**
 * Created by Shanu on 01-03-2016.
 */
public class getLocation
{
    String naddress;
    double mlatitude;
    double mlongitude;

    public getLocation() {
    }

    public getLocation( double mlatitude, double mlongitude, String naddress) {
        this.mlatitude = mlatitude;
        this.mlongitude = mlongitude;
        this.naddress = naddress;
    }
    public double getMlatitude() {
        return mlatitude;
    }

    public void setMlatitude(double mlatitude) {
        this.mlatitude = mlatitude;
    }

    public double getMlongitude() {
        return mlongitude;
    }

    public void setMlongitude(double mlongitude) {
        this.mlongitude = mlongitude;
    }

    public String getNaddress() {
        return naddress;
    }

    public void setNaddress(String naddress) {
        this.naddress = naddress;
    }
}
