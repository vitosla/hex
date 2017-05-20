package by.lvl.hexmap.ui.custom.WaitDialog;

import android.support.v4.app.FragmentManager;

import by.lvl.hexmap.tools.AppLog;


public class WaitDialogHelper {

    private WaitDialogFragment waitDialogFragment;
    private FragmentManager fragmentManager;


    public interface OnCancelWaitDialog {
        void onCancel();
    }


    public WaitDialogHelper(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        waitDialogFragment = new WaitDialogFragment();
        waitDialogFragment.setCancelable(true);
    }


    public WaitDialogHelper(FragmentManager fragmentManager, OnCancelWaitDialog callback) {
        this(fragmentManager);
        setWaitDialogCancelCallback(callback);
    }


    public void setWaitDialogCancelCallback(OnCancelWaitDialog callback) {
        waitDialogFragment.setWaitDialogCancelCallback(callback);
    }


    public void showWaitDialog() {

        if (waitDialogFragment.isAdded()) {
            return;
        }

        try {
            fragmentManager
                    .beginTransaction()
                    .add(waitDialogFragment, null)
                    .commitAllowingStateLoss();
        }
        catch (IllegalStateException e) {
            AppLog.e(e.getMessage());
        }
    }


    public void dismissWaitDialog() {
        if (waitDialogFragment != null && waitDialogFragment.getDialog() != null && waitDialogFragment.isAdded()) {
            waitDialogFragment.getDialog().dismiss();
            waitDialogFragment.dismissAllowingStateLoss();
        }
    }
}
