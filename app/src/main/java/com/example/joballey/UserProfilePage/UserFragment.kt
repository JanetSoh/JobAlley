package com.example.joballey.UserProfilePage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.joballey.R
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.joballey.databinding.ActivityMainBinding
import com.example.joballey.databinding.FragmentSkillsBinding
import com.example.joballey.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.ArrayList

class UserFragment : Fragment() {
    //view binding
    private lateinit var binding: FragmentUserBinding
    //Recycle view Stuff
    private lateinit var eduRv: RecyclerView
    private lateinit var educateList: ArrayList<UserEdu>
    private lateinit var eduAdapter:EduAdapter
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserSkills>
    private lateinit var userAdapter: UserAdapter

    private lateinit var database: DatabaseReference
    private lateinit var nameInput: TextView
    private lateinit var phoneInput: TextView
    private lateinit var emailInput: TextView
    private lateinit var desInput: TextView
    private lateinit var navController: NavController
    private lateinit var uid: String

    //firebase auth
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        val edit1 = binding.editDetail
        val edit2 = binding.editSkill
        val edit3 = binding.editEdu

        //ArrayList
        userList = ArrayList<UserSkills>()
        educateList = ArrayList<UserEdu>()

        //Authenticator
        auth = FirebaseAuth.getInstance()
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


        edit1.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_editProfileFragment)
        }
        edit2.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_skillsFragment)
        }
        edit3.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_educationFragment)
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
                    }catch (e:Exception){

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