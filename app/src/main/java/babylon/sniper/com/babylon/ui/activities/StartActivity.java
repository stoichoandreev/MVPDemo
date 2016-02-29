package babylon.sniper.com.babylon.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.ui.fragments.PostsFragment;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {

    public static final String SELECTED_POST_DATA = "selected_data";
    public static final String SELECTED_USER = "selected_user";

    @Override
    protected int getLayoutResource() {
        return R.layout.start_activity_layout;
    }

    @Override
    protected void applicationHasInternetConnection() {
        notifyTopScreenAboutConnectionChanges();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        replaceFragment(PostsFragment.newInstance(null),false);
    }

}
