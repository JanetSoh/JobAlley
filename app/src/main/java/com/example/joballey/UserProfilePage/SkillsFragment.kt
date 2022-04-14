package com.example.joballey.UserProfilePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.R
import com.example.joballey.UserProfilePage.SkillsDialogFragment
import com.example.joballey.databinding.FragmentSkillsBinding
import com.example.joballey.UserProfilePage.UserSkills
import com.example.joballey.UserProfilePage.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*

class SkillsFragment : Fragment() {
    private lateinit var binding: FragmentSkillsBinding
    private lateinit var database:DatabaseReference
    //Recycle view Stuff
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserSkills>
    private lateinit var userAdapter: UserAdapter
    private lateinit var eduRv: RecyclerView
    private lateinit var educateList: ArrayList<UserEdu>
    private lateinit var eduAdapter:EduAdapter

    private lateinit var auth : FirebaseAuth
    private lateinit var sk: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSkillsBinding.inflate(layoutInflater, container, false)
        database = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/").getReference("Users")
        val okButton = binding.addSkill
        val skillDia = SkillsDialogFragment()
        val addsBtn = binding.newSkills
        val cancelBtn = binding.cancelSkill

        sk="skill"
        //ArrayList
        userList = ArrayList<UserSkills>()
        educateList = ArrayList<UserEdu>()

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()

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

        addsBtn.setOnClickListener {
            skillDia.passData(fragmentManager, object : OnClickListener {
                override fun onClick(userSkills: UserSkills) {
                    userList.add(userSkills)
                    userAdapter = UserAdapter(userList)
                    recv.adapter = userAdapter
                    recv.layoutManager = LinearLayoutManager(requireContext())


                    if (currentUser != null) {
                        database.child(currentUser.uid).child(sk).setValue(userList).addOnSuccessListener {
                            Toast.makeText(requireContext(), "Skill Saved Successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Skill Saved Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            })
        }

        cancelBtn.setOnClickListener{
            findNavController().navigate(R.id.action_skillsFragment_to_userFragment)
        }

        okButton.setOnClickListener {
            findNavController().navigate(R.id.action_skillsFragment_to_userFragment)
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
        fun onClick(userSkills: UserSkills)
    }
    private fun loadSkill(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(currentUser!!.uid).child("skill")
        val skillListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for(skillSnapshot in snapshot.children){
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
    private fun loadEdu(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(currentUser!!.uid).child("education")
        val eduListener = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val TAG: String = "EducationFragment"
                Log.d(TAG, snapshot.exists().toString())
                if(snapshot.exists()){
                    for(eduSnapshot in snapshot.children){
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