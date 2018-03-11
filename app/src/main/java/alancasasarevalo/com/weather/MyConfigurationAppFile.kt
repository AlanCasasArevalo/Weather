package alancasasarevalo.com.weather

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyConfigurationAppFile : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        setRealmDefaultConfiguration()

        val realm = Realm.getDefaultInstance()
        realm.close()
    }

    private fun setRealmDefaultConfiguration() {
        val configuration = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(configuration)
    }

}

