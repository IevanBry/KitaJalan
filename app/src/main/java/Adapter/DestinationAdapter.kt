package Adapter

import Domain.TrendsDomain
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.Activity.basic_api.data.model.Destination
import com.example.kitajalan.R
import com.squareup.picasso.Picasso

class DestinationAdapter(
    private var destinations: List<Destination>
) : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    fun updateData(newItems: List<Destination>){
        destinations = newItems
        notifyDataSetChanged()
    }
    class DestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_destination, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = destinations[position]
        holder.titleTextView.text = destination.title
        holder.descriptionTextView.text = destination.description
        Picasso.get()
            .load(destination.thumbnail)
            .into(holder.thumbnailImageView)
    }
    override fun getItemCount(): Int = destinations.size
}
