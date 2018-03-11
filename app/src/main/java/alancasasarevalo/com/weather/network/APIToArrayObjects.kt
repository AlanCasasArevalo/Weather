package alancasasarevalo.com.weather.network

import alancasasarevalo.com.weather.deserializer.MyDeserializer
import alancasasarevalo.com.weather.model.CitySearched
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIToArrayObjects{

    companion object {
        val BASE_URL: String = "http://api.geonames.org/"

        var retrofit: Retrofit? = null

        fun getApiArrayObjectsRetrofit() : Retrofit {
            if(retrofit == null){

                val builder = GsonBuilder()
                builder.registerTypeAdapter(CitySearched::class.java, MyDeserializer())

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(builder.create()))
                        .build()
            }

            return retrofit!!
        }
    }

}