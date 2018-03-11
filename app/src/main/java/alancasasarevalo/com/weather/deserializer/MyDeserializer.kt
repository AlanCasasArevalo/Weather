package alancasasarevalo.com.weather.deserializer

import alancasasarevalo.com.weather.CitySearched
import alancasasarevalo.com.weather.Weather
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MyDeserializer : JsonDeserializer<CitySearched> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CitySearched {

        var geonames = json?.asJsonObject?.get("geonames")?.asJsonArray
        val name = geonames?.asJsonArray?.get(1)?.asJsonObject?.get("name")?.asString
        val bbox = geonames?.asJsonArray?.get(1)?.asJsonObject?.get("bbox")
        val east = bbox?.asJsonObject?.get("east")?.asString
        val south = bbox?.asJsonObject?.get("south")?.asString
        val north = bbox?.asJsonObject?.get("north")?.asString
        val west = bbox?.asJsonObject?.get("west")?.asString

        val citySarched = CitySearched(
                name!!,
                east!!,
                south!!,
                north!!,
                west!!
        )

        return  citySarched
    }
}