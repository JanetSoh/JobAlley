package com.example.joballey.UserProfilePage

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.UserProfilePage.SkillsFragment
import com.example.joballey.UserProfilePage.UserAdapter
import com.example.joballey.UserProfilePage.UserSkills
import com.example.joballey.databinding.AddSkillBinding
import java.util.*

class SkillsDialogFragment : DialogFragment() {
    private lateinit var binding: AddSkillBinding
    private lateinit var userList: ArrayList<UserSkills>
    private lateinit var userAdapter: UserAdapter
    private lateinit var listener: SkillsFragment.OnClickListener
    //private lateinit var recv: RecyclerView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddSkillBinding.inflate(LayoutInflater.from(context))

        val addSkill = binding.userSkill
        val addDialog = AlertDialog.Builder(requireContext())

        userList = ArrayList<UserSkills>()
        userAdapter = UserAdapter(userList)

        addDialog.setView(binding.root)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            val skill = addSkill.text.toString()
            //why i cant add skill into my array ??
            userList.add(UserSkills(skill))
            userAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Skill Added Successfully", Toast.LENGTH_SHORT).show()
            listener.onClick(UserSkills(skill))
            dialog.dismiss()
        }

        addDialog.setNegativeButton("Cancel") { dialog, _ ->

            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }


        return addDialog.create()
    }

    fun passData(fragmentManager: FragmentManager,listener: SkillsFragment.OnClickListener){
        this.show(fragmentManager,null)
        this.listener = listener
    }
}