package by.lvl.hexmap.models.UserLocation.repo;

import java.util.List;

import by.lvl.hexmap.api.retrofit.RetrofitService;
import by.lvl.hexmap.models.UserLocation.PostUserLocationDTO;
import by.lvl.hexmap.models.UserLocation.UserLocation;
import by.lvl.hexmap.models.UserLocation.UserLocationMapper;
import rx.Observable;


public class RemoteUserLocationRepository implements IUserLocationRepository {

    @Override
    public Observable<Void> put(UserLocation userLocation) {
        PostUserLocationDTO value = new UserLocationMapper().map(userLocation);
        return RetrofitService.getInstance().postLocation(value);
    }

    @Override
    public Observable<List<UserLocation>> getList(double lat, double lng, double distance, int timeAgoSec) {
        return RetrofitService.getInstance()
                .getUserLocationList(lat, lng, distance, timeAgoSec)
                .map(dtoList -> new UserLocationMapper().map(dtoList));
    }
}
