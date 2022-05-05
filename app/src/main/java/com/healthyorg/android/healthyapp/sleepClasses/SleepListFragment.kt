package com.healthyorg.android.healthyapp.sleepClasses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.database.SleepDatabase
import java.nio.file.Files.delete

private const val TAG = "SleepListFragment"

class SleepListFragment: Fragment() {
    private lateinit var sleepRecyclerView: RecyclerView
    private var adapter: SleepAdapter? = SleepAdapter(emptyList())

    //Associate SleepListFragment with SleepListViewModel
    private val sleepListViewModel: SleepListViewModel by lazy {
        ViewModelProvider(this).get(SleepListViewModel::class.java)
    }

    //Finding the recycler view associated with the sleep page and hooking it up to the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_sleep_list, container, false)

        sleepRecyclerView =
            view.findViewById(R.id.sleep_recycler_view) as RecyclerView
        sleepRecyclerView.layoutManager = LinearLayoutManager(context)
        sleepRecyclerView.adapter = adapter

        return view
    }

    //Uses LiveData to get the sleep objects stored in the database, then
    //passes them to update UI so they can actually appear in the recyclerview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sleepListViewModel.sleepListLiveData.observe(
            viewLifecycleOwner,
            Observer { sleeps ->
                sleeps?.let{
                    Log.i(TAG, "Got sleeps ${sleeps.size}")
                    updateUI(sleeps)
                }
            }
        )
    }

    //Gets a list of sleep objects and adapts them so that they appear in their proper places
    //onscreen, based on what we have in our database.
    private fun updateUI(sleeps: List<DailySleepMood>){
        adapter = SleepAdapter(sleeps)
        sleepRecyclerView.adapter = adapter
    }

    //An inner class that takes in a particular view (which is essentially just one
    //line/entry in our recyclerview) and passes it over to the actual recyclerview
    private inner class SleepHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var sleep: DailySleepMood

        //The two textviews and delete button that make up each line in our recyclerview
        private val sleepTextView: TextView = itemView.findViewById(R.id.sleep_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.sleep_date)
        private val sleepDeleteButton: ImageButton = itemView.findViewById(R.id.sleep_delete_button)

        init {
            itemView.setOnClickListener(this)
        }

        //Binds sleep text and current date to the current sleep object. Updates the screen.
        //Also handles the onclick listener for the delete button
        fun bind(sleep: DailySleepMood){
            sleepDeleteButton.setOnClickListener{
                deleteCurrentSleep(sleep)
            }
            this.sleep = sleep
            sleepTextView.text = "${this.sleep.hours.toString()} hours"
            dateTextView.text = this.sleep.date.toString()
        }


        override fun onClick(v: View){
            //Toast.makeText(context, "Sleep from ${sleep.date} pressed!", Toast.LENGTH_SHORT).show()
        }

    }

    //An adapter, which creates view models and puts them in the correct position
    private inner class SleepAdapter(var sleeps: List<DailySleepMood>)
        :RecyclerView.Adapter<SleepHolder>(){

        //Creates a view to display, wrapping it in a viewholder, then returning the result
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepHolder {
            val view = layoutInflater.inflate(R.layout.list_item_daily_sleep, parent, false)
            return SleepHolder(view)
        }

        //Gets the number of sleep items we have
        override fun getItemCount() = sleeps.size

        //Populates the sleep holder with a sleep object from a certain position
        override fun onBindViewHolder(holder: SleepHolder, position: Int) {
            val sleep = sleeps[sleeps.size - position - 1]
            holder.bind(sleep)
        }
    }

    companion object{
        fun newInstance(): SleepListFragment {
            return SleepListFragment()
        }
    }

    //Function for deleting an entry in the recycler view. Gets called by the bind() function
    fun deleteCurrentSleep(sleep: DailySleepMood){
        sleepListViewModel.deleteSleep(sleep)
    }


}