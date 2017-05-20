package by.lvl.hexmap;

import android.annotation.SuppressLint;
import android.content.Context;


public enum AppContext {

    @SuppressLint("StaticFieldLeak")INSTANCE;

    private Context applicationContext;


    public void init(final Context context) {
        applicationContext = context.getApplicationContext();
    }


    public Context getContext() {
        if (null == applicationContext) {
            throw new IllegalStateException("Have you called init(context)?");
        }
        return applicationContext;
    }
}
