package by.lvl.hexmap.api;

import android.accounts.NetworkErrorException;
import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import by.lvl.hexmap.AppContext;
import by.lvl.hexmap.R;
import by.lvl.hexmap.api.events.FailedEvent;
import by.lvl.hexmap.tools.AppLog;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;


public abstract class BaseResultSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() { }


    @Override
    public void onError(Throwable e) {

        AppLog.e(e.getMessage());
        String message = e.getMessage();

        Context context = AppContext.INSTANCE.getContext();

        if (e instanceof ConnectException) {
            message = context.getString(R.string.connect_exception_error);
        }
        else if (e instanceof SocketTimeoutException){
            message = context.getString(R.string.timeout_error);
        }
        else if (e instanceof UnknownHostException || e instanceof NetworkErrorException) {
            message = context.getString(R.string.network_error);
        }
        else if (e instanceof HttpException) {
            message = context.getString(R.string.something_went_wrong);
        }

        EventBus.getDefault().post(new FailedEvent(message));
    }


    @Override
    public void onNext(T data) {
        postSuccessful(data);
    }


    public void postSuccessful(T data) {};
}

