package by.lvl.hexmap.api.retrofit;

import java.util.List;

import by.lvl.hexmap.models.User.UserDTO;
import by.lvl.hexmap.models.UserLocation.PostUserLocationDTO;
import by.lvl.hexmap.models.UserLocation.UserLocationDTO;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface IRetrofitService {

    @POST("/api/users")
    Observable<Void> signup(
            @Body UserDTO value
    );

    @POST("/api/locations")
    Observable<Void> postLocation(
            @Body PostUserLocationDTO value
    );

    @GET("/api/locations/nearest")
    Observable<List<UserLocationDTO>> getUserLocationList(
            @Query("lat") double lat,
            @Query("lng") double lng,
            @Query("distance") double distance,
            @Query("datediff") int timeAgoSec
    );
}