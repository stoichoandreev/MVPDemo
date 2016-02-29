package babylon.sniper.com.babylon.ui.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import babylon.sniper.com.babylon.BabylonApp;
import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.ui.dialogs.MyProgressDialog;
import babylon.sniper.com.babylon.ui.fragments.BaseFragment;
import babylon.sniper.com.babylon.utils.ConnectivityChangeBroadcastReceiver;
import babylon.sniper.com.babylon.utils.IConnectivityChangeListener;

@UiThread
public abstract class BaseActivity extends AppCompatActivity implements IConnectivityChangeListener {

    private static final String TAG = BaseActivity.class.getCanonicalName();
    public static final String PROGRESSBAR_TAG = "Progress";
    protected DialogFragment progressDialog;
    protected BabylonApp mApp;
    private ConnectivityChangeBroadcastReceiver mReceiver;// internet connection checker
    private boolean hasBeenApplicationNotifiedForInternetConnection;// use this flag to prevent more then once activities internet state changes
    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResource());
        mApp = getBabylonApplication();
    }
    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        if (mReceiver == null) {
            mReceiver = new ConnectivityChangeBroadcastReceiver(this);
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, filter);
    }
    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mReceiver);
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void connectivityChange(boolean connected) {

        mApp.isOnline = connected;
        if(connected && !hasBeenApplicationNotifiedForInternetConnection){
            hasBeenApplicationNotifiedForInternetConnection = true;
            applicationHasInternetConnection();
        }else {
            hasBeenApplicationNotifiedForInternetConnection = false;
        }
    }
    /**
     * Use this to replace existing fragment with other fragment
     * @param fragment
     * @param withAnimationTransaction
     */
    public void replaceFragment(Fragment fragment, boolean withAnimationTransaction) {
        FragmentTransaction t = getFragmentManager().beginTransaction();
        if(withAnimationTransaction) t.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter, R.anim.fragment_slide_right_exit);

        String fragmentClassName = fragment.getClass().getCanonicalName();
        t.replace(R.id.activity_fragment_container, fragment, fragmentClassName);
        t.addToBackStack(fragmentClassName);
        t.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStackImmediate();
        } else {
            getFragmentManager().popBackStackImmediate();
            super.onBackPressed();
        }
    }
    public void showSoftKeyBoard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyBoard(View view) {
//        Pass view which is on focus with keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public DialogFragment getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog();
        }
         return progressDialog;
    }

    protected void notifyTopScreenAboutConnectionChanges(){
        try {
            FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1);
            String topFragmentName = backEntry.getName();
            final Fragment fragment = getFragmentManager().findFragmentByTag(topFragmentName);
            if(fragment != null && fragment instanceof BaseFragment) {
                ((BaseFragment)fragment).onConnectionChange(BabylonApp.isOnline);
            }
        }catch (ArrayIndexOutOfBoundsException ex){
            Log.e(TAG,"There is no fragment in the stack to be notified");
//            throw new RuntimeException("notifyTopScreenAboutConnectionChanges ArrayIndexOutOfBoundsException");
        }
    }

    public BabylonApp getBabylonApplication(){
        return (BabylonApp) getApplication();
    }

    public void showProgress() {
        if (getProgressDialog() != null && !getProgressDialog().isVisible() && !getProgressDialog().isAdded()) {
            getProgressDialog().show(getFragmentManager(), PROGRESSBAR_TAG);
        }
    }
    public void hideProgress() {
        if (getProgressDialog() != null && getProgressDialog().isVisible()) {
            getProgressDialog().dismiss();
        }
    }
    @LayoutRes
    protected abstract int getLayoutResource();
    protected abstract void applicationHasInternetConnection();
}