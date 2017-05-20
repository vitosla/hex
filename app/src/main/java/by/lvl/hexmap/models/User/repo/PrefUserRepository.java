package by.lvl.hexmap.models.User.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import by.lvl.hexmap.AppContext;
import by.lvl.hexmap.R;
import by.lvl.hexmap.models.User.User;


public class PrefUserRepository {

    public void put(User user) {
        Context context = AppContext.INSTANCE.getContext();
        String json = new Gson().toJson(user);
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString(context.getString(R.string.pref_user), json);
        prefEditor.apply();
    }

    @NonNull
    public User get() {
        Context context = AppContext.INSTANCE.getContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String json = pref.getString(context.getString(R.string.pref_user), null);
        return TextUtils.isEmpty(json) ? new User() : new Gson().fromJson(json, User.class);
    }

    public void clear() {
        Context context = AppContext.INSTANCE.getContext();
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefEditor.putString(context.getString(R.string.pref_user), "");
        prefEditor.apply();
    }
}
