package babylon.sniper.com.babylon.api.object_models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fernandocejas.arrow.optional.Optional;

import java.util.ArrayList;
import java.util.List;

import babylon.sniper.com.babylon.api.db.RealmComment;
import babylon.sniper.com.babylon.api.db.RealmPostCollection;
import io.realm.RealmResults;

public class Comment {

    @NonNull
    public final int postId;
    @NonNull
    public final int id;
    @NonNull
    public final String name;
    @NonNull
    public final String email;
    @NonNull
    public final Optional<String> body;

    /*package*/
    @Nullable
    public static List<Comment> from(RealmResults<RealmComment> dbCollectionItems) {
        List<Comment> collectionItems = new ArrayList<>(dbCollectionItems.size());
        for (RealmComment dbCollectionItem : dbCollectionItems) {
            collectionItems.add(from(dbCollectionItem));
        }
        return collectionItems;

    }
    @Nullable
    public static Comment from(RealmComment dbCollectionItem) {
        if (dbCollectionItem == null) {
            return null;
        }
        return new Builder()
                .postId(dbCollectionItem.getPostId())
                .id(dbCollectionItem.getId())
                .name(dbCollectionItem.getName())
                .email(dbCollectionItem.getEmail())
                .body(dbCollectionItem.getBody())
                .build();
    }

    private Comment(Builder builder) {
        postId = builder.postId;
        id = builder.id;
        name = builder.name;
        email = builder.email;
        body = Optional.fromNullable(builder.body);
    }


    private static final class Builder {
        private int postId;
        private int id;
        private String name;
        private String email;
        private String body;

        public Builder postId(int val) {
            postId = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }
        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
