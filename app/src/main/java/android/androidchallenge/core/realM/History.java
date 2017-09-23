package android.androidchallenge.core.realM;

import android.view.ViewStructure;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rob on 9/23/17.
 */

public class History extends RealmObject {

    @PrimaryKey
    private Long time;
    private String text;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static List<History> getAllHistory() {
        return Realm.getDefaultInstance().where(History.class).findAll();
    }

    public static void addHistoryToDB(String text) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<History> histories = getAllHistory();
        for (History history : histories) {
            if (history.text.equals(text)) {
                history.deleteFromRealm();
            }
        }
        History history = new History();
        history.text = text;
        history.time = System.currentTimeMillis();
        realm.copyToRealmOrUpdate(history);
        realm.commitTransaction();
    }
}
