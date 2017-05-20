package by.lvl.hexmap.api;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.lvl.hexmap.api.events.GetUserLocationListEvent;
import by.lvl.hexmap.api.events.PostLocationEvent;
import by.lvl.hexmap.api.events.PostSignupEvent;
import by.lvl.hexmap.api.repo.IRepositoryFactory;
import by.lvl.hexmap.models.User.User;
import by.lvl.hexmap.models.User.repo.IUserRepository;
import by.lvl.hexmap.models.UserLocation.UserLocation;
import by.lvl.hexmap.models.UserLocation.repo.IUserLocationRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class Api {

    private static Api instance = null;

    private Api() { }

    public static Api getInstance() {
        if (instance == null)
            instance = new Api();
        return instance;
    }

    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private IRepositoryFactory repositoryFactory;


    public void setRepositoryFactory(IRepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }


    public void signup(final User user) {

        IUserRepository repository = repositoryFactory.getUserRepository();

        compositeSubscription
                .add(repository.signup(user)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseResultSubscriber<Void>() {
                            @Override
                            public void postSuccessful(Void data) {
                                EventBus.getDefault().post(new PostSignupEvent(user));
                            }
                        }));
    }


    public void postLocation(final UserLocation userLocation) {

        IUserLocationRepository repository = repositoryFactory.getUserLocationRepository();

        compositeSubscription
                .add(repository.put(userLocation)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseResultSubscriber<Void>() {
                            @Override
                            public void postSuccessful(Void data) {
                                EventBus.getDefault().post(new PostLocationEvent(userLocation));
                            }
                        }));
    }


    public void getUserLocationList(double lat, double lng, double distance, int timeAgoSec) {

        IUserLocationRepository repository = repositoryFactory.getUserLocationRepository();

        compositeSubscription
                .add(repository.getList(lat, lng, distance, timeAgoSec)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseResultSubscriber<List<UserLocation>>() {
                            @Override
                            public void postSuccessful(List<UserLocation> data) {
                                EventBus.getDefault().post(new GetUserLocationListEvent(data));
                            }
                        }));
    }


    public Observable<List<UserLocation>> getUserLocationListObservable(double lat, double lng, double distance, int timeAgoSec) {
        IUserLocationRepository repository = repositoryFactory.getUserLocationRepository();
        return Observable
                .interval(10, TimeUnit.SECONDS)
                .flatMap(tick -> repository.getList(lat, lng, distance, timeAgoSec))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void cancelAll() {
        compositeSubscription.unsubscribe();
        compositeSubscription = new CompositeSubscription();
    }
}
