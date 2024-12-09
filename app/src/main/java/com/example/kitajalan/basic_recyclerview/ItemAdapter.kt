package com.example.kitajalan.basic_recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kitajalan.activity.MainActivity
import com.example.kitajalan.R
import com.squareup.picasso.Picasso

class ItemAdapter (
    private val mList: List<ItemModel>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = mList[position]

        Picasso.get().load(item.imageurl).into(holder.imageView)
        holder.textDesc.text = item.description

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            Toast.makeText(context, "Anda klik item ke $position : ${item.description}", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        //            println("Anda klik item ke- $position: ${item.description}")
        }
        println("Loading Item yang ke: $position")
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val imageView : ImageView = ItemView.findViewById(R.id.imageView)
        val textDesc : TextView = ItemView.findViewById(R.id.textDesc)
    }
}