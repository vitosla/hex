package by.lvl.hexmap.api.events;

import java.util.List;

import by.lvl.hexmap.models.UserLocation.UserLocation;


public class GetUserLocationListEvent extends SuccessfulEvent<List<UserLocation>> {

    public GetUserLocationListEvent(List<UserLocation> data) {
        super(data);
    }
}