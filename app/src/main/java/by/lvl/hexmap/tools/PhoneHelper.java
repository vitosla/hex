package by.lvl.hexmap.tools;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.UUID;

import by.lvl.hexmap.AppContext;


public class PhoneHelper {

    public static String getAndroidId() {

        final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        final String ID_KEY = "android_id";

        String androidId = UUID.randomUUID().toString();

        String[] params = { ID_KEY };
        Cursor c = AppContext.INSTANCE.getContext().getContentResolver().query(URI, null, null, params, null);

        if (c != null && (!c.moveToFirst() || c.getColumnCount() < 2)) {
            c.close();
        }
        else if (c != null) {
            try {
                String id = Long.toHexString(Long.parseLong(c.getString(1)));
                c.close();
                androidId = id;
            }
            catch (NumberFormatException e) {
            }
        }

        return androidId;
    }


    public static void hideSoftKeyboard(View v) {
        Context context = AppContext.INSTANCE.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
