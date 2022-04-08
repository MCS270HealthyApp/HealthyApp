package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.R

private const val TAG = "SleepListFragment"

class SleepListFragment: Fragment() {
    private lateinit var sleepRecyclerView: RecyclerView
    private var adapter: SleepAdapter? = SleepAdapter(emptyList())

    private val sleepListViewModel: SleepListViewModel by lazy {
        ViewModelProvider(this).get(SleepListViewModel::class.java)
    }

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

    private fun updateUI(sleeps: List<DailySleepMood>){
        adapter = SleepAdapter(sleeps)
        sleepRecyclerView.adapter = adapter
    }

    private inner class SleepHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var sleep: DailySleepMood

        private val sleepTextView: TextView = itemView.findViewById(R.id.sleep_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.sleep_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(sleep: DailySleepMood){
            this.sleep = sleep
            sleepTextView.text = "${this.sleep.hours.toString()} hours"
            dateTextView.text = this.sleep.date.toString()
        }

        override fun onClick(v: View){
            Toast.makeText(context, "Sleep from ${sleep.date} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class SleepAdapter(var sleeps: List<DailySleepMood>)
        :RecyclerView.Adapter<SleepHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepHolder {
            val view = layoutInflater.inflate(R.layout.list_item_daily_sleep, parent, false)
            return SleepHolder(view)
        }

        override fun getItemCount() = sleeps.size

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
}