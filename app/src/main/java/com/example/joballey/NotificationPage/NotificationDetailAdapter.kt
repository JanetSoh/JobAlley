package com.example.joballey.NotificationPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.R
import com.google.android.material.imageview.ShapeableImageView

class NotificationDetailAdapter (private var context: Context, private val detaillist:ArrayList<Detail>):RecyclerView.Adapter<NotificationDetailAdapter.MyViewHolder>() {

    private var mListener: onItemClickListener?=null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView, mListener)
    }



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.MyViewHolder {
//
//        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
//        return MyViewHolder(itemView)
//    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = detaillist[position]
        //holder.titleImage.text=currentItem.titleImage
        currentItem?.imageD?.let {
            Glide.with(context).load(currentItem.imageD).into(holder.titleImage)
        }
        holder.heading.text = currentItem.heading
        holder.content.text=currentItem.content
    }

    override fun getItemCount(): Int {
        return detaillist.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        val titleImage: ShapeableImageView = itemView.findViewById(R.id.image_heading)
        val content: TextView =itemView.findViewById(R.id.news)
        val heading: TextView = itemView.findViewById(R.id.heading)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }
}