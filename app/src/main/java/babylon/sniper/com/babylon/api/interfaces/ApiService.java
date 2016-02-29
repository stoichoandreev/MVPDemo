package babylon.sniper.com.babylon.api.interfaces;


import java.util.List;

import babylon.sniper.com.babylon.api.api_model.ApiCommentParseObject;
import babylon.sniper.com.babylon.api.api_model.ApiPostParseObject;
import babylon.sniper.com.babylon.api.api_model.ApiUserParseObject;
import babylon.sniper.com.babylon.constants.Preferences;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Retrofit Api Service interface.
 */
public interface ApiService {

    @GET(Preferences.POSTS_ENDPOINT)
    Observable<List<ApiPostParseObject>> getAllPosts();

    @GET(Preferences.USERS_ENDPOINT)
    Observable<List<ApiUserParseObject>> getAllUsers();

    @GET(Preferences.COMMENTS_ENDPOINT)
    Observable<List<ApiCommentParseObject>> getAllComments();

}
