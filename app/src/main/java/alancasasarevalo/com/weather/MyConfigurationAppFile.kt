package alancasasarevalo.com.weather

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import java.util.concurrent.atomic.AtomicInteger

class MyConfigurationAppFile : Application(){

    var CITYSEARCHID = AtomicInteger()
    var WEATHERID = AtomicInteger()

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        setRealmDefaultConfiguration()

        val realm = Realm.getDefaultInstance()

        CITYSEARCHID = getElementByID(realm, CitySearched::class.java)!!
        WEATHERID = getElementByID(realm, Weather::class.java)!!
        realm.close()
    }

    private fun <T : RealmObject> getElementByID(realm: Realm, anyclass: Class<T>): AtomicInteger {
        val results = realm.where(anyclass).findAll()
        if (results.size > 0) AtomicInteger(results.max("id")!!.toInt()) else AtomicInteger()
        return if (results.size > 0) {
            AtomicInteger(results.max("id")!!.toInt())
        }else{
            val newID = AtomicInteger()
            newID
        }



    }

    private fun setRealmDefaultConfiguration(){
        val configuration = RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(configuration)
    }
}
