package com.example.joballey.UserProfilePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.R
import com.example.joballey.databinding.FragmentEducationBinding
import com.example.joballey.UserProfilePage.UserEdu
//import com.example.joballey.model.UserSkills
import com.example.joballey.UserProfilePage.EduAdapter
import com.example.joballey.UserProfilePage.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import java.lang.Exception
import java.util.ArrayList

class EducationFragment : Fragment() {
    private lateinit var binding: FragmentEducationBinding
    private lateinit var database: DatabaseReference

    //Recycle view Stuff
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserSkills>
    private lateinit var userAdapter: UserAdapter
    private lateinit var eduRv: RecyclerView
    private lateinit var educateList: ArrayList<UserEdu>
    private lateinit var eduAdapter: EduAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var ed: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEducationBinding.inflate(layoutInflater, container, false)

        val okButton = binding.doneEdu
        ed = "education"
        database =
            FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
                .getReference("Users")

        val eduDia = EduDialogFragment()
        val addsBtn = binding.addEdu
        val cancelBtn = binding.cancelEdu

        //Array List
        userList = ArrayList<UserSkills>()
        educateList = ArrayList<UserEdu>()

        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser


        loadUserInfo()

        //recycle view for Skills
        recv = binding.skillsList
        userAdapter = UserAdapter(userList)
        recv.adapter = userAdapter
        recv.layoutManager = LinearLayoutManager(requireContext())
        loadSkill()

        //recycle view for Education
        eduRv = binding.eduList
        eduAdapter = EduAdapter(educateList)
        eduRv.adapter = eduAdapter
        eduRv.layoutManager = LinearLayoutManager(requireContext())
        loadEdu()


        eduRv.layoutManager = LinearLayoutManager(requireContext())
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()

        addsBtn.setOnClickListener {
            eduDia.passData(fragmentManager, object : OnClickListener {
                override fun onClick(userEdu: UserEdu) {
                    educateList.add(userEdu)
                    eduAdapter = EduAdapter(educateList)
                    eduRv.adapter = eduAdapter
                    eduRv.layoutManager = LinearLayoutManager(requireContext())

                    if (currentUser != null) {
                        database.child(currentUser.uid).child(ed).setValue(educateList)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    requireContext(),
                                    "Education Saved Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Education Saved Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

            })
        }
        cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_educationFragment_to_userFragment)
        }


        okButton.setOnClickListener {
            findNavController().navigate(R.id.action_educationFragment_to_userFragment)

        }

        return binding.root
    }

    private fun loadUserInfo(){
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(auth.currentUser!!.uid)
        val eventListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value!=null) {
                    val name = snapshot.child("name").value as String
                    val email = snapshot.child("email").value as String
                    val telNo = snapshot.child("telNO").value as String
                    val des = snapshot.child("des").value as String
                    val profilePic = snapshot.child("profilePic").value as String

                    //set data
                    binding.uName.text = name
                    binding.uEmail.text = email
                    binding.uTelNo.text = telNo
                    binding.uDes.text = des

                    //set image
                    try{
                        Glide.with(requireContext())
                            .load(profilePic)
                            .placeholder(R.drawable.unknown)
                            .into(binding.profileImage)
                    }catch (e: Exception){

                    }
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

    private fun loadSkill() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val datab =
            FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(currentUser!!.uid).child("skill")
        val skillListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (skillSnapshot in snapshot.children) {
                        val usersSkill = skillSnapshot.getValue(UserSkills::class.java)

                        if (usersSkill != null) {
                            userList.add(usersSkill)
                        }
                        userAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Retrieved failed", Toast.LENGTH_SHORT).show()
            }

        }
        dataRef.addListenerForSingleValueEvent(skillListener)
    }

    private fun loadEdu() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val datab =
            FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(currentUser!!.uid).child("education")
        val eduListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val TAG: String = "EducationFragment"
                Log.d(TAG, snapshot.exists().toString())
                if (snapshot.exists()) {
                    for (eduSnapshot in snapshot.children) {
//                       val sch = snapshot.child("uschool").value as String
//                        val educate = eduSnapshot.child("uSchool").getValue()
                        Log.d(TAG, currentUser.uid)
                        val educate = eduSnapshot.getValue(UserEdu::class.java)
                        Log.d(TAG, eduSnapshot.toString())
                        Log.d(TAG, educate.toString())
                        if (educate != null) {
                            educateList.add(educate)
                        }
                        eduAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Retrieved failed", Toast.LENGTH_SHORT).show()
            }

        }
        dataRef.addListenerForSingleValueEvent(eduListener)
    }


}