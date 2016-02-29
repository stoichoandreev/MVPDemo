package babylon.sniper.com.babylon.api.object_models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import babylon.sniper.com.babylon.api.db.RealmUser;

public class User implements Serializable{

    @NonNull
    public final Integer id;
    @NonNull
    public final String name;
    @NonNull
    public final String username;
    @NonNull
    public final String email;

    @NonNull
    public final Address address;
    @NonNull
    public final String phone;
    public final String website;
    private static final String EMPTY_STRING = "";

    public static final User EMPTY = new User(new Builder()
                                        .id(-1)
                                        .name(EMPTY_STRING)
                                        .username(EMPTY_STRING)
                                        .email(EMPTY_STRING)
                                        .phone(EMPTY_STRING)
                                        .website(EMPTY_STRING));


    private User(Builder builder) {
        id = builder.id;
        name = builder.name;
        username = builder.username;
        email = builder.email;
        phone = builder.phone;
        website = builder.website;
        address = builder.address;
    }
    @Nullable
    public static User from(RealmUser dbCollectionItem) {
        if (dbCollectionItem == null) {
            return null;
        }
        return new Builder()
                .id(dbCollectionItem.getId())
                .email(dbCollectionItem.getEmail())
                .name(dbCollectionItem.getName())
                .username(dbCollectionItem.getUsername())
                .address(Address.from(dbCollectionItem.getAddress()))
                .website(dbCollectionItem.getWebsite())
                .phone(dbCollectionItem.getPhone())
                .build();
    }


    private static final class Builder {
        private Integer id;
        private String name;
        private String username;
        private String email;
        private String phone;
        private String website;
        private Address address;

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

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder website(String val) {
            website = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
    public Object deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
