package by.lvl.hexmap;

import android.app.Application;

import by.lvl.hexmap.api.Api;
import by.lvl.hexmap.api.repo.RepositoryFactory;

// task1
public class HexmapApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.INSTANCE.init(getApplicationContext());
        Api.getInstance().setRepositoryFactory(new RepositoryFactory());
    }
}
