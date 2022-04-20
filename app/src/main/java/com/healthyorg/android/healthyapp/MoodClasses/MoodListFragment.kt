package com.healthyorg.android.healthyapp.MoodClasses

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

private const val TAG = "MoodListFragment"

class MoodListFragment: Fragment() {
    private lateinit var moodRecyclerView: RecyclerView
    private var adapter: MoodAdapter? = MoodAdapter(emptyList())

    private val moodListViewModel: MoodListViewModel by lazy {
        ViewModelProvider(this).get(MoodListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_mood_list, container, false)

        moodRecyclerView =
            view.findViewById(R.id.mood_recycler_view) as RecyclerView
        moodRecyclerView.layoutManager = LinearLayoutManager(context)
        moodRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moodListViewModel.moodListLiveData.observe(
            viewLifecycleOwner,
            Observer { moods ->
                moods?.let{
                    Log.i(TAG, "Got moods ${moods.size}")
                    updateUI(moods)
                }
            }
        )
    }

    private fun updateUI(moods: List<Daily_Mood>){
        adapter = MoodAdapter(moods)
        moodRecyclerView.adapter = adapter
    }

    private inner class MoodHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var mood: Daily_Mood

        private val moodTextView: TextView = itemView.findViewById(R.id.mood_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.mood_date)
        private val moodDeleteButton: ImageButton = itemView.findViewById(R.id.mood_delete_button)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(mood: Daily_Mood){
            moodDeleteButton.setOnClickListener {
                deleteCurrentMood(mood)
            }
            this.mood = mood
            moodTextView.text = "${this.mood.feelings.toString()}"
            dateTextView.text = this.mood.date.toString()
        }

        override fun onClick(v: View){
            //Toast.makeText(context, "Mood from ${mood.date} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class MoodAdapter(var moods: List<Daily_Mood>)
        :RecyclerView.Adapter<MoodHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodHolder {
            val view = layoutInflater.inflate(R.layout.list_item_daily_mood, parent, false)
            return MoodHolder(view)
        }

        override fun getItemCount() = moods.size

        override fun onBindViewHolder(holder: MoodHolder, position: Int) {
            val mood = moods[moods.size - position - 1]
            holder.bind(mood)
        }
    }

    companion object{
        fun newInstance(): MoodListFragment {
            return MoodListFragment()
        }
    }

    fun deleteCurrentMood(mood: Daily_Mood){
        moodListViewModel.deleteMood(mood)
    }
}