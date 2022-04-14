package com.example.joballey.UserProfilePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.R
import com.example.joballey.UserProfilePage.UserSkills
import org.w3c.dom.Text

class UserAdapter(private val userList:ArrayList<UserSkills>):RecyclerView.Adapter<UserAdapter.ViewHolder>()
{

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var skill :TextView

        init {
            skill = view.findViewById(R.id.skills)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_skill,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.skill.text = userList[position].uSkills.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
