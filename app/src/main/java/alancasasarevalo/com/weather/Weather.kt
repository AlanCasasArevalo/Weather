package alancasasarevalo.com.weather

import java.io.Serializable

class Weather (
        val stationName: String,
        val temperature : Int,
        val humidity: Int,
        val windDirection:  Int,
        val windSpeed: Int,
        val latitude: Double,
        val Longitude: Double
) : Serializable
