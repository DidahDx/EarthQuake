package com.example.earthquake;

public class EarthQuake {

    private String magnitude;
    private String location;
    private String date;
    private String time;
    private String url;

    private String LocationOffeset;

    public EarthQuake(String mMagnitude, String mLocation, String mDate, String mUrl,String mTime,String mLocationOffeset) {
        magnitude = mMagnitude;
        location = mLocation;
        date = mDate;
        url=mUrl;
        time=mTime;
        LocationOffeset=mLocationOffeset;
    }

    public EarthQuake(String mMagnitude, String mLocation, String mDate, String mUrl,String mTime) {
        magnitude = mMagnitude;
        location = mLocation;
        date = mDate;
        url=mUrl;
        time=mTime;

    }


    public String getTime() {
        return time;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getLocationOffeset() {
        return LocationOffeset;
    }
}
