package alancasasarevalo.com.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIToSimplesObjects{

    companion object {
        val BASE_URL: String = "http://api.geonames.org/"

        var retrofit: Retrofit? = null

        fun getApiRetrofit() : Retrofit {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }

            return retrofit!!
        }
    }

}