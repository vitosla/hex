package by.lvl.hexmap.models.UserLocation;


import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import by.lvl.hexmap.models.User.User;


public class UserLocation {

    private User user;
    private LatLng location;
    private Date date;
    private double distance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
