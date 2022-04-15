package com.example.joballey.SavePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.SaveJobAdapter
import com.example.joballey.R
import com.example.joballey.databinding.FragmentSavePageBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class SavePage : Fragment() {
    private lateinit var dbref : DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<SaveJob>
    private var apply: Button?=null
    private lateinit var tempArrayList: ArrayList<SaveJob>
    lateinit var imageId:Array<Int>
    lateinit var job_name:Array<String>
    lateinit var saveicon:Array<Int>
    lateinit var company_name:Array<String>
    lateinit var place:Array<String>
    lateinit var salary:Array<String>
    lateinit var news:Array<String>

    private lateinit var binding: FragmentSavePageBinding
//    private lateinit var apply:Button

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SaveJobAdapter.MyViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavePageBinding.inflate(inflater,container,false)
        val views=binding.root


        apply= views.findViewById(R.id.save_applybutton)
        newRecyclerView=views.findViewById(R.id.recyclerView_Save)
        newArrayList= arrayListOf<SaveJob>()
        tempArrayList= arrayListOf<SaveJob>()

        return binding.root

    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding=null
//    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)

        apply?.setOnClickListener{
            Toast.makeText(context,"Applied Success", Toast.LENGTH_SHORT).show()
        }
        getUserdata()
        setHasOptionsMenu(true)

        newArrayList= arrayListOf<SaveJob>()
        tempArrayList= arrayListOf<SaveJob>()


    }

    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
            .getReference("SavedCompany")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val company = userSnapshot.getValue(SaveJob::class.java)
                        newArrayList.add(company!!)
                        tempArrayList.addAll(newArrayList)
                        //tempArrayList.add(company!!)

                    }

                    newRecyclerView.adapter = SaveJobAdapter(requireContext(),newArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        tempArrayList.addAll(newArrayList)

        val adapter=SaveJobAdapter(requireContext(),tempArrayList)
        adapter.setOnItemClickListener(object :SaveJobAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context,"You clicked in item no . $position", Toast.LENGTH_SHORT).show()

//                val intent= Intent(context,Search_Page_Detail::class.java)
//                intent.putExtra("name",newArrayList[position].job_name)
//                intent.putExtra("imageId",newArrayList[position].titleImage)
//                intent.putExtra("news",news[position])
//                startActivity(intent)
            }
        })

        newRecyclerView.layoutManager= LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter

    }

}