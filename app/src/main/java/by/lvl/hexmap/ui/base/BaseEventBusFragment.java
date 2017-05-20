package by.lvl.hexmap.ui.base;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import by.lvl.hexmap.api.Api;
import by.lvl.hexmap.api.events.FailedEvent;
import by.lvl.hexmap.ui.custom.WaitDialog.WaitDialogHelper;


public abstract class BaseEventBusFragment extends Fragment {

    protected boolean shouldRegisterEventBus = false;
    private WaitDialogHelper waitDialogHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        waitDialogHelper = new WaitDialogHelper(getActivity().getSupportFragmentManager(), Api.getInstance()::cancelAll);
        shouldRegisterEventBus = true;
    }


    @Override
    public void onStop() {
        super.onStop();
        if (shouldRegisterEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (shouldRegisterEventBus) {
            EventBus.getDefault().register(this);
        }
    }


    public void showProgress() {
        waitDialogHelper.showWaitDialog();
    }


    public void hideProgress() {
        new Handler().post(() -> waitDialogHelper.dismissWaitDialog());
    }


    @Subscribe
    public void onApiRequestFailed(FailedEvent event) {
        hideProgress();
    }
}

