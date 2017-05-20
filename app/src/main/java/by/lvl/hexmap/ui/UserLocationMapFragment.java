package by.lvl.hexmap.ui;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import by.lvl.hexmap.R;
import by.lvl.hexmap.api.Api;
import by.lvl.hexmap.models.User.User;
import by.lvl.hexmap.models.User.repo.PrefUserRepository;
import by.lvl.hexmap.models.UserLocation.UserLocation;
import by.lvl.hexmap.tools.Constants;
import by.lvl.hexmap.tools.DateHelper;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


@SuppressWarnings("MissingPermission")
public class UserLocationMapFragment extends Fragment implements OnMapReadyCallback {

    private static View view;

    private GoogleMap map;
    private Location myLocation;
    private BitmapDescriptor bmdMarker, bmdMarkerActive, bmdMarkerMe;

    private Subscription locationSubscription;
    private boolean isFirstTime = true;

    private int maxDistance, maxTime;
    private Marker selectedMarker;
    private UserLocation selectedLocation;


    public static UserLocationMapFragment newInstance(Bundle args) {
        UserLocationMapFragment fragment = new UserLocationMapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getContext().getApplicationContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            if (map != null) map.clear();
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) parent.removeView(view);
        }

        try {
            view = inflater.inflate(R.layout.fragment_map, container, false);
        }
        catch (InflateException e) {
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        maxDistance = getArguments().getInt(Constants.DISTANCE, 1000);
        maxTime = getArguments().getInt(Constants.DISTANCE, 600);

        bmdMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_gray);
        bmdMarkerActive = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_red);
        bmdMarkerMe = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_me);

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initMap();
    }


    private void initMap() {

        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        myLocation = lm.getLastKnownLocation(lm.getBestProvider(new Criteria(), false));
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerClickListener(this::onMarkerClick);
        map.setOnMapClickListener(this::onMapClick);

        addMe();

        if (map != null && myLocation != null) {

            LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            Observable<List<UserLocation>> locationObservable = Api.getInstance()
                    .getUserLocationListObservable(myLatLng.latitude, myLatLng.longitude, maxDistance, maxTime);
            locationSubscription = locationObservable.subscribe(new Subscriber<List<UserLocation>>() {

                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) { }

                @Override
                public void onNext(List<UserLocation> userLocationList) {
                    map.clear();
                    addMarkers(userLocationList);
                    addMe();
                }
            });
        }
    }


    private boolean onMarkerClick(Marker marker) {

        if (selectedMarker != null) {
            selectedMarker.setIcon(bmdMarker);
        }

        selectedMarker = marker;
        selectedMarker.setIcon(bmdMarkerActive);
        selectedMarker.showInfoWindow();

        selectedLocation = (UserLocation) selectedMarker.getTag();

        return false;
    }


    private void onMapClick(LatLng latLng) {
        if (selectedMarker != null) {
            selectedMarker.setIcon(bmdMarker);
        }
        selectedMarker = null;
        selectedLocation = null;
    }


    private void addMe() {
        if (map != null && myLocation != null) {
            LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(myLatLng)
                    .title(getString(R.string.Im_here))
                    .icon(bmdMarkerMe));
            if (isFirstTime) cameraForMe(myLatLng);
        }
    }


    private void cameraForMe(LatLng myLatLng) {
        isFirstTime = false;
        CameraPosition myPosition =
                new CameraPosition.Builder().target(myLatLng)
                        .zoom(10.5f)
                        .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition), null);
    }


    private void addMarkers(List<UserLocation> userLocationList) {

        selectedMarker = null;
        User user = new PrefUserRepository().get();

        for (UserLocation item : userLocationList) {
            if (!user.getId().equals(item.getUser().getId())) {

                MarkerOptions opt = new MarkerOptions()
                        .title(item.getUser().getName())
                        .snippet(String.valueOf((int) item.getDistance()) + " м - в " + DateHelper.DATE_FORMATTER_HH_mm.format(item.getDate()))
                        .icon(bmdMarker)
                        .position(item.getLocation());

                Marker marker = map.addMarker(opt);
                marker.setTag(item);

                if (selectedLocation != null && item.getUser().getId().equals(selectedLocation.getUser().getId())) {
                    onMarkerClick(marker);
                }
            }
        }
    }
}
