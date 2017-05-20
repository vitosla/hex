package by.lvl.hexmap.api.events;

import by.lvl.hexmap.models.UserLocation.UserLocation;


public class PostLocationEvent extends SuccessfulEvent<UserLocation> {

    public PostLocationEvent(UserLocation data) {
        super(data);
    }
}