package babylon.sniper.com.babylon.api.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmPostCollection extends RealmObject {

    @PrimaryKey
    private int id;
    private int userId;
    private String title;
    private String body;

    public RealmPostCollection() {

    }

    private RealmPostCollection(Builder builder) {
        setId(builder.id);
        setUserId(builder.userId);
        setBody(builder.body);
        setTitle(builder.title);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static final class Builder {
        private int id;
        private int userId;
        private String title;
        private String body;

        public Builder() {

        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder userId(int val) {
            userId = val;
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

        public RealmPostCollection build() {
            return new RealmPostCollection(this);
        }
    }
}
