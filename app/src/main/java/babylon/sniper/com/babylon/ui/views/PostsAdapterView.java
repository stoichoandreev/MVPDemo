package babylon.sniper.com.babylon.ui.views;

import android.support.annotation.StringRes;

import java.util.List;

import babylon.sniper.com.babylon.api.object_models.Post;

public interface PostsAdapterView {

    void setPostsModule(List<Post> allPosts);
    void onErrorOccurred(@StringRes int errorMessage);

}
