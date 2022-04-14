package com.example.joballey.UserProfilePage

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import com.example.joballey.R
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.joballey.databinding.FragmentEditProfileBinding
import com.example.joballey.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null
    private lateinit var uid: String


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
        loadPic()
        changeProfButton.setOnClickListener {
            pickImageGallery()
        }


        done.setOnClickListener {

            val name = binding.inputName.text.toString()
            val email = binding.inputMail.text.toString()
            val telNo = binding.inputPhone.text.toString()
            val des = binding.inputDes.text.toString()
            val uri = imageUri.toString()


            validateName()
            validateTel()
            validateEmail()

            database =
                FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
                    .getReference("Users")

            if (currentUser != null) {
                database.child(currentUser.uid).child("name").setValue(name)
                database.child(currentUser.uid).child("email").setValue(email)
                database.child(currentUser.uid).child("telNO").setValue(telNo)
                database.child(currentUser.uid).child("des").setValue(des)
                database.child(currentUser.uid).child("profilePic").setValue(uri)

            }
            Toast.makeText(requireContext(), "Profile Updated Successfully!!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)


        }
        cancel.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_userFragment)
        }

        return binding.root
    }


    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            //get uri of the image
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data
                binding.profileImage.setImageURI(imageUri)
                val TAG: String = "EditProfileFragment"
                Log.d(TAG, imageUri.toString())
            } else {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
            }

        }
    )

    private fun loadPic() {
        val datab =
            FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
        val dataRef = datab.getReference("Users").child(auth.currentUser!!.uid)
        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val profilePic = snapshot.child("profilePic").value as String

                    //set image
                    try {
                        Glide.with(requireContext())
                            .load(profilePic)
                            .placeholder(R.drawable.unknown)
                            .into(binding.profileImage)
                    } catch (e: Exception) {

                    }
                } else {
                    Toast.makeText(requireContext(), "Retrieved failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        dataRef.addListenerForSingleValueEvent(eventListener)

    }


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