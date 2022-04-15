package com.example.joballey.NotificationPage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.NotificationAdapter
import com.example.joballey.R
import com.example.joballey.databinding.FragmentNotificationPageBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotificationPage : Fragment() {
    private var _binding:FragmentNotificationPageBinding?=null
    private val binding get()=_binding!!

    private lateinit var dbref: DatabaseReference

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Notification>
    private lateinit var tempArrayList: ArrayList<Notification>

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NotificationAdapter.MyViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNotificationPageBinding.inflate(inflater,container,false)
        val views =binding.root

        newRecyclerView=views.findViewById(R.id.recyclerView)
        newArrayList= arrayListOf<Notification>()
        tempArrayList= arrayListOf<Notification>()

        var adapter= NotificationAdapter(requireContext(),newArrayList)
        adapter.setOnItemClickListener(object : NotificationAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                findNavController().navigate(R.id.action_notificationPage2_to_notificationDetail)
            }

        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)
        getUserdata()
    }


    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
            .getReference("Notification")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val notification = userSnapshot.getValue(Notification::class.java)
                        newArrayList.add(notification!!)
                        tempArrayList.addAll(newArrayList)
                        //tempArrayList.add(company!!)

                    }

                    newRecyclerView.adapter = NotificationAdapter(requireContext(),newArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        newRecyclerView.layoutManager=LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter

    }


}