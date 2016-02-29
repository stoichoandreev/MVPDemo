package babylon.sniper.com.babylon.api.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * String realm object to be used in a RealmList.
 */
public class RealmString extends RealmObject {

    @PrimaryKey
    private String string;

    public RealmString(String value) {
        this.string = value;
    }

    public RealmString() {

    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
