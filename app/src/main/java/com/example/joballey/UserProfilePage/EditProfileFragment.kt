package com.example.joballey.UserProfilePage

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.example.joballey.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.joballey.databinding.FragmentEditProfileBinding
import com.example.joballey.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var uid:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

        //button declaration
        val done: Button = binding.doneProfile
        val cancel: Button = binding.cancelProfile
        val backButton = binding.back
        val changeProfButton = binding.chgProf

        //firebase auth
        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser


        done.setOnClickListener{

            val name = binding.inputName.text.toString()
            val email = binding.inputMail.text.toString()
            val telNo = binding.inputPhone.text.toString()
            val des = binding.inputDes.text.toString()

            validateName()
            validateTel()
            validateEmail()

            database = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/").getReference("Users")
            val user = UserData(name, email, telNo, des)

            if (currentUser != null) {
                database.child(currentUser.uid).setValue(user).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Successfully Saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_educationFragment_to_userFragment)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Upload Failed", Toast.LENGTH_SHORT).show()
                }
            }


        }
        cancel.setOnClickListener{
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }
//        changeProfButton.setOnClickListener{
//            showImageAttachMenu()
//        }
        return binding.root
    }

    private fun loadUserInfo() {
        //db reference to user info
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = "${snapshot.child("name").value}"
                val email = "${snapshot.child("email").value}"
                val telNo = "${snapshot.child("telNo").value}"
                val des = "${snapshot.child("des").value}"
                val profilePic = "${snapshot.child("profilePic").value}"

                //set data
                binding.inputName.setText(name)
                binding.inputMail.setText(email)
                binding.inputPhone.setText(telNo)
                binding.inputDes.setText(des)

                //set image


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
//    private fun showImageAttachMenu(){
//        val popupMenu = PopupMenu(requireContext(),binding.chgProf)
//        popupMenu.menu.add(Menu.NONE,0,0,"Camera")
//        popupMenu.menu.add(Menu.NONE,1,1,"Gallery")
//        popupMenu.show()
//
//        //handle popup menu item click
//        popupMenu.setOnMenuItemClickListener {item->
//            val id = item.itemId
//
//        }
//    }

//    private fun uploadProfilePic() {
//        val packagename = getActivity()?.getPackageName()
//        imageUri = Uri.parse("android.resource://$packagename/${R.drawable.unknown}")
//        storageReference =
//            FirebaseStorage.getInstance().getReference("Users/" + auth.currentUser?.uid)
//        storageReference.putFile(imageUri).addOnSuccessListener {
//            Toast.makeText(
//                requireContext(),
//                "Profile Picture updated successfully",
//                Toast.LENGTH_SHORT
//            ).show()
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(), "Failed to Update Profile Picture", Toast.LENGTH_SHORT)
//                .show()
//        }
//    }

    private fun validateName() {
        val name = binding.inputName.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), "Enter your name", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validateTel() {
        val telNo = binding.inputPhone.text.toString()
        if (TextUtils.isEmpty(telNo)) {
            Toast.makeText(requireContext(), "Enter your telephone number", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun validateEmail() {
        val email = binding.inputMail.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show()
        }
    }

}