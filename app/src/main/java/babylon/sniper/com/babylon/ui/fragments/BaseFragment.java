package babylon.sniper.com.babylon.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;

import babylon.sniper.com.babylon.ui.activities.BaseActivity;


@UiThread
public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragmentUI();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        //call this code to hide keyboard if it is open from previous fragment
        mActivity.hideSoftKeyBoard(getView());
    }

    protected abstract void initFragmentUI();
    public abstract void onConnectionChange(boolean isConnected);

}
