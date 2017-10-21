package com.royalteck.progtobi.aauaeventmanager.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PROG. TOBI on 09-Aug-17.
 */

public class EventModel implements Serializable {

    @SerializedName("title")
    private String title;

    @SerializedName("year")
    private String year;

    @SerializedName("month")
    private String month;

    @SerializedName("daynum")
    private String daynum;

    @SerializedName("location")
    private String location;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImadeUrl() {
        return imageUrl;
    }

    public void setImadeUrl(String imadeUrl) {
        this.imageUrl = imadeUrl;
    }
}
