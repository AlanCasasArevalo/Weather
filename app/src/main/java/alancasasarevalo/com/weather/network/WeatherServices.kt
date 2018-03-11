package alancasasarevalo.com.weather.network

import alancasasarevalo.com.weather.model.CitySearched
import alancasasarevalo.com.weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weatherJSON")
    fun getWeatherForACity(@Query("north") north: String,
                           @Query("south") south: String,
                           @Query("east") east: String,
                           @Query("west") west: String,
                           @Query("username") ilgeonamessample: String
    ): Call<Weather>

    @GET("searchJSON")
    fun getACityToSearch(@Query("q") cityName: String,
                         @Query("maxRows") maxRows: String = "20",
                         @Query("startRow") startRow: String = "0",
                         @Query("lang") lang: String = "en",
                         @Query("isNameRequired") isNameRequired:Boolean =true,
                         @Query("style") style: String = "FULL",
                         @Query("username") ilgeonamessample: String
    ): Call<CitySearched>

}