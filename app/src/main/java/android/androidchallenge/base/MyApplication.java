package android.androidchallenge.base;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by rob on 9/22/17.
 */

public class MyApplication extends Application {

    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("ANDROID_CHALLENGE.realm")
                .schemaVersion(1)
                .build();
        Realm.getInstance(config);
        Realm.setDefaultConfiguration(config);
    }

    public static Context getMyApplicationContext() {
        return context.getApplicationContext();
    }
}
