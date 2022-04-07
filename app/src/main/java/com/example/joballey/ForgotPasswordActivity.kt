package com.example.joballey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var reset_password: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        editEmail = findViewById(R.id.fp_email)
        reset_password = findViewById(R.id.send)

        auth = FirebaseAuth.getInstance()

        reset_password.setOnClickListener {
            auth.sendPasswordResetEmail(editEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "No email sent.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}