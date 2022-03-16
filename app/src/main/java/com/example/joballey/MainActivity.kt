package com.example.joballey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import android.content.Intent


class MainActivity : AppCompatActivity() {

//    var registration : TextView = findViewById(R.id.register)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var login : TextView = findViewById(R.id.sign_in)
        var registration : TextView = findViewById(R.id.register)

        login.setOnClickListener(){
            Toast.makeText(this, "Clicked",Toast.LENGTH_LONG).show()
        }

        registration.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}