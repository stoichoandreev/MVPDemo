package babylon.sniper.com.babylon;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

public class BabylonApp extends Application {

    public static boolean isOnline;

    public void onCreate() {
        super.onCreate();

        Fresco.initialize(getApplicationContext());

        //Use Stetho only for debug
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }
}