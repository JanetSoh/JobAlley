package com.example.joballey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.SaveJobAdapter
import com.example.joballey.DataClass
import com.example.joballey.databinding.FragmentJobsListBinding
import com.google.firebase.database.*

class JobsListFragment : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<DataClass>
    private var apply: Button?=null
    private lateinit var tempArrayList: ArrayList<DataClass>
    lateinit var imageId:Array<Int>
    lateinit var job_name:Array<String>
    lateinit var saveicon:Array<Int>
    lateinit var company_name:Array<String>
    lateinit var place:Array<String>
    lateinit var salary:Array<String>
    lateinit var news:Array<String>

    private lateinit var binding: FragmentJobsListBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<JobsAdapter.MyViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentJobsListBinding.inflate(inflater,container,false)
        val views=binding.root


        apply= views.findViewById(R.id.save_applybutton)
        newRecyclerView=views.findViewById(R.id.recyclerView_home)
        newArrayList= arrayListOf<DataClass>()
        tempArrayList= arrayListOf<DataClass>()

        return binding.root

    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)

        apply?.setOnClickListener{
            Toast.makeText(context,"Applied Success", Toast.LENGTH_SHORT).show()
        }
        getUserdata()
        setHasOptionsMenu(true)

        newArrayList= arrayListOf<DataClass>()
        tempArrayList= arrayListOf<DataClass>()


    }

    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
            .getReference("Company")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val company = userSnapshot.getValue(DataClass::class.java)
                        newArrayList.add(company!!)
                        tempArrayList.addAll(newArrayList)
                        //tempArrayList.add(company!!)

                    }

                    newRecyclerView.adapter = JobsAdapter(requireContext(),newArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        tempArrayList.addAll(newArrayList)

        val adapter= JobsAdapter(requireContext(),tempArrayList)
        adapter.setOnItemClickListener(object : JobsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context,"You clicked in item no . $position", Toast.LENGTH_SHORT).show()
            }
        })

        newRecyclerView.layoutManager= LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter

    }
}