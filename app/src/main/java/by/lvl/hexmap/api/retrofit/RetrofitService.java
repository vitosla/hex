package by.lvl.hexmap.api.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.lvl.hexmap.BuildConfig;
import by.lvl.hexmap.models.User.UserDTO;
import by.lvl.hexmap.models.UserLocation.PostUserLocationDTO;
import by.lvl.hexmap.models.UserLocation.UserLocationDTO;
import by.lvl.hexmap.tools.DateHelper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class RetrofitService implements IRetrofitService {

    private static RetrofitService instance = null;

    private RetrofitService() { }

    public static RetrofitService getInstance() {
        if (instance == null) {
            instance = new RetrofitService();
        }
        return instance;
    }

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .build();

    private Gson gson = new GsonBuilder()
            .setDateFormat(DateHelper.DATE_PATTERN_yyyy_MM_ddTHH_mm_ss)
            .create();

    private IRetrofitService caller = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()
            .create(IRetrofitService.class);

    @Override
    public Observable<Void> signup(UserDTO value) {
        return caller.signup(value);
    }


    @Override
    public Observable<Void> postLocation(PostUserLocationDTO value) {
        return caller.postLocation(value);
    }


    @Override
    public Observable<List<UserLocationDTO>> getUserLocationList(double lat, double lng, double distance, int timeAgoSec) {
        return caller.getUserLocationList(lat, lng, distance, timeAgoSec);
    }
}
