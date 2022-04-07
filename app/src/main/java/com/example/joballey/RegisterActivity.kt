package com.example.joballey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*



class RegisterActivity : AppCompatActivity()
{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val success : TextView = findViewById(R.id.register_now)
//        val uploadFile : Button = findViewById(R.id.upload_pdf)


        success.setOnClickListener {

            username = name.text.toString()
            gender = UserGender()
            birthdate = dob.text.toString()
            useraddress = address.text.toString()
            usertel = telNo.text.toString()
            useremail = email.text.toString()
            userpassword = password.text.toString()
            usercpassword = confirmPassword.text.toString()


            validateName()
            validateDOB()
            validateAddress()
            validateTel()
            validateEmail()
            validatePassword()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val userDetails = UserData(
                username,
                gender,
                birthdate,
                useraddress,
                usertel,
                eduLv,
                useremail,
                userpassword
            )
            database.child(username).setValue(userDetails).addOnSuccessListener {
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(useremail, userpassword).addOnSuccessListener {
                Toast.makeText(this, "createUserWithEmail: success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "createUserWithEmail: failed", Toast.LENGTH_SHORT).show()
            }

        }





    }


    private fun validateName() {
        username = name.text.toString()
        if(TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun UserGender(): String{
        if (female.isChecked) {
            gender = "Female"
        }
        else if (male.isChecked){
            gender = "Male"
        }
        return gender
    }



    private fun validateDOB(){
        birthdate = dob.text.toString()
        if(TextUtils.isEmpty(birthdate)){
            Toast.makeText(this, "Enter your Birth Of Date", Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateAddress(){
        useraddress = address.text.toString()
        if(TextUtils.isEmpty(useraddress)) {
            Toast.makeText(this, "Enter your address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateTel() {
        usertel = telNo.text.toString()
        if(TextUtils.isEmpty(usertel)){
            Toast.makeText(this, "Enter your telephone number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateEmail() {
        useremail = email.text.toString()
        if(TextUtils.isEmpty(useremail)) {
            Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            Toast.makeText(this, "Please re-enter your email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePassword() {
        userpassword = password.text.toString()
        usercpassword = confirmPassword.text.toString()

        if(TextUtils.isEmpty(userpassword)) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
        }
        if(userpassword.length < 6) {
            Toast.makeText(this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show()
        }
        if(TextUtils.isEmpty(usercpassword)) {
            Toast.makeText(this, "Enter your confirm password", Toast.LENGTH_SHORT).show()
        }
        if(!userpassword.equals(usercpassword)) {
            Toast.makeText(this, "Please same password", Toast.LENGTH_SHORT).show()
            password.clearComposingText()
            confirmPassword.clearComposingText()

        }
    }

}








