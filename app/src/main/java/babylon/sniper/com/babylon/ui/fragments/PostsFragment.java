package babylon.sniper.com.babylon.ui.fragments;


import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.ui.activities.StartActivity;
import babylon.sniper.com.babylon.ui.adapters.PostsAdapter;
import babylon.sniper.com.babylon.ui.interfaces.PostAdapterListener;
import butterknife.Bind;
import butterknife.ButterKnife;

@UiThread
public class PostsFragment extends BaseFragment implements PostAdapterListener {

    private static final String TAG = PostsFragment.class.getCanonicalName();
    public static final int BACKGROUND_TRANSITION_DURATION = 1000;

    @Bind(R.id.posts_recycler_view)             RecyclerView mRecyclerView;
    @Bind(R.id.title_tv)                        TextView titleTextView;
    @Bind(R.id.no_content_message_view_stub)    ViewStub noContentMessageViewStub;
    private                                     TextView noContentTv;

//    private PostsViewPresenter postsPresenter;
    private PostsAdapter postAdapter;

    public static PostsFragment newInstance(Bundle args) {
        PostsFragment fragment = new PostsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBackground();
    }
    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.posts_fragment_layout, container, false);

        ButterKnife.bind(this,rootView);

//        postsPresenter = new PostsViewPresenter(this);

        if (postAdapter == null) {
            postAdapter = new PostsAdapter(mActivity.getBabylonApplication(), this);
            postAdapter.activatePostListPresenter();
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(postAdapter);

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
//        postsPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        postsPresenter.unbindView();
    }
    @Override
    protected void initFragmentUI() {
        if(isAdded()) {
            titleTextView.setText(getResources().getString(R.string.post_screen_title));
        }
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        if(isConnected){
            postAdapter.activatePostListPresenter();
        }
    }

    private void setupBackground(){
        TransitionDrawable trans = (TransitionDrawable) ContextCompat.getDrawable(mActivity,R.drawable.posts_background_transition);
        mActivity.getWindow().setBackgroundDrawable(trans);
        trans.reverseTransition(BACKGROUND_TRANSITION_DURATION);
    }

    @Override
    public void onPostSelected(Post postData) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(StartActivity.SELECTED_POST_DATA,postData);
        mActivity.replaceFragment(DetailsFragment.newInstance(bundle),true);
    }

    @Override
    public void onDataLoad() {
        if(noContentTv != null && noContentTv.getVisibility() == View.VISIBLE){
            noContentTv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDataError(@StringRes int message) {
        if(isAdded() && mRecyclerView != null) {
            Snackbar.make(mRecyclerView, message, Snackbar.LENGTH_LONG).show();
            if(postAdapter.getItemCount() == 0) {
                if (noContentMessageViewStub.getParent() != null) {
                    noContentTv = (TextView) noContentMessageViewStub.inflate();
                }
                noContentTv.setVisibility(View.VISIBLE);
            }
        }
    }
//    @Override
//    public void setModuleCollection(PostModuleCollection moduleCollection) {
//
//    }
}
