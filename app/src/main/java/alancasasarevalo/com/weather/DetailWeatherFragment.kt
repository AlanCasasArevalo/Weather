package alancasasarevalo.com.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailWeatherFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DetailWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailWeatherFragment : Fragment() {

    companion object {
        fun newInstance () : DetailWeatherFragment {
            var fragment = DetailWeatherFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_detail_weather, container, false)
    }

}