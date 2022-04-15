package com.example.joballey.SearchPage

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment_mad.SearchPageAdapter
import com.example.joballey.R
import com.example.joballey.SavePage.SaveJob
import com.example.joballey.databinding.FragmentSearchPageBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search_page.*
import java.util.*
import kotlin.collections.ArrayList

class SearchPage : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<SearchJob>
    private var apply:Button?=null
    private lateinit var tempArrayList: ArrayList<SearchJob>
    lateinit var imageId:Array<Int>
    lateinit var job_name:Array<String>
    lateinit var saveicon:Array<Int>
    lateinit var company_name:Array<String>
    lateinit var place:Array<String>
    lateinit var salary:Array<String>
    lateinit var news:Array<String>

    private var _binding:FragmentSearchPageBinding?=null
    private val binding get()=_binding!!
//    private lateinit var apply:Button

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SearchPageAdapter.MyViewHolder>? = null

    override fun onResume() {
        super.onResume()
        val places=resources.getStringArray(R.array.place)
        val arrayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item,places)

        val jobs=resources.getStringArray(R.array.job)
        val arrayAdapter2=ArrayAdapter(requireContext(),R.layout.dropdown_item,jobs)

        binding.spinnerPlace.setAdapter(arrayAdapter)
        binding.spinnerJob.setAdapter(arrayAdapter2)

        spinner_job.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    if(parent.getItemAtPosition(position)=="All"){
                        tempArrayList.addAll(newArrayList)
                        val adapter=SearchPageAdapter(requireContext(),tempArrayList)
                        newRecyclerView.adapter=adapter
                        newRecyclerView.layoutManager=LinearLayoutManager(requireContext())

                    }

                    else{
                        tempArrayList.clear()
                        val item=parent.getItemAtPosition(position).toString()
                        Toast.makeText(parent.context,"Selected:$item",Toast.LENGTH_SHORT).show()
                        if(item.isNotEmpty()){
                            newArrayList.forEach{
                                if(it.jobname?.lowercase(Locale.getDefault())!!.contains(item)){
                                    tempArrayList.add(it)
                                }
                                val adapter=SearchPageAdapter(requireContext(),tempArrayList)
                                newRecyclerView.adapter=adapter
                                newRecyclerView.layoutManager=LinearLayoutManager(requireContext())
                            }
                        }else{
                            tempArrayList.clear()
                            tempArrayList.addAll(newArrayList)
                            newRecyclerView.adapter!!.notifyDataSetChanged()
                        }

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        spinner_place.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    if(parent.getItemAtPosition(position)=="All"){
                        tempArrayList.addAll(newArrayList)
                        val adapter=SearchPageAdapter(requireContext(),tempArrayList)
                        newRecyclerView.adapter=adapter
                        newRecyclerView.layoutManager=LinearLayoutManager(requireContext())

                    }else{
                        tempArrayList.clear()
                        val item=parent.getItemAtPosition(position).toString()
                        Toast.makeText(parent.context,"Selected:$item",Toast.LENGTH_SHORT).show()
                        if(item.isNotEmpty()){
                            newArrayList.forEach{
                                if(it.place?.lowercase(Locale.getDefault())!!.contains(item)){
                                    tempArrayList.add(it)
                                }
                                val adapter=SearchPageAdapter(requireContext(),tempArrayList)
                                newRecyclerView.adapter=adapter
                                newRecyclerView.layoutManager=LinearLayoutManager(requireContext())
                            }
                        }else{
                            tempArrayList.clear()
                            tempArrayList.addAll(newArrayList)
                            newRecyclerView.adapter!!.notifyDataSetChanged()
                        }


                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSearchPageBinding.inflate(inflater,container,false)
        val views=binding.root
        apply= views.findViewById(R.id.search_salary)
        //val views =inflater.inflate(R.layout.fragment_search__page_, container, false)
        newRecyclerView=views.findViewById(R.id.recyclerView_Search)
        newArrayList= arrayListOf<SearchJob>()
        tempArrayList= arrayListOf<SearchJob>()

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        super.onViewCreated(itemView, savedInstanceState)



        apply?.setOnClickListener{
            Toast.makeText(context,"Applied Success",Toast.LENGTH_SHORT).show()
        }
        getUserdata()
        setHasOptionsMenu(true)

        newArrayList= arrayListOf<SearchJob>()
        tempArrayList= arrayListOf<SearchJob>()

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu,menu)
        val item=menu?.findItem(R.id.search_action)
        val searchView=item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return tempArrayList.addAll(newArrayList)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText= newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    newArrayList.forEach{
                        if (it.jobname?.lowercase(Locale.getDefault())!!.contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    val adapter=SearchPageAdapter(requireContext(),tempArrayList)
                    newRecyclerView.adapter=adapter
                    newRecyclerView.layoutManager=LinearLayoutManager(requireContext())
                }else{
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }


                return false
            }

        })


        return super.onCreateOptionsMenu(menu,inflater)
    }




    private fun getUserdata() {
        dbref = FirebaseDatabase.getInstance("https://job-alley-3f825-default-rtdb.firebaseio.com/")
            .getReference("Company")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val company = userSnapshot.getValue(SearchJob::class.java)
                        newArrayList.add(company!!)
                        tempArrayList.addAll(newArrayList)
                        //tempArrayList.add(company!!)

                    }

                    newRecyclerView.adapter = SearchPageAdapter(requireContext(),newArrayList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        tempArrayList.addAll(newArrayList)

        val adapter=SearchPageAdapter(requireContext(),tempArrayList)
        adapter.setOnItemClickListener(object :SearchPageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context,"You clicked in item no . $position",Toast.LENGTH_SHORT).show()

//                val intent= Intent(context,Search_Page_Detail::class.java)
//                intent.putExtra("name",newArrayList[position].job_name)
//                intent.putExtra("imageId",newArrayList[position].titleImage)
//                intent.putExtra("news",news[position])
//                startActivity(intent)
            }
        })

        newRecyclerView.layoutManager=LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)
        newRecyclerView.adapter=adapter

    }


}