package by.lvl.hexmap.ui.custom.WaitDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import by.lvl.hexmap.R;


public class WaitDialogFragment extends AppCompatDialogFragment {

    private WaitDialogHelper.OnCancelWaitDialog callback;


    public void setWaitDialogCancelCallback(WaitDialogHelper.OnCancelWaitDialog callback) {
        this.callback = callback;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme_Translucent_Dark);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wait_dialog, container, false);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        if (callback != null) {
            callback.onCancel();
        }
    }
}
