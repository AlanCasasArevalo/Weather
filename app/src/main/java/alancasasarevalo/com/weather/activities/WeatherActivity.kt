package alancasasarevalo.com.weather.activities

import alancasasarevalo.com.weather.DetailWeatherFragment
import alancasasarevalo.com.weather.GoogleMapFragment
import alancasasarevalo.com.weather.R
import alancasasarevalo.com.weather.model.CitySearched
import alancasasarevalo.com.weather.model.Weather
import alancasasarevalo.com.weather.network.APIToSimplesObjects
import alancasasarevalo.com.weather.network.WeatherService
import alancasasarevalo.com.weather.thread.DispatchOnMainThread
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity() {

    val service: WeatherService = APIToSimplesObjects.getApiRetrofit().create(WeatherService::class.java)
    var realm: Realm? = null
    var cityName: String? = null
    var citySearched: CitySearched? = null
    var weather: Weather? = null
    var east: String? = null
    var west: String? = null
    var south: String? = null
    var north: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        realm = Realm.getDefaultInstance()

        if (intent.extras != null) {
            cityName = intent.extras.getString("name")
            citySearched = realm?.where(CitySearched::class.java)?.equalTo("name", cityName)?.findFirst()

            east = citySearched?.east
            west = citySearched?.west
            south = citySearched?.south
            north = citySearched?.north

            if (east != null && west != null && south != null && north != null) {
//                parseWithNormalObjects(east!!, west!!, south!!, north!!)
            }

            parseWithNormalObj()
        }

        if (supportFragmentManager.findFragmentById(R.id.weather_activity_google_fragment) == null || supportFragmentManager.findFragmentById(R.id.weather_activity_list_fragment) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.weather_activity_google_fragment, GoogleMapFragment.newInstance())
                    .add(R.id.weather_activity_list_fragment, DetailWeatherFragment.newInstance())
                    .commit()
        }

    }

    fun parseWithNormalObjects(east: String, west: String, south: String, north: String) {

        Thread(Runnable {

            DispatchOnMainThread(Runnable {
                val cityCall: Call<Weather> = service.getWeatherForACity(
                        north,
                        south,
                        east,
                        west,
                        "ilgeonamessample"
                )
                cityCall.enqueue(object : Callback<Weather> {

                    override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                        val weatherParsed = response?.body()
                        weather = weatherParsed!!

                        weatherParsed.humidity
                        weatherParsed.temperature


                        weather?.temperature
                        weather?.humidity


                        realm?.executeTransaction {

                            weather?.latitude
                            weather?.Longitude
                            weather?.humidity
                            weather?.temperature

                            it.copyToRealm(weather)
                        }

                        realm?.close()

                    }

                    override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                        Toast.makeText(CitiesActivity().baseContext, "Error en el parseo de GSON con Retrofit", Toast.LENGTH_LONG).show()
                    }

                })

            })

        }).run()


    }

    fun parseWithNormalObj(){
        val cityCall : Call<Weather> = service.getWeatherForACity("41.16", "39.88", "-3.05","-4.57","ilgeonamessample")

        cityCall.enqueue(object : Callback<Weather>{

            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                val cityParsed = response?.body()
            }

            override fun onFailure(call: Call<Weather>?, t: Throwable?) {
                Toast.makeText(WeatherActivity().baseContext, "Error en el parseo de GSON con Retrofit",Toast.LENGTH_LONG).show()
            }

        })

    }

}





















