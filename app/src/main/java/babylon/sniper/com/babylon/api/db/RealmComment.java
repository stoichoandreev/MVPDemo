package babylon.sniper.com.babylon.api.db;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmComment extends RealmObject {

    @PrimaryKey
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

    public RealmComment() {
    }

    private RealmComment(Builder builder) {
        setId(builder.id);
        setPostId(builder.postId);
        setName(builder.name);
        setEmail(builder.email);
        setBody(builder.body);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static final class Builder {
        private int id;
        private int postId;
        private String name;
        private String email;
        private String body;

        public Builder() {

        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }
        public Builder postId(Integer val) {
            postId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public RealmComment build() {
            return new RealmComment(this);
        }
    }
}
