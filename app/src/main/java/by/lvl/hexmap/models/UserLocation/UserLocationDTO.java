package by.lvl.hexmap.models.UserLocation;

import com.google.gson.annotations.SerializedName;
import by.lvl.hexmap.models.User.UserDTO;


public class UserLocationDTO {

    @SerializedName("User")
    private UserDTO user;
    @SerializedName("Location")
    private PostUserLocationDTO location;
    @SerializedName("Distance")
    private double distance;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PostUserLocationDTO getLocation() {
        return location;
    }

    public void setLocation(PostUserLocationDTO location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
