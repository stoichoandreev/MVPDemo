package babylon.sniper.com.babylon.utils;

import java.util.List;

import babylon.sniper.com.babylon.api.db.RealmString;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Static utility class for utility operations.
 */
public class Util {

    /**
     * Converts a list of Strings into a RealmList of {@link RealmString} for persistence.
     *
     * @param stringList list of Strings
     * @return realm list of realm Strings
     */
    public static RealmList<RealmString> convertToRealmStrings(List<String> stringList) {
        RealmList<RealmString> realmList = new RealmList<>();
        for (String str : stringList) {

            RealmString realmStr = new RealmString(str);
            realmList.add(realmStr);
        }
        return realmList;
    }

    public static RealmList<RealmString> copyToRealm(Realm realm, List<String> stringList) {
        RealmList<RealmString> realmList = convertToRealmStrings(stringList);
        List<RealmString> list = realm.copyToRealmOrUpdate(realmList);
        final RealmList<RealmString> persisted = new RealmList<>();
        persisted.addAll(list);
        return persisted;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
