package babylon.sniper.com.babylon.api.db;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGeo extends RealmObject {

    @PrimaryKey
    private int userId;
    private String lat;
    private String lng;

    public RealmGeo() {
    }

    private RealmGeo(Builder builder) {
        setUserId(builder.userId);
        setLat(builder.lat);
        setLng(builder.lng);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public static final class Builder {
        private int userId;
        private String lat;
        private String lng;

        public Builder() {

        }

        public Builder userId(int val) {
            userId = val;
            return this;
        }

        public Builder lat(String val) {
            lat = val;
            return this;
        }

        public Builder lng(String val) {
            lng = val;
            return this;
        }

        public RealmGeo build() {
            return new RealmGeo(this);
        }
    }
}
