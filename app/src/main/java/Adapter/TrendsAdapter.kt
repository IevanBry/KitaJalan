package Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.Activity.MainActivity
import com.example.kitajalan.Activity.WebViewActivity
import com.example.kitajalan.Activity.WebViewBali
import com.example.kitajalan.Activity.fragment.DetailFragment
import com.example.kitajalan.R
import domain.TrendsDomain

class TrendsAdapter(private val items: ArrayList<TrendsDomain>, private val context: Context) : RecyclerView.Adapter<TrendsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_trend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trend = items[position]
        holder.title.text = trend.title
        holder.subtitle.text = trend.subtitle

        val drawableResourceId = context.resources.getIdentifier(trend.picAddress, "drawable", context.packageName)
        holder.pic.setImageResource(drawableResourceId)

        holder.itemView.setOnClickListener {
            when (trend.title) {
                "Bali" -> {
                    val baliDescription = "Bali is a beautiful Indonesian island known for " +
                            "its stunning beaches, vibrant culture, and lush rice terraces. " +
                            "It's a popular destination for tourists seeking relaxation, adventure, " +
                            "and unique experiences."
                    val bundle = Bundle().apply {
                        putString("description", baliDescription)
                    }

                    val activity = context as? MainActivity
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.fragment_container, DetailFragment().apply { arguments = bundle })
                        ?.addToBackStack(null)
                        ?.commit()

                    //Menampilkan webview bali
//                    val intent = Intent(context, WebViewBali::class.java)
//                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
        val pic: ImageView = itemView.findViewById(R.id.pic)
    }
}
