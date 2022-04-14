package com.example.joballey.UserProfilePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.R
import com.example.joballey.databinding.ListEduBinding
import com.example.joballey.databinding.ListSkillBinding
import com.example.joballey.UserProfilePage.UserEdu
import org.w3c.dom.Text

class EduAdapter(private val eduList:ArrayList<UserEdu>): RecyclerView.Adapter<EduAdapter.ViewHolder>() {
    private lateinit var binding:ListEduBinding

    class ViewHolder(binding:ListEduBinding):RecyclerView.ViewHolder(binding.root) {
        var school : TextView
        var sub : TextView
        var eduStart : TextView

        init {
            school = binding.edu
            sub = binding.eduLevel
            eduStart = binding.eduDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListEduBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sub.text = eduList[position].subject.toString()
        holder.school.text = eduList[position].uSchool.toString()
        holder.eduStart.text = eduList[position].schStart.toString()
    }

    override fun getItemCount(): Int {
        return eduList.size
    }
}