package com.example.joballey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    //var education: Spinner = findViewById(R.id.spinner_educationLv)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        ArrayAdapter.createFromResource(
//            this,
//            R.array.educationLv,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            education.adapter = adapter
//        }
//
//        education.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//
//
//        }


    }
}