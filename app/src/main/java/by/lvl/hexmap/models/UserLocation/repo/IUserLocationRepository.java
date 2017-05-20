package by.lvl.hexmap.models.UserLocation.repo;

import java.util.List;

import by.lvl.hexmap.models.UserLocation.UserLocation;
import rx.Observable;


public interface IUserLocationRepository {
    Observable<Void> put(UserLocation userLocation);
    Observable<List<UserLocation>> getList(double lat, double lng, double distance, int timeAgoSec);
}
