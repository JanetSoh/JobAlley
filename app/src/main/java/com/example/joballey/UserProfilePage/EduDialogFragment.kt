package com.example.joballey.UserProfilePage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.joballey.databinding.AddEducationBinding
import com.example.joballey.UserProfilePage.UserEdu
import com.example.joballey.UserProfilePage.EduAdapter
import java.util.ArrayList

class EduDialogFragment :DialogFragment() {
    private lateinit var binding: AddEducationBinding
    private lateinit var eduList: ArrayList<UserEdu>
    private lateinit var eduAdapter: EduAdapter
    private lateinit var listener:EducationFragment.OnClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddEducationBinding.inflate(LayoutInflater.from(context))

        val addSub = binding.eduSub
        val addSch = binding.userEdu
        val schStart = binding.eduStart
        val addDialog = AlertDialog.Builder(requireContext())

        eduList = ArrayList<UserEdu>()
        eduAdapter = EduAdapter(eduList)

        addDialog.setView(binding.root)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            val school = addSch.text.toString()
            val subAdd = addSub.text.toString()
            val schoolS = schStart.text.toString()
            //add edu to array
            eduList.add(UserEdu(subAdd,school,schoolS))
            eduAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Education Added Successfully", Toast.LENGTH_SHORT).show()
            listener.onClick(UserEdu(school,subAdd,schoolS))
            dialog.dismiss()
        }

        addDialog.setNegativeButton("Cancel") { dialog, _ ->

            Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }


        return addDialog.create()
    }

    fun passData(fragmentManager: FragmentManager, listener: EducationFragment.OnClickListener){
        this.show(fragmentManager,null)
        this.listener = listener
    }

}