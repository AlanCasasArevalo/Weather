package alancasasarevalo.com.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val service: WeatherService = APIToSimplesObjects.getApiRetrofit().create(WeatherService::class.java)
    val serviceForSecondCity: WeatherService = APIToArrayObjects.getApiArrayObjectsRetrofit().create(WeatherService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseWithObjectsIntoArrayObjects()
        parseWithNormalObjects()

    }

    fun parseWithObjectsIntoArrayObjects(){
        val cityCall : Call<CitySearched> = serviceForSecondCity.getACityToSearch("Toledo",
                "20",
                "0",
                "en",
                true,
                "FULL",
                "ilgeonamessample")

        cityCall.enqueue(object : Callback<CitySearched> {

            override fun onResponse(call: Call<CitySearched>?, response: Response<CitySearched>?) {
                val cityParsed = response?.body()
            }

            override fun onFailure(call: Call<CitySearched>?, t: Throwable?) {
                Toast.makeText(MainActivity().baseContext, "Error en el parseo de GSON con Retrofit", Toast.LENGTH_LONG).show()
            }

        })

    }

    fun parseWithNormalObjects(){
        val cityCall : Call<Weather> = service.getWeatherForACity(
                "-24.668868272392903",
                "-24.758353947607095",
                "-53.69381916647705",
                "-53.792291953522955",
                "ilgeonamessample"
        )

        cityCall.enqueue(object : Callback<Weather> {

            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                val cityParsed = response?.body()
            }

            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                Toast.makeText(MainActivity().baseContext, "Error en el parseo de GSON con Retrofit", Toast.LENGTH_LONG).show()
            }

        })
    }

}