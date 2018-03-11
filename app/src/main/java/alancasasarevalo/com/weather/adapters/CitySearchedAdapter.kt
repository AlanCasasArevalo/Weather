package alancasasarevalo.com.weather.adapters

import alancasasarevalo.com.weather.R
import alancasasarevalo.com.weather.model.CitySearched
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.lang.ref.WeakReference

class CitySearchedAdapter (val citiesSearched: List<CitySearched>, context: Context, val layout: Int) : BaseAdapter() {

    val weakReference = WeakReference<Context>(context)

    override fun getItem(position: Int): Any {
        return citiesSearched[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return citiesSearched.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val citySearched = citiesSearched[position]

        val viewHolder: ViewHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(weakReference.get()).inflate(layout, null)
            viewHolder = ViewHolder()
            viewHolder.name = convertView.findViewById<View>(R.id.city_name_text_view) as TextView

            viewHolder.east = convertView.findViewById<View>(R.id.east_value_text_view) as TextView
            convertView.findViewById<View>(R.id.east_text_view) as TextView

            viewHolder.south = convertView.findViewById<View>(R.id.south_value_text_view) as TextView
            convertView.findViewById<View>(R.id.south_text_view) as TextView

            viewHolder.north = convertView.findViewById<View>(R.id.north_value_text_view) as TextView
            convertView.findViewById<View>(R.id.north_text_view) as TextView

            viewHolder.west = convertView.findViewById<View>(R.id.west_value_text_view) as TextView
            convertView.findViewById<View>(R.id.west_text_view) as TextView

            convertView.tag = viewHolder

        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.name?.text = citySearched.name
        viewHolder.east?.text = citySearched.east
        viewHolder.south?.text = citySearched.south
        viewHolder.north?.text = citySearched.north
        viewHolder.west?.text = citySearched.west

        return convertView!!
    }

}

class ViewHolder {
    var name: TextView? = null
    var east : TextView?  = null
    var south: TextView?  = null
    var north: TextView? = null
    var west: TextView? = null
}
























