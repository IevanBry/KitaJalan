package Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import Domain.TrendsDomain
import com.example.kitajalan.Activity.fragment.MainFragment
import com.example.kitajalan.databinding.ListItemBinding
import com.example.kitajalan.databinding.MainMenuGridBinding
import com.example.kitajalan.databinding.ViewholderTrendBinding
import com.squareup.picasso.Picasso

class TrendsAdapter(private var items: ArrayList<TrendsDomain>, private val context: Context) : RecyclerView.Adapter<TrendsAdapter.ViewHolder>() {

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_trend, parent, false)
//        return ViewHolder(view)
//    }

    fun updateData(newItems: ArrayList<TrendsDomain>){
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =  ViewholderTrendBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Mengembalikan ViewHolder dengan binding yang telah diinflate
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trend = items[position]
        holder.binding.title.text = trend.title
        holder.binding.subtitle.text = trend.subtitle

        // Mencetak semua isi dari trend
//        Log.d("TrendsAdapter", "Trend Item: $trend")

        val drawableResourceId = context.resources.getIdentifier(trend.picAddress, "drawable", context.packageName)
        if (trend.picAddress.startsWith("http") || trend.picAddress.startsWith("https")) {
            Picasso.get()
                .load(trend.picAddress)
                .into(holder.binding.pic)
        } else {
            holder.binding.pic.setImageResource(drawableResourceId)
        }

        holder.itemView.setOnClickListener {
            val title = trend.title
            val description = trend.description
            val price = trend.price
            val imageResource = context.resources.getIdentifier(trend.picAddress, "drawable", context.packageName)
            val isFavorite = trend.isFavorite


            val bundle = Bundle().apply {
                putString("title", title)
                putString("description", description)
                putString("price", price)
                putInt("image", imageResource)
                putBoolean("isFavorite", isFavorite)
            }

            val activity = context as? MainActivity
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, DetailFragment().apply { arguments = bundle })
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val title: TextView = itemView.findViewById(R.id.title)
//        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
//        val pic: ImageView = itemView.findViewById(R.id.pic)
//    }
    class ViewHolder(val  binding: ViewholderTrendBinding) : RecyclerView.ViewHolder(binding.root)
}
