package alancasasarevalo.com.weather

import alancasasarevalo.com.weather.adapters.CitySearchedAdapter
import alancasasarevalo.com.weather.model.CitySearched
import alancasasarevalo.com.weather.model.Weather
import alancasasarevalo.com.weather.network.APIToArrayObjects
import alancasasarevalo.com.weather.network.APIToSimplesObjects
import alancasasarevalo.com.weather.network.WeatherService
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RealmChangeListener<RealmResults<CitySearched>>, AdapterView.OnItemClickListener {



    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        // TODO: Hacer algo para ir a la siguiente actividad
//        val intent = Intent(this, NoteActivity::class.java)
//        intent.putExtra("id", boardsRealmResult?.get(position)?.id)
//        startActivity(intent)
    }

    var citySearched:CitySearched? = null

    val service: WeatherService = APIToSimplesObjects.getApiRetrofit().create(WeatherService::class.java)
    val serviceForSecondCity: WeatherService = APIToArrayObjects.getApiArrayObjectsRetrofit().create(WeatherService::class.java)

    var realm: Realm? = null
    var citySearchedRealmResult: RealmResults<CitySearched>? = null

    var listView: ListView? = null
    var citySearchedAdapter: CitySearchedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val floatingButton = findViewById<FloatingActionButton>(R.id.activity_add_new_city_floating_action_button)

        realm = Realm.getDefaultInstance()

        citySearchedRealmResult = realm?.where(CitySearched::class.java)?.findAll()
//
//        realm?.executeTransaction {
//            it.deleteAll()
//        }




//        parseWithObjectsIntoArrayObjects()

        citySearchedAdapter = CitySearchedAdapter(citySearchedRealmResult as List<CitySearched>, this, R.layout.list_view_city_item)

        listView = findViewById(R.id.activity_board_list_view)

        listView?.adapter = citySearchedAdapter


        floatingButton.setOnClickListener {
            showAlertForCreateANewCityTosearch("Por favor agrega una nueva ciudad a buscar")
        }

    }

    fun createNewCitytoSearch(cityName: String) {

        if (cityName.isEmpty()) {

        } else {
            parseWithObjectsIntoArrayObjects(cityName)
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

    fun parseWithObjectsIntoArrayObjects(cityToSearch: String) {

        if (cityToSearch.isEmpty()) {

        } else {

            val cityCall: Call<CitySearched> = serviceForSecondCity.getACityToSearch(cityToSearch,
                    "20",
                    "0",
                    "en",
                    true,
                    "FULL",
                    "ilgeonamessample")

            cityCall.enqueue(object : Callback<CitySearched> {

                override fun onResponse(call: Call<CitySearched>?, response: Response<CitySearched>?) {
                    val cityParsed = response?.body()
                    citySearched = cityParsed
                    realm?.executeTransaction {
                        it.copyToRealm(citySearched)
                    }
                }

                override fun onFailure(call: Call<CitySearched>?, t: Throwable?) {
                    Toast.makeText(MainActivity().baseContext, "Error en el parseo de GSON con Retrofit", Toast.LENGTH_LONG).show()
                }

            })

        }

    }

    fun parseWithNormalObjects(citySearched: CitySearched) {

        if (citySearched == null) {

        } else {
            val cityCall: Call<Weather> = service.getWeatherForACity(
                    citySearched.north!!,
                    citySearched.south!!,
                    citySearched.east!!,
                    citySearched.west!!,
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
    override fun onChange(t: RealmResults<CitySearched>?) {
        citySearchedAdapter?.notifyDataSetChanged()
    }

}