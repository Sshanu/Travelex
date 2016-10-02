package com.example.shanu.travelex;

/**
 * Created by NikTin on 07/11/15.
 */
public class FeedItem {
    private String title;
    private String location;
    private String url;
    private String thumbnail;
    private String largethumbnail;
    private String rates;
    private String description;
    private String cuisines;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLargethumbnail() {
        return largethumbnail;
    }

    public void setLargethumbnail(String largethumbnail) {
        this.largethumbnail = largethumbnail;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }


}
