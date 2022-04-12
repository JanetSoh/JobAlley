package com.example.joballey.NotificationPage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.NotificationAdapter
import com.example.joballey.R
import com.example.joballey.data.Company
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationPage : Fragment() {
    val database= Firebase.database
    val myRef=database.getReference("message")

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Company>
    private lateinit var tempArrayList: ArrayList<Company>
    lateinit var imageId:Array<Int>
    lateinit var heading:Array<String>
    lateinit var news:Array<String>

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NotificationAdapter.MyViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var views =inflater.inflate(R.layout.fragment_notification_page, container, false)
        newRecyclerView=views.findViewById(R.id.recyclerView)

        // Inflate the layout for this fragment
        return views


    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)

        imageId= arrayOf(
            R.drawable.company_logo_1,
            R.drawable.company_logo_2,
            R.drawable.company_logo_3,
            R.drawable.company_logo_4,
            R.drawable.company_logo_5,
            R.drawable.company_logo_6,
            R.drawable.company_logo_7,
            R.drawable.company_logo_8,
            R.drawable.company_logo_9,
            R.drawable.company_logo_10
        )

        heading= arrayOf(
            "Candidate Biden Called Saudi Arable a Pareft eaft.",
            "The definitive text on the healing powers of the mind/body connection.",
            "This is the definitive book on mindfulness from the beloved Zen master.",
            "A timeless classic in personal development.",
            "The definitive text on the healing powers of the mind/body connection.",
            "The definitive text on the healing powers of the mind/body connection.",
            "The definitive text on the healing powers of the mind/body connection.",
            "The definitive text on the healing powers of the mind/body connection.",
            "The definitive text on the healing powers of the mind/body connection.",
            "The definitive text on the healing powers of the mind/body connection."
        )

        news= arrayOf(
            getString(R.string.news_a),
            getString(R.string.news_b),
            getString(R.string.news_c),
            getString(R.string.news_d),
            getString(R.string.news_e),
            getString(R.string.news_f),
            getString(R.string.news_g),
            getString(R.string.news_h),
            getString(R.string.news_i),
            getString(R.string.news_j)
        )




        newArrayList= arrayListOf<Company>()
        tempArrayList= arrayListOf<Company>()
        getUserdata()



//        newRecyclerView.apply {
//            layoutManager=LinearLayoutManager(activity)
//            newRecyclerView.setHasFixedSize(true)
//
//        }
//
//
//        recyclerView.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity)
//            // set the custom adapter to the RecyclerView
//            adapter = NotificationAdapter(newArrayList)
//        }



    }


    private fun getUserdata() {
        for (i in imageId.indices){
            val company=Company(imageId[i],heading[i])
            newArrayList.add(company)
        }

//        tempArrayList.addAll(newArrayList)
//
//        val adapter = Notification_Fragment(tempArrayList)

        var adapter= NotificationAdapter(newArrayList)
        adapter.setOnItemClickListener(object : NotificationAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context,"You clicked in item no . $position", Toast.LENGTH_SHORT).show()

                val intent= Intent(context, NotificationDetail::class.java)
                intent.putExtra("heading",newArrayList[position].heading)
                intent.putExtra("imageId",newArrayList[position].titleImage)
                intent.putExtra("news",news[position])
                startActivity(intent)
            }
        })

        newRecyclerView.layoutManager= LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter
    }

}