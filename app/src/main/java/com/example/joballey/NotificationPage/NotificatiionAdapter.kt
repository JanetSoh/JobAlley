package com.example.assignment_mad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.NotificationPage.Notification
import com.example.joballey.R
import com.google.android.material.imageview.ShapeableImageView



class NotificationAdapter(private var context: Context, private val notificationlist:ArrayList<Notification>):RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

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


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = notificationlist[position]
        //holder.titleImage.text=currentItem.titleImage
        currentItem?.titleImage?.let {
            Glide.with(context).load(currentItem.titleImage).into(holder.imageNotification)
        }
        holder.heading.text = currentItem.heading
    }

    override fun getItemCount(): Int {
        return notificationlist.size
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        val imageNotification: ShapeableImageView = itemView.findViewById(R.id.notification_image)

        //val titleImage: TextView =itemView.findViewById(R.id.title_image)
        val heading: TextView = itemView.findViewById(R.id.notification_heading)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

}