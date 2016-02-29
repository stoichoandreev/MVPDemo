package babylon.sniper.com.babylon.ui.interfaces;

import babylon.sniper.com.babylon.api.object_models.Post;

public interface PostAdapterListener {
    void onPostSelected(Post postData);
    void onDataLoad();
    void onDataError(int message);
}
