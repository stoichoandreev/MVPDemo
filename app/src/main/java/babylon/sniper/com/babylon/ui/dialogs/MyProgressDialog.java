package babylon.sniper.com.babylon.ui.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import babylon.sniper.com.babylon.R;


public class MyProgressDialog extends DialogFragment {
    public MyProgressDialog() {
        super();
        int style = DialogFragment.STYLE_NO_TITLE;
        setStyle(style, getTheme());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_progress_layout, container, false);
        setCancelable(true);
        return rootView;
    }
    @Override
    public int getTheme() {
        return R.style.BabylonDialog;
    }
}
