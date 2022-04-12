package com.example.assignment_mad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.R
import com.google.android.material.imageview.ShapeableImageView

class SearchPageAdapter(private val companysList:ArrayList<CompanySearch>):RecyclerView.Adapter<SearchPageAdapter.MyViewHolder>() {

    private var mListener:onItemClickListener?=null

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item_search,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=companysList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.job_name.text=currentItem.job_name
        holder.save_icon.setImageResource(currentItem.save_icon)
        holder.company_name.text=currentItem.company_name
        holder.place.text=currentItem.place
        holder.salary.text=currentItem.salary


    }

    override fun getItemCount(): Int {
        return companysList.size
    }

    //to insert the post detail
    class MyViewHolder(itemView: View,listener: onItemClickListener?):RecyclerView.ViewHolder(itemView){

        val titleImage:ShapeableImageView=itemView.findViewById(R.id.title_image2)
        val job_name:TextView=itemView.findViewById(R.id.job_name)
        val save_icon:ShapeableImageView=itemView.findViewById(R.id.save_icon)
        val company_name:TextView=itemView.findViewById(R.id.company_name)
        val place:TextView=itemView.findViewById(R.id.detail_place)
        val salary:TextView=itemView.findViewById(R.id.detail_salary)

        init {
            itemView.setOnClickListener{
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}