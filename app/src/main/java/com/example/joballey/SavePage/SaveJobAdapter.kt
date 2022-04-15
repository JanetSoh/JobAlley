package com.example.assignment_mad

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.R
import com.example.joballey.SavePage.SaveJob
import com.google.android.material.imageview.ShapeableImageView

class SaveJobAdapter(private var context: Context, private val savedCompany:ArrayList<SaveJob>): RecyclerView.Adapter<SaveJobAdapter.MyViewHolder>() {

    private var mListener:onItemClickListener?=null


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item_save,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=savedCompany[position]
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
        return savedCompany.size
    }

    //to insert the post detail
    class MyViewHolder(itemView: View,listener: onItemClickListener?):RecyclerView.ViewHolder(itemView){

        val titleImage:ShapeableImageView=itemView.findViewById(R.id.save_image)
        val job_name:TextView=itemView.findViewById(R.id.save_jobname)
        val company_name:TextView=itemView.findViewById(R.id.save_companyname)
        val place:TextView=itemView.findViewById(R.id.save_place)
        val salary:TextView=itemView.findViewById(R.id.save_salary)



        val button: Button =itemView.findViewById(R.id.save_applybutton)

        init {
            itemView.setOnClickListener{
                listener?.onItemClick(adapterPosition)
            }
        }
    }

}