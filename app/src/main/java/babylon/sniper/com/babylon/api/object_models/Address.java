package babylon.sniper.com.babylon.api.object_models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fernandocejas.arrow.optional.Optional;

import java.io.Serializable;

import babylon.sniper.com.babylon.api.db.RealmAddress;

public class Address implements Serializable{

    @NonNull
    public final String street;
    @NonNull /*package*/ final String suite;
    @NonNull
    public final Optional<String> city;
    @NonNull
    public final Optional<String> zipcode;
    @NonNull
    public final Geo geo;


    private Address(Builder builder) {
        street = builder.street;
        suite = builder.suite;
        city = Optional.fromNullable(builder.city);
        zipcode = Optional.fromNullable(builder.zipcode);
        geo = builder.geo;
    }
    @Nullable
    public static Address from(RealmAddress dbCollectionItem) {
        if (dbCollectionItem == null) {
            return null;
        }
        return new Builder()
                .street(dbCollectionItem.getStreet())
                .city(dbCollectionItem.getCity())
                .suite(dbCollectionItem.getSuite())
                .zipcode(dbCollectionItem.getZipcode())
                .geo(Geo.from(dbCollectionItem.getGeo()))
                .build();
    }

    public static final class Builder {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        public Builder() {

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
        public Builder geo(Geo val) {
            geo = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
