package babylon.sniper.com.babylon.api.object_models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fernandocejas.arrow.optional.Optional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import babylon.sniper.com.babylon.api.db.RealmPostCollection;
import babylon.sniper.com.babylon.constants.Preferences;
import io.realm.RealmResults;

public class Post implements Serializable{

    @NonNull
    public final int userId;
    @NonNull
    public final Optional<Integer> id;
    @NonNull
    public final String title;
    @NonNull
    public final Optional<String> body;
    @NonNull
    public final Optional<String> userAvatar;

    public static final Post EMPTY = new Post(new Builder().userId(-1).body("").id(-1).title(""));

    /*package*/
    @Nullable
    public static List<Post> from(RealmResults<RealmPostCollection> dbCollectionItems) {
        List<Post> collectionItems = new ArrayList<>(dbCollectionItems.size());
        for (RealmPostCollection dbCollectionItem : dbCollectionItems) {
            collectionItems.add(from(dbCollectionItem));
        }
        return collectionItems;

    }
    @Nullable
    public static Post from(RealmPostCollection dbCollectionItem) {
        if (dbCollectionItem == null) {
            return null;
        }
        return new Builder()
                .userId(dbCollectionItem.getUserId())
                .id(dbCollectionItem.getId())
                .title(dbCollectionItem.getTitle())
                .body(dbCollectionItem.getBody())
                .userAvatar(Preferences.AVATAR_URL+dbCollectionItem.getUserId())
                .build();
    }

    private Post(Builder builder) {
        userId = builder.userId;
        id = Optional.fromNullable(builder.id);
        title = builder.title;
        body = Optional.fromNullable(builder.body);
        userAvatar = Optional.fromNullable(builder.userAvatar);
    }


    private static final class Builder {
        private int userId;
        private int id;
        private String title;
        private String body;
        private String userAvatar;

        public Builder userId(int val) {
            userId = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }
        public Builder userAvatar(String val) {
            userAvatar = val;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
