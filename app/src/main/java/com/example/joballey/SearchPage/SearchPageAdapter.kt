package com.example.assignment_mad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.R
import com.example.joballey.SearchPage.SearchJob
import com.google.android.material.imageview.ShapeableImageView

class SearchPageAdapter(private var context: Context, private val companysList:ArrayList<SearchJob>):RecyclerView.Adapter<SearchPageAdapter.MyViewHolder>() {
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
        currentItem.image?.let { Glide.with(context).load(currentItem.image).into(holder.titleImage) }
        holder.job_name.text=currentItem.jobname
        holder.company_name.text=currentItem.name
        holder.place.text=currentItem.place
        holder.salary.text=currentItem.salary
        holder.button.setOnClickListener{ChangeColor()}
        holder.love.setOnClickListener { ChangeColor2()}


    }

    private fun ChangeColor() {
        Toast.makeText(context,"Applied", Toast.LENGTH_SHORT).show()
    }

    private fun ChangeColor2() {
        Toast.makeText(context,"Success add to favourite",Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return companysList.size
    }

    //to insert the post detail
    class MyViewHolder(itemView: View,listener: onItemClickListener?):RecyclerView.ViewHolder(itemView){

        val titleImage:ShapeableImageView=itemView.findViewById(R.id.search_image)
        val job_name:TextView=itemView.findViewById(R.id.search_jobname)
        val company_name:TextView=itemView.findViewById(R.id.search_companyname)
        val place:TextView=itemView.findViewById(R.id.search_place)
        val salary:TextView=itemView.findViewById(R.id.search_salary)
        val button: Button =itemView.findViewById(R.id.search_applubutton)
        val love: ImageButton =itemView.findViewById(R.id.searchjob_icon)



        init {
            itemView.setOnClickListener{
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}