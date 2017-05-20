package by.lvl.hexmap.tools;

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

import by.lvl.hexmap.BuildConfig;


public class AppLog {

    private static final String TAG = "Hexmap";


    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, getLocation() + " : " + (msg == null ? "" : msg));
        }
    }


    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, getLocation() + " : " + (msg == null ? "" : msg));
        }
    }


    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, getLocation() + " : " + (msg == null ? "" : msg));
        }
    }


    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, getLocation() + " : " + (msg == null ? "" : msg));
        }
    }


    private static String getLocation() {

        final String logClassName = AppLog.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (StackTraceElement trace : traces) {

            try {
                if (found) {
                    if (!trace.getClassName().startsWith(logClassName)) {

                        Class<?> clazz = Class.forName(trace.getClassName());

                        String clazzName = clazz.getSimpleName();
                        if (TextUtils.isEmpty(clazzName)) {
                            clazzName = clazz.getName();
                        }

                        return String.format(Locale.getDefault(), " [%s.%s:%d]", clazzName, trace.getMethodName(), trace.getLineNumber());
                    }
                }
                else if (trace.getClassName().startsWith(logClassName)) {
                    found = true;
                }
            }
            catch (ClassNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return " []";
    }
}
