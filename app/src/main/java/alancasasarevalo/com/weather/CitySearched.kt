package alancasasarevalo.com.weather

import java.io.Serializable

data class CitySearched(
                          val name: String,
                          val east : String,
                          val south: String,
                          val north: String,
                          val west: String
) : Serializable

