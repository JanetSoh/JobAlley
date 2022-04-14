package com.example.joballey

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.joballey.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class RegisterActivity : AppCompatActivity()
{


    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference



    private lateinit var name: EditText
    private lateinit var female: RadioButton
    private lateinit var male: RadioButton
    private lateinit var dob: EditText
    private lateinit var address: EditText
    private lateinit var telNo: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    //private lateinit var confirmPassword: EditText

    private lateinit var username: String
    private lateinit var birthdate: String
    private lateinit var useraddress: String
    private lateinit var usertel: String
    private lateinit var useremail: String
    private lateinit var userpassword: String
    //private lateinit var usercpassword: String
    private lateinit var gender: String
    private lateinit  var eduLv: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val success: Button = findViewById(R.id.register_now)
        val back: ImageButton = findViewById(R.id.imageBack)
        val resume: Button = findViewById(R.id.upload_resume)
        val spinner : Spinner = findViewById(R.id.spinner_educationLv)




        val educationLevel = arrayOf("Primary School", "Secondary School", "Diploma", "Degree", "Master", "Doctor of Philosophy")
        val arrayAdapter  = ArrayAdapter(this, android.R.layout.simple_spinner_item, educationLevel)

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                eduLv = educationLevel[p2]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        name = findViewById(R.id.editTextTextPersonName)
        female = findViewById(R.id.gender_female)
        male = findViewById(R.id.gender_male)
        dob = findViewById(R.id.editTextDate)
        address = findViewById(R.id.editTextTextAddress)
        telNo = findViewById(R.id.editTextTel)
        email = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editRPassword)
        //confirmPassword = findViewById(R.id.editTextRCPassword2)

        auth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser


        success.setOnClickListener {

            username = name.text.toString()
            gender = UserGender()
            birthdate = dob.text.toString()
            useraddress = address.text.toString()
            usertel = telNo.text.toString()
            useremail = email.text.toString()
            userpassword = password.text.toString()


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

            auth.createUserWithEmailAndPassword(useremail, userpassword).addOnSuccessListener {

            if (currentUser != null) {
                database.child(currentUser.uid).setValue(userDetails).addOnSuccessListener {

                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }
            }


                Toast.makeText(this, "createUserWithEmail: success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "createUserWithEmail: failed", Toast.LENGTH_SHORT).show()
            }

        }



        back.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }

        resume.setOnClickListener {
            val intent3 = Intent(this, UploadPDFActivity::class.java)
            startActivity(intent3)

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
       // usercpassword = confirmPassword.text.toString()

        if(TextUtils.isEmpty(userpassword)) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show()
        }
        if(userpassword.length < 6) {
            Toast.makeText(this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show()
        }
    }

}










