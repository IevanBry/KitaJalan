package basic_list_view

import Domain.SettingsDomain
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kitajalan.R

class SettingsAdapter(
    context: Context,
    private val menuList: List<SettingsDomain>
) : ArrayAdapter<SettingsDomain>(context, 0, menuList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_settings, parent, false)

        val menuItem = getItem(position)

        val imageView: ImageView = view.findViewById(R.id.itemIcon)
        val textView: TextView = view.findViewById(R.id.itemText)

        menuItem?.let {
            imageView.setImageResource(it.imageResId)
            textView.text = it.title
        }
        return view
    }
}
