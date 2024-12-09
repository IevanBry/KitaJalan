package Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.basic_api.data.model.NewsHorizontalModel
import com.example.kitajalan.databinding.FragmentMainBinding
import com.example.kitajalan.databinding.FragmentNotificationBinding
import com.example.kitajalan.databinding.ViewholderTrendBinding
import com.squareup.picasso.Picasso

class NewsHorizontalAdapter(
    private var mList: List<NewsHorizontalModel>,
) : RecyclerView.Adapter<NewsHorizontalAdapter.ViewHolder>() {

    fun updateData(newItems: List<NewsHorizontalModel>) {
        mList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderTrendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        holder.binding.title.text = item.newsTitle
        Picasso.get().load(item.imageUrl).into(holder.binding.pic)
        holder.itemView.setOnClickListener {
            Log.i("RecyclerView", "Anda klik item ke $position : ${item.newsTitle}")
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val newsImage: ImageView = itemView.findViewById(R.id.newsHoriImage)
//        val newsTitle: TextView = itemView.findViewById(R.id.newsHoriTitle)
//    }

    class ViewHolder(val binding: ViewholderTrendBinding): RecyclerView.ViewHolder(binding.root)
}