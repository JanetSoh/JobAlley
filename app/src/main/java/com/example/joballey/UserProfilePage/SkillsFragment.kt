package com.example.joballey.UserProfilePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joballey.R
import com.example.joballey.UserProfilePage.SkillsDialogFragment
import com.example.joballey.databinding.FragmentSkillsBinding
import com.example.joballey.UserProfilePage.UserSkills
import com.example.joballey.UserProfilePage.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class SkillsFragment : Fragment() {
    private lateinit var binding: FragmentSkillsBinding
    private lateinit var database:DatabaseReference
    private lateinit var recv: RecyclerView
    private lateinit var userList: ArrayList<UserSkills>
    private lateinit var userAdapter: UserAdapter
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

        sk="skill"
        userList = ArrayList<UserSkills>()
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.commit()

        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        loadUserInfo()


        //recycle view
        recv = binding.skillsList

        userAdapter = UserAdapter(userList)
        recv.adapter = userAdapter
        recv.layoutManager = LinearLayoutManager(requireContext())

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

        okButton.setOnClickListener {
            findNavController().navigate(R.id.action_skillsFragment_to_userFragment)
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
        fun onClick(userSkills: UserSkills)
    }


}