package babylon.sniper.com.babylon.api.db;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmUser extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private RealmAddress address;


    public RealmUser() {
    }

    private RealmUser(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setUsername(builder.username);
        setEmail(builder.email);
        setPhone(builder.phone);
        setWebsite(builder.website);
        setAddress(builder.address);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public RealmAddress getAddress() {
        return address;
    }

    public void setAddress(RealmAddress address) {
        this.address = address;
    }

    public static final class Builder {
        private int id;
        private String name;
        private String username;
        private String phone;
        private String website;
        private String email;
        private RealmAddress address;

        public Builder() {

        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }
        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder website(String val) {
            website = val;
            return this;
        }

        public Builder address(RealmAddress val) {
            address = val;
            return this;
        }

        public RealmUser build() {
            return new RealmUser(this);
        }
    }
}
