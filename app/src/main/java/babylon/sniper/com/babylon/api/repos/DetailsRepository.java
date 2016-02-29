package babylon.sniper.com.babylon.api.repos;

import android.app.Application;

import java.util.List;

import babylon.sniper.com.babylon.api.db.RealmComment;
import babylon.sniper.com.babylon.api.db.RealmUser;
import babylon.sniper.com.babylon.api.interfaces.IDetailsRepository;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.User;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class DetailsRepository implements IDetailsRepository {

    private final Application application;
    private final int userId;
    private final int postId;

    public DetailsRepository(Application application, int userId, int postId) {
        this.application = application;
        this.userId = userId;
        this.postId = postId;
    }

    @Override
    public void getUserDetails(Callback<User> callback) {

        Observable<User> userObservable = Observable.create(
                (Observable.OnSubscribe<User>) sub -> {
                    final Realm realm = Realm.getInstance(application);
                    final RealmUser user = realm.where(RealmUser.class)
                            .equalTo("id",userId)
                            .findFirst();
                    if (user != null) {
                        final User u = User.from(user);

                        sub.onNext(u);
                    }
                    realm.close();
                    sub.onCompleted();
                }
        );
        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    callback.onDataUpdated(user);
                });
    }

    @Override
    public void getPostComments(Callback<List<Comment>> callback) {
        Observable<List<Comment>> userObservable = Observable.create(
                (Observable.OnSubscribe<List<Comment>>) sub -> {
                    final Realm realm = Realm.getInstance(application);
                    final RealmResults<RealmComment> allComments = realm.where(RealmComment.class)
                            .equalTo("postId",postId)
                            .findAll();
                    if (allComments != null) {
                        final List<Comment> com = Comment.from(allComments);

                        sub.onNext(com);
                    }
                    realm.close();
                    sub.onCompleted();
                }
        );
        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                    callback.onDataUpdated(comments);
                });
    }
}
