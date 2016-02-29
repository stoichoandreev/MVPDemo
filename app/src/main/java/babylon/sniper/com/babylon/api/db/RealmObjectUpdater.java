package babylon.sniper.com.babylon.api.db;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * This class is a helper to keep an existing Realm object if it already exists in the database
 * or to copy a given object to the Realm if it doesn't exist.
 * <p/>
 * Use it like this:
 * final RealmUser realmUser = new RealmUser.Builder(apiItem.userId).build();
 * final RealmUser realmUser = RealmObjectUpdater
 *                           .with(realm, RealmUser.class)
 *                           .equalTo("userId", apiItem.userId)
 *                           .keepExistingOrCopyToRealm(realmUser);
 *
 * @param <T> type of Realm object
 */
public class RealmObjectUpdater<T extends RealmObject> {

    private final Realm realm;
    private final Class<T> type;
    private RealmQuery<T> query;

    public RealmObjectUpdater(Realm realm, Class<T> type) {
        this.realm = realm;
        this.type = type;
    }

    /**
     * Returns a RealmObjectUpdater instance
     *
     * @param realm Realm instance
     * @param type ImageType class
     * @param <T> ImageType of object
     * @return RealmObjectUpdater instance
     */
    public static <T extends RealmObject> RealmObjectUpdater<T> with(Realm realm, Class<T> type) {
        return new RealmObjectUpdater<>(realm, type);
    }

    /**
     * Specify the object to look for using its primary key
     *
     * @param field Primary key
     * @param value the value to look for
     * @return RealmObjectUpdater
     */
    public RealmObjectUpdater<T> equalTo(String field, String value) {
        query = realm.where(type).equalTo(field, value);
        return this;
    }

    /**
     * If an object that with the given key exists in the Realm it is returned.
     * If not it is copied to the Realm and the Realm instance is returned.
     *
     * @param newObject new instance to be persisted if necessary
     * @return persisted Realm object
     */
    public T keepExistingOrCopyToRealm(T newObject) {
        if (query == null) {
            throw new UnsupportedOperationException("You must call 'equalTo' first");
        }
        final T persisted;
        final T existing = query.findFirst();
        if (null == existing) {
            persisted = realm.copyToRealmOrUpdate(newObject);
        } else {
            persisted = existing;
        }
        return persisted;
    }
}
