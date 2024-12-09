package Adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.activity.MainActivity
import com.example.kitajalan.fragment.DetailFragment
import com.example.kitajalan.R
import com.example.kitajalan.basic_api.data.model.TrendsDomain
import com.example.kitajalan.databinding.ItemTrendAdminBinding
import com.example.kitajalan.databinding.ViewholderTrendBinding
import com.squareup.picasso.Picasso

class TrendsAdapter(
    private var items: List<TrendsDomain>,
    private val context: Context,
    private val onDeleteClick: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
    private val userRole: String = sharedPrefs.getString("role", "user") ?: "user"

    private val VIEW_TYPE_ADMIN = 1
    private val VIEW_TYPE_USER = 2
    fun updateData(newItems: List<TrendsDomain>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (userRole == "admin") VIEW_TYPE_ADMIN else VIEW_TYPE_USER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ADMIN) {
            val binding = ItemTrendAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdminViewHolder(binding)
        } else {
            val binding = ViewholderTrendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trend = items[position]

        if (holder is AdminViewHolder) {
            holder.binding.Title.text = trend.title
            holder.binding.Subtitle.text = trend.subtitle

            loadImage(trend.picAddress, holder.binding.Image)

            holder.binding.btnEdit.setOnClickListener {

            }

            holder.binding.btnDelete.setOnClickListener {
                onDeleteClick(trend._uuid)
            }
        } else if (holder is UserViewHolder) {
            holder.binding.title.text = trend.title
            holder.binding.subtitle.text = trend.subtitle

            loadImage(trend.picAddress, holder.binding.pic)

            holder.itemView.setOnClickListener {
                openDetailFragment(trend)
            }
        }
    }

    private fun openDetailFragment(trend: TrendsDomain) {
        val bundle = Bundle().apply {
            putString("title", trend.title)
            putString("description", trend.description)
            putString("price", trend.price)
            val imageResource = context.resources.getIdentifier(trend.picAddress, "drawable", context.packageName)
            putInt("image", imageResource)
            putBoolean("isFavorite", trend.isFavorite)
        }

        val activity = context as? MainActivity
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, DetailFragment().apply { arguments = bundle })
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun loadImage(picAddress: String, imageView: ImageView) {
        if (picAddress.startsWith("http") || picAddress.startsWith("https")) {
            Picasso.get().load(picAddress).into(imageView)
        } else {
            val drawableResourceId = context.resources.getIdentifier(picAddress, "drawable", context.packageName)
            imageView.setImageResource(drawableResourceId)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class AdminViewHolder(val binding: ItemTrendAdminBinding) : RecyclerView.ViewHolder(binding.root)
    class UserViewHolder(val binding: ViewholderTrendBinding) : RecyclerView.ViewHolder(binding.root)
}
