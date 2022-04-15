package com.example.joballey.NotificationPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.NotificationAdapter
import com.example.joballey.R
import com.example.joballey.databinding.FragmentNotificationDetailBinding
import com.google.firebase.database.*

class NotificationDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentNotificationDetailBinding?=null
    private val binding get()=_binding!!

    private lateinit var dbref: DatabaseReference

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Detail>
    private lateinit var tempArrayList: ArrayList<Detail>

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NotificationAdapter.MyViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentNotificationDetailBinding.inflate(inflater,container,false)
        val views =binding.root

        newRecyclerView=views.findViewById(R.id.recyclerView)
        newArrayList= arrayListOf<Detail>()
        tempArrayList= arrayListOf<Detail>()
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)
        getUserdata()
    }


    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance("https://recyclerview-e3e96-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Detail")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val notification = userSnapshot.getValue(Detail::class.java)
                        newArrayList.add(notification!!)
                        tempArrayList.addAll(newArrayList)
                        //tempArrayList.add(company!!)

                    }

                    newRecyclerView.adapter = NotificationDetailAdapter(requireContext(),newArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        newRecyclerView.layoutManager= LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter

    }
}