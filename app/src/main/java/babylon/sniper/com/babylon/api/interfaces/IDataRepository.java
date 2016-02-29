package babylon.sniper.com.babylon.api.interfaces;

import java.util.List;

import babylon.sniper.com.babylon.api.api_model.ApiCommentParseObject;
import babylon.sniper.com.babylon.api.api_model.ApiUserParseObject;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.api.object_models.User;

/**
 * Data Repository interface.
 */
public interface IDataRepository {

    interface Callback<T> {
        void onDataUpdated(T data);
        void onListDataUpdated(List<T> data);
        void onError(Throwable throwable);
    }

    void getPostScreenModules(Callback<Post> callback);

    /**
     * Get all users from API
     *
     * @param callback   callback when it completes
     */
    void getUsers(Callback<User> callback);

    /**
     * Get all Comments from API
     *
     * @param callback   callback when it completes
     */
    void getComments(Callback<Comment> callback);

}
