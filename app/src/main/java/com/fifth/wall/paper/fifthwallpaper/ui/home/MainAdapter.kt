package com.fifth.wall.paper.fifthwallpaper.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.fifth.wall.paper.fifthwallpaper.R

class MainAdapter (private val context: Context, private var dataList: List<Drawable>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    // Define a click listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Declare a variable to hold the listener
    private var listener: OnItemClickListener? = null

    // Setter method for the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // Provide a reference to the views for each data item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem: AppCompatImageView = itemView.findViewById(R.id.img_item)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.imag_item, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.imgItem.setImageDrawable(data)

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            // Notify the listener when an item is clicked
            listener?.onItemClick(position)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataList.size
    }

    // Method to update the dataset
    fun updateData(newData: List<Drawable>) {
        dataList = newData
        notifyDataSetChanged()
    }
}