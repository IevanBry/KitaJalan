package quiz_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kitajalan.R

class Quiz1ListAdapter (
    context: Context,
    private val menuList: List<Quiz1ListModel>
) : ArrayAdapter<Quiz1ListModel>(context, 0, menuList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_view_quiz, parent, false)

        val menuItem = getItem(position)

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textName)
        val textDesc: TextView = view.findViewById(R.id.textDesc)

        menuItem?.let {
            imageView.setImageResource(it.imageResId)
            textView.text = it.name
            textDesc.text = it.desc
        }
        return view
    }
}