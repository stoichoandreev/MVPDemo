package babylon.sniper.com.babylon.api.interfaces;

import java.util.List;

import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.User;

/**
 * Details Data Repository interface.
 */
public interface IDetailsRepository {

    interface Callback<T> {
        void onDataUpdated(T data);
        void onError(Throwable throwable);
    }

    void getUserDetails(Callback<User> callback);

    /**
     *
     * @param callback
     */
    void getPostComments(Callback<List<Comment>> callback);

}
