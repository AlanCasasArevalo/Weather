package alancasasarevalo.com.weather.model

import io.realm.RealmObject
import java.io.Serializable

open class CitySearched(
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




