package babylon.sniper.com.babylon.api.repos;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import babylon.sniper.com.babylon.api.api_model.ApiCommentParseObject;
import babylon.sniper.com.babylon.api.api_model.ApiPostParseObject;
import babylon.sniper.com.babylon.api.api_model.ApiUserParseObject;
import babylon.sniper.com.babylon.api.db.RealmAddress;
import babylon.sniper.com.babylon.api.db.RealmComment;
import babylon.sniper.com.babylon.api.db.RealmGeo;
import babylon.sniper.com.babylon.api.db.RealmPostCollection;
import babylon.sniper.com.babylon.api.db.RealmUser;
import babylon.sniper.com.babylon.api.interfaces.ApiService;
import babylon.sniper.com.babylon.api.interfaces.IDataRepository;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.api.object_models.User;
import babylon.sniper.com.babylon.api.retrofit.RetrofitServiceProvider;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DataRepository implements IDataRepository {

    private static final String TAG = DataRepository.class.getCanonicalName();
    private final Application application;
    private final ApiService apiService;

    public DataRepository(Application application) {
        this.application = application;
        this.apiService = RetrofitServiceProvider.getApiServiceInstance(application);
    }

    @Override
    public void getPostScreenModules(@NonNull final Callback<Post> callback) {
        final Realm realm = Realm.getInstance(application);

        // First return the current post screen structure from the database
        final RealmResults<RealmPostCollection> allOldPosts = realm
                .where(RealmPostCollection.class)
                .findAll();
        if (allOldPosts != null) {
            callback.onListDataUpdated(Post.from(allOldPosts));
        }
        realm.close();

        final Observable<List<ApiPostParseObject>> postScreenObservable = apiService.getAllPosts().map((apiPostParseObject) -> {
            final Realm realm1 = Realm.getInstance(application);

            // Reset all posts in database if necessary
//            deleteAllDBPosts(realm1);
            realm1.executeTransaction(realm2 -> {
                for(ApiPostParseObject obj : apiPostParseObject){
                    final RealmPostCollection post = new RealmPostCollection.Builder()
                            .userId(obj.getUserId())
                            .id(obj.getId())
                            .body(obj.getBody())
                            .title(obj.getTitle())
                            .build();
                    realm2.copyToRealmOrUpdate(post);
                }
            });

            return apiPostParseObject;

        });
        postScreenObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ApiPostParseObject>>() {
                        @Override
                        public final void onCompleted() {
                            // do nothing
                        }
                        @Override
                        public final void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                            callback.onError(e);
                        }

                        @Override
                        public final void onNext(List<ApiPostParseObject> response) {
                            final Realm realm1 = Realm.getInstance(application);
                            RealmResults<RealmPostCollection> allNewPosts = realm1.where(RealmPostCollection.class).findAll();
                            callback.onListDataUpdated(Post.from(allNewPosts));
                            realm1.close();
                        }
        });
    }

    @Override
    public void getUsers(Callback<User> callback) {
        final Observable<List<ApiUserParseObject>> userObservable = apiService.getAllUsers().map((apiUserParseObject) -> {
            final Realm realm1 = Realm.getInstance(application);

            // Reset all user in database if necessary
//            deleteAllDBUsers(realm1);
            realm1.executeTransaction(realm2 -> {
                for(ApiUserParseObject obj : apiUserParseObject){
                    final RealmUser user = new RealmUser.Builder()
                            .name(obj.getName())
                            .id(obj.getId())
                            .phone(obj.getPhone())
                            .email(obj.getEmail())
                            .username(obj.getUsername())
                            .website(obj.getWebsite())
//                            .company(obj.getCompany())
                            .build();

                    final RealmAddress address = new RealmAddress.Builder()
                            .city(obj.getAddress().getCity())
                            .street(obj.getAddress().getStreet())
                            .suite(obj.getAddress().getSuite())
                            .zipcode(obj.getAddress().getZipcode())
                            .userId(obj.getId())
                            .build();

                    final RealmGeo geo = new RealmGeo.Builder()
                            .lat(obj.getAddress().getGeo().lat)
                            .lng(obj.getAddress().getGeo().lng)
                            .userId(obj.getId())
                            .build();

                    address.setGeo(geo);
                    user.setAddress(address);
                    //insert or update user
                    realm2.copyToRealmOrUpdate(user);
                }
            });

            return apiUserParseObject;

        });
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ApiUserParseObject>>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<ApiUserParseObject> apiUserParseObjects) {
                        Log.e(TAG, apiUserParseObjects.toString());
                    }
                });
    }

    @Override
    public void getComments(Callback<Comment> callback) {
        final Observable<List<ApiCommentParseObject>> commentsObservable = apiService.getAllComments().map((apiCommentsParseObject) -> {
        final Realm realm1 = Realm.getInstance(application);

        // Reset all comments in database if necessary
//            deleteAllDBComments(realm1);
        realm1.executeTransaction(realm2 -> {
            for(ApiCommentParseObject obj : apiCommentsParseObject){
                final RealmComment comment = new RealmComment.Builder()
                        .body(obj.getBody())
                        .id(obj.getId())
                        .email(obj.getEmail())
                        .postId(obj.getPostId())
                        .name(obj.getName())
                        .build();
                realm2.copyToRealmOrUpdate(comment);
            }
        });

        return apiCommentsParseObject;

    });
        commentsObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<ApiCommentParseObject>>() {
                @Override
                public void onCompleted() {
                    // do nothing
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.getMessage());
                    callback.onError(e);
                }

                @Override
                public void onNext(List<ApiCommentParseObject> apiCommentParseObjects) {
                    Log.e(TAG, apiCommentParseObjects.toString());
                }
            });
    }
    //    private void deleteAllDBPosts(Realm realm) {
//        RealmResults<RealmPostCollection> existingPosts = realm.where(RealmPostCollection.class).findAll();
//
//        realm.beginTransaction();
//        existingPosts.clear();
//        realm.commitTransaction();
//    }
//    private void deleteAllDBUsers(Realm realm) {
//        RealmResults<RealmUser> existingUsers = realm.where(RealmUser.class).findAll();
//
//        realm.beginTransaction();
//        existingUsers.clear();
//        realm.commitTransaction();
//    }
//    private void deleteAllDBComments(Realm realm) {
//        RealmResults<RealmComment> existingComments = realm.where(RealmComment.class).findAll();
//
//        realm.beginTransaction();
//        existingComments.clear();
//        realm.commitTransaction();
//    }
}
