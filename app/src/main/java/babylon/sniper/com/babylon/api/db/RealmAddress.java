package babylon.sniper.com.babylon.api.db;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmAddress extends RealmObject {

    @PrimaryKey
    private int userId;
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private RealmGeo geo;

    public RealmAddress() {
    }

    private RealmAddress(Builder builder) {
        setUserId(builder.userId);
        setCity(builder.city);
        setStreet(builder.street);
        setSuite(builder.suite);
        setZipcode(builder.zipcode);
        setGeo(builder.geo);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public RealmGeo getGeo() {
        return geo;
    }

    public void setGeo(RealmGeo geo) {
        this.geo = geo;
    }


    public static final class Builder {
        private int userId;
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private RealmGeo geo;

        public Builder() {

        }

        public Builder userId(int val) {
            userId = val;
            return this;
        }

        public Builder street(String val) {
            street = val;
            return this;
        }

        public Builder suite(String val) {
            suite = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }
        public Builder zipcode(String val) {
            zipcode = val;
            return this;
        }
        public Builder geo(RealmGeo val) {
            geo = val;
            return this;
        }

        public RealmAddress build() {
            return new RealmAddress(this);
        }
    }
}
