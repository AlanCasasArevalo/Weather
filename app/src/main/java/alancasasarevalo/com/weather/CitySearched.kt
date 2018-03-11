package alancasasarevalo.com.weather

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class CitySearched(
        @PrimaryKey
                          var name: String? = null,
                          var east : String? = null,
                          var south: String? = null,
                          var north: String? = null,
                          var west: String? = null
) :  RealmObject(), Serializable{

    constructor(name: String) : this() {
        this.name = name
    }
}




