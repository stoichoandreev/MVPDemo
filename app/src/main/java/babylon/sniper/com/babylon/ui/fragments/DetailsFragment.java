package babylon.sniper.com.babylon.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.api.object_models.User;
import babylon.sniper.com.babylon.api.repos.DetailsRepository;
import babylon.sniper.com.babylon.ui.activities.MapActivity;
import babylon.sniper.com.babylon.ui.activities.StartActivity;
import babylon.sniper.com.babylon.ui.presenters.DetailsViewPresenter;
import babylon.sniper.com.babylon.ui.views.DetailsView;
import butterknife.Bind;
import butterknife.ButterKnife;

@UiThread
public class DetailsFragment extends BaseFragment implements DetailsView {

    public static final String TAG = DetailsFragment.class.getCanonicalName();

    @Bind(R.id.title_tv)            TextView titleTextView;
    @Bind(R.id.user_name)           TextView userName;
    @Bind(R.id.post_title)          TextView postTitle;
    @Bind(R.id.post_body)           TextView postBody;
    @Bind(R.id.user_avatar)         SimpleDraweeView userAvatar;
    @Bind(R.id.comments_number)     TextView commentNumberTextView;

    private                         Post selectedPost;
    private                         User selectedUser;
    private                         DetailsViewPresenter detailsPresenter;

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        selectedPost = (getArguments() != null && getArguments().containsKey(StartActivity.SELECTED_POST_DATA)) ?
                    (Post)getArguments().getSerializable(StartActivity.SELECTED_POST_DATA) :
                    Post.EMPTY;

        final DetailsRepository detailsRepository = new DetailsRepository(mActivity.getApplication(),selectedPost.userId,selectedPost.id.get());
        detailsPresenter = new DetailsViewPresenter(this,detailsRepository);
    }

    public static DetailsFragment newInstance(Bundle args) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.details_fragment_layout, container, false);
        ButterKnife.bind(this, rootView);

        detailsPresenter.onDetailViewCreated();

        return rootView;
    }

    @Override
    protected void initFragmentUI() {
        if(isAdded()) {
            titleTextView.setText(getResources().getString(R.string.details_screen_title));
        }
        postTitle.setText(selectedPost.title);
        postBody.setText(selectedPost.body.get());

        userAvatar.setImageURI(Uri.parse(selectedPost.userAvatar.get()));
        userName.setOnClickListener(e -> onUserNameClicked());
    }

    private void onUserNameClicked() {
        Intent mapIntent = new Intent(mActivity , MapActivity.class);
        mapIntent.putExtra(StartActivity.SELECTED_USER, selectedUser);
        mapIntent.putExtra(MapActivity.USER_AVATAR_URL, selectedPost.userAvatar.get());
        startActivity(mapIntent);
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        //No implementation here for now.
    }

    @Override
    public void onResume() {
        super.onResume();
        detailsPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        detailsPresenter.unbindView();
    }

    @Override
    public void setPostCommentNumber(List<Comment> postComments) {
        if(isAdded()){
            final int commentNumber = (postComments != null) ? postComments.size() : 0;
            commentNumberTextView.setText(String.format(getResources().getString(R.string.comments_number),Integer.toString(commentNumber)));
        }
    }

    @Override
    public void setUser(User user) {
        if(isAdded()){
            selectedUser = (User)user.deepClone();

            userName.setText(selectedUser.username + "\n(click to see location)");
        }
    }
}
