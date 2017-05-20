package by.lvl.hexmap.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import by.lvl.hexmap.R;
import by.lvl.hexmap.tools.Constants;


public class Navigator {

    public static void toSignUp(FragmentActivity activity) {
        Fragment fragment = SignupFragment.newInstance(null);
        addFragment(activity, fragment);
    }

    public static void toMap(FragmentActivity activity, int distance, int time) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DISTANCE, distance);
        bundle.putInt(Constants.TIME, time);
        Fragment fragment = UserLocationMapFragment.newInstance(bundle);
        addFragment(activity, fragment);
    }

    public static void addFragment(FragmentActivity activity, Fragment fragment) {
        String tag = fragment.getClass().getName();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_right_in, R.anim.activity_left_out, R.anim.activity_left_in, R.anim.activity_right_out)
                .add(R.id.fragment_layout, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    private static void replaceFragment(FragmentActivity activity, Fragment fragment) {
        String tag = fragment.getClass().getName();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_right_in, R.anim.activity_left_out, R.anim.activity_left_in, R.anim.activity_right_out)
                .replace(R.id.fragment_layout, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }
}
