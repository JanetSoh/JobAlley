package com.example.assignment_mad


import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SaveJobDetail :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_detail)

        val headingNews: TextView =findViewById(R.id.heading)
        val mainNews: TextView =findViewById(R.id.news)
        val imageNews: ImageView =findViewById(R.id.image_heading)

        val bundle: Bundle?=intent.extras
        val heading=bundle!!.getString("heading")
        val imageId=bundle.getInt("imageId")
        val news=bundle.getString("news")

        headingNews.text=heading
        mainNews.text=news
        imageNews.setImageResource(imageId)

    }
}