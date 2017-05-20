package by.lvl.hexmap.models.UserLocation;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import by.lvl.hexmap.models.User.UserMapper;
import rx.Observable;


public class UserLocationMapper {

    private UserMapper userMapper;


    public UserLocationMapper() {
        userMapper = new UserMapper();
    }

    public UserLocation map(UserLocationDTO dto) {
        PostUserLocationDTO location = dto.getLocation();
        UserLocation item = new UserLocation();
        item.setUser(userMapper.map(dto.getUser()));
        item.setLocation(new LatLng(location.getLat(), location.getLng()));
        item.setDate(location.getDate());
        item.setDistance(dto.getDistance());
        return item;
    }

    public PostUserLocationDTO map(UserLocation userLocation) {
        PostUserLocationDTO item = new PostUserLocationDTO();
        item.setUserId(userLocation.getUser().getId());
        item.setLat(userLocation.getLocation().latitude);
        item.setLng(userLocation.getLocation().longitude);
        item.setDate(userLocation.getDate());
        return item;
    }

    public List<UserLocation> map(List<UserLocationDTO> dtoList) {

        if (dtoList == null || dtoList.size() == 0) return new ArrayList<>();

        return Observable
                .from(dtoList)
                .map(this::map)
                .toList()
                .toBlocking()
                .first();
    }
}
