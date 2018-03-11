package alancasasarevalo.com.weather.model

import io.realm.RealmObject
import java.io.Serializable

open class Weather (
        var stationName: String? = null,
        var temperature : Int? = null,
        var humidity: Int? = null,
        var windDirection:  Int? = null,
        var windSpeed: Int? = null,
        var latitude: Double? = null,
        var Longitude: Double? = null
) :  RealmObject(), Serializable  {
    constructor(stationName: String) : this (){
        this.stationName = stationName
    }
}

