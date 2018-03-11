package alancasasarevalo.com.weather

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val service: WeatherService = APIToSimplesObjects.getApiRetrofit().create(WeatherService::class.java)
    val serviceForSecondCity: WeatherService = APIToArrayObjects.getApiArrayObjectsRetrofit().create(WeatherService::class.java)

    var realm: Realm? = null
    var citySearchedRealmResult: RealmResults<CitySearched>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatingButton = findViewById<FloatingActionButton>(R.id.activity_add_new_city_floating_action_button)

        realm = Realm.getDefaultInstance()

        citySearchedRealmResult = realm?.where(CitySearched::class.java)?.findAll()


        parseWithObjectsIntoArrayObjects()
        parseWithNormalObjects()


        floatingButton.setOnClickListener {
            showAlertForCreateANewCityTosearch("Por favor agrega una nueva ciudad a buscar")
        }

    }


    fun createNewCitytoSearch(cityName: String){
        realm?.executeTransaction {
            val newCityToSearch = CitySearched(cityName)
            it.copyToRealm(newCityToSearch)
        }
    }

    fun showAlertForCreateANewCityTosearch(title: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        var viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_to_search_new_city, null)

        builder.setView(viewInflated)

        val addButtonOnAlertDialog: EditText = viewInflated.findViewById(R.id.dialog_search_new_city_edit_text)

        builder.setPositiveButton("Add", { dialog, which ->
            val cityToSearch = addButtonOnAlertDialog.text.toString().trim()
            if (cityToSearch.isNotEmpty())
                createNewCitytoSearch(cityToSearch)
            else
                Toast.makeText(applicationContext, "El nombre no es valido para crear el nuevo espacio", Toast.LENGTH_LONG).show()

        })

        builder
                .create()
                .show()
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