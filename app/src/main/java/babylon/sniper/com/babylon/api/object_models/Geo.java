package babylon.sniper.com.babylon.api.object_models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import babylon.sniper.com.babylon.api.db.RealmGeo;

public class Geo implements Serializable{

//    @NonNull /*package*/ public final String lat;
//    @NonNull /*package*/ final String lng;
    @NonNull
    public final String lat;
    @NonNull
    public final String lng;

    private Geo(Builder builder) {
        lat = builder.lat;
        lng = builder.lng;
    }

    @Nullable
    public static Geo from(RealmGeo dbCollectionItem) {
        if (dbCollectionItem == null) {
            return null;
        }
        return new Builder()
                .lat(dbCollectionItem.getLat())
                .lng(dbCollectionItem.getLng())
                .build();
    }
    private static final class Builder {
        private String lat;
        private String lng;

        public Builder() {

        }

        public Builder lat(String val) {
            lat = val;
            return this;
        }

        public Builder lng(String val) {
            lng = val;
            return this;
        }

        public Geo build() {
            return new Geo(this);
        }
    }
}
