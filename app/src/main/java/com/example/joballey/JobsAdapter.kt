package com.example.joballey

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class JobsAdapter(
    private val context: Context,
    private val items : ArrayList<DataClass>)
    : RecyclerView.Adapter<JobsAdapter.MyViewHolder>() {

    private var mListener:onItemClickListener?=null


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.jobs_item,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=items[position]
        currentItem.image?.let { Glide.with(context).load(currentItem.image).into(holder.titleImage) }
        holder.job_name.text=currentItem.jobname
        holder.company_name.text=currentItem.name
        holder.place.text=currentItem.place
        holder.salary.text=currentItem.salary
        holder.button.setOnClickListener{ChangeColor()}

    }

    private fun ChangeColor() {
        Toast.makeText(context,"Applied", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //to insert the post detail
    class MyViewHolder(itemView: View,listener: onItemClickListener?):RecyclerView.ViewHolder(itemView){

        val titleImage: ShapeableImageView =itemView.findViewById(R.id.home_image)
        val job_name:TextView=itemView.findViewById(R.id.home_jobname)
        val company_name:TextView=itemView.findViewById(R.id.home_companyname)
        val place:TextView=itemView.findViewById(R.id.home_place)
        val salary:TextView=itemView.findViewById(R.id.home_salary)



        val button: Button =itemView.findViewById(R.id.home_applybutton)

        init {
            itemView.setOnClickListener{
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}
