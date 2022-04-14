package com.example.joballey.UserProfilePage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.R
import com.example.joballey.databinding.FragmentEducationBinding
import com.example.joballey.UserProfilePage.UserEdu
//import com.example.joballey.model.UserSkills
import com.example.joballey.UserProfilePage.EduAdapter
import com.example.joballey.UserProfilePage.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class EducationFragment : Fragment() {
    private lateinit var binding: FragmentEducationBinding
    private lateinit var database: DatabaseReference
    private lateinit var eduRv: RecyclerView
    private lateinit var educateList: ArrayList<UserEdu>
    private lateinit var eduAdapter:EduAdapter
    private lateinit var auth : FirebaseAuth
    private lateinit var ed: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEducationBinding.inflate(layoutInflater, container, false)

        val okButton = binding.doneEdu
        ed = "education"
        database = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/").getReference("Users")

        val eduDia = EduDialogFragment()
        val addsBtn = binding.addEdu

        educateList = ArrayList<UserEdu>()
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()

        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        loadUserInfo()


        //recycle view
        eduRv = binding.eduList

        eduAdapter = EduAdapter(educateList)
        eduRv.adapter = eduAdapter
        eduRv.layoutManager = LinearLayoutManager(requireContext())

        addsBtn.setOnClickListener {
            eduDia.passData(fragmentManager, object : OnClickListener {
                override fun onClick(userEdu: UserEdu) {
                    educateList.add(userEdu)
                    eduAdapter = EduAdapter(educateList)
                    eduRv.adapter = eduAdapter
                    eduRv.layoutManager = LinearLayoutManager(requireContext())

                    if (currentUser != null) {
                        database.child(currentUser.uid).child(ed).setValue(educateList).addOnSuccessListener {
                            Toast.makeText(requireContext(), "Education Saved Successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Education Saved Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            })
        }

        okButton.setOnClickListener{
            okButton.setOnClickListener {
                findNavController().navigate(R.id.action_educationFragment_to_userFragment)

            }
            // findNavController().navigate(R.id.action_educationFragment_to_userFragment)
        }

        return binding.root
    }
    fun loadUserInfo(){
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(auth.currentUser!!.uid)
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value!=null) {
                    val name = snapshot.child("name").value as String
                    val email = snapshot.child("email").value as String
                    val telNo = snapshot.child("telNO").value as String
//                    val email = "${snapshot.child("email").value}"
//                    val telNo = "${snapshot.child("telNO").value}"
                    //set data
                    binding.uName.text = name
                    binding.uEmail.text = email
                    binding.uTelNo.text = telNo
                }else{
                    Toast.makeText(requireContext(), "Retrieved failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        dataRef.addListenerForSingleValueEvent(eventListener)
    }

    interface OnClickListener {
        fun onClick(userEdu: UserEdu)
    }

    fun loadSkill(){
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(auth.currentUser!!.uid)
    }

    fun loadEdu(){

    }


}