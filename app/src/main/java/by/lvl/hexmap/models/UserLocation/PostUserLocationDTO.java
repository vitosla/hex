package by.lvl.hexmap.models.UserLocation;


import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class PostUserLocationDTO {

    @SerializedName("UserId")
    private String userId;
    @SerializedName("Lat")
    private double lat;
    @SerializedName("Lng")
    private double lng;
    @SerializedName("Timestamp")
    private Date date;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
