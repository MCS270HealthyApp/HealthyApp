package com.healthyorg.android.healthyapp.workoutClasses

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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.R

private const val TAG = "WorkoutListFragment"

/**
 * Acts as a manager for the different food item fragments
 */
class WorkoutListFragment: Fragment() {
    //Declare the recycler view and initialize the adapter
    private lateinit var workoutRecyclerView: RecyclerView
    private var adapter: WorkoutAdapter? = WorkoutAdapter(emptyList())

    private val workoutListViewModel: WorkoutListViewModel by lazy {
        ViewModelProviders.of(this).get(WorkoutListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_workout_list, container, false)
    //Layout relevant objects initialized
        workoutRecyclerView =
            view.findViewById(R.id.workout_recycler_view) as RecyclerView
        workoutRecyclerView.layoutManager = LinearLayoutManager(context)
        workoutRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Observe the relevant date from the view model and update the ui as the data set changes
        workoutListViewModel.workoutListLiveData.observe(
            viewLifecycleOwner,
            Observer { workouts ->
                workouts?.let{
                    Log.i(TAG, "Got workouts ${workouts.size}")
                    updateUI(workouts)
                }
            }
        )
    }
    /**
     * Updates the UI so that the recycler view presents a current set of items
     */
    private fun updateUI(workouts: List<Daily_Workout>){
        adapter = WorkoutAdapter(workouts)
        workoutRecyclerView.adapter = adapter
    }

    private inner class WorkoutHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var workout: Daily_Workout
        //Relevant data and interactive fields initialized
        private val workoutNameTextView: TextView = itemView.findViewById(R.id.workout_name)
        private val workoutTypeTextView: TextView = itemView.findViewById(R.id.workout_type)
        private val workoutCalorieTextView: TextView = itemView.findViewById(R.id.workout_calorie)
        private val dateTextView: TextView = itemView.findViewById(R.id.workout_date)
        private val workoutDeleteButton: ImageButton = itemView.findViewById(R.id.workout_delete_button)

        /**
         * Bind a fragment and set its info based off Meal data
         */
        fun bind(workout: Daily_Workout){
            //Sets up the onClick for the delete button
            workoutDeleteButton.setOnClickListener {
                deleteCurrentWorkout(workout)
            }
            this.workout = workout
            workoutNameTextView.text = this.workout.workoutName
            workoutTypeTextView.text = this.workout.workoutType
            workoutCalorieTextView.text = "${this.workout.calorieBurned.toString()} calories"
            dateTextView.text = this.workout.date.toString()
        }

        override fun onClick(v: View){
        }
    }
    /**
     * An adapter for intra class use and controlling some data
     */
    private inner class WorkoutAdapter(var workouts: List<Daily_Workout>)
        :RecyclerView.Adapter<WorkoutHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
            val view = layoutInflater.inflate(R.layout.list_item_daily_workout, parent, false)
            return WorkoutHolder(view)
        }

        override fun getItemCount() = workouts.size

        override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
            val workout = workouts[workouts.size - position - 1]
            holder.bind(workout)
        }
    }

    companion object{
        fun newInstance(): WorkoutListFragment {
            return WorkoutListFragment()
        }
    }

    fun deleteCurrentWorkout(workout: Daily_Workout){
        workoutListViewModel.deleteWorkout(workout)
    }
}