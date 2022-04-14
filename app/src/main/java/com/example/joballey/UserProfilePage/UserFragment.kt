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
import com.example.joballey.databinding.ActivityMainBinding
import com.example.joballey.databinding.FragmentSkillsBinding
import com.example.joballey.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {
    //view binding
    private lateinit var binding: FragmentUserBinding
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

        //Authenticator
        auth = FirebaseAuth.getInstance()


        loadUserInfo()
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


    fun loadUserInfo(){
        val datab = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(auth.currentUser!!.uid)
        val eventListener = object :ValueEventListener{
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
}