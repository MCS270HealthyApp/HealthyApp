package com.healthyorg.android.healthyapp.goalClasses

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.R
import kotlinx.android.synthetic.main.goal_todo.view.*

private const val TAG = "GoalListFragment"

class GoalListFragment: Fragment() {
    private lateinit var goalRecyclerView: RecyclerView
    private var adapter: GoalAdapter? = GoalAdapter(emptyList())

    private val goalListViewModel: GoalListViewModel by lazy {
        ViewModelProvider(this).get(GoalListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_goal_list, container, false)

        goalRecyclerView =
            view.findViewById(R.id.goal_recycler_view) as RecyclerView
        goalRecyclerView.layoutManager = LinearLayoutManager(context)
        goalRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goalListViewModel.goalListLiveData.observe(
            viewLifecycleOwner,
            Observer { goals ->
                goals?.let{
                    Log.i(TAG, "Got sleeps ${goals.size}")
                    updateUI(goals)
                }
            }
        )
    }

    private fun updateUI(goals: List<Goal>){
        adapter = GoalAdapter(goals)
        goalRecyclerView.adapter = adapter
    }

    private inner class GoalHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var goal: Goal

        private val goalTextView: TextView = itemView.findViewById(R.id.goalTitle)
        private val goalCheckBox: CheckBox = itemView.findViewById(R.id.cbDone)
        private val goalDeleteButton: ImageButton = itemView.findViewById(R.id.goal_delete_button)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(goal: Goal){
            goalDeleteButton.setOnClickListener{
                deleteCurrentGoal(goal)
            }
            goalCheckBox.setOnClickListener {
                this.goal.isChecked = goalCheckBox.isChecked()
                toggleStrikeThrough(goalTextView, this.goal.isChecked)
            }
            this.goal = goal
            goalTextView.text = "${this.goal.title.toString()}"
            this.goal.isChecked = goalCheckBox.isChecked()
            //dateTextView.text = this.sleep.date.toString()
        }


        override fun onClick(v: View){
            //Toast.makeText(context, "Sleep from ${sleep.date} pressed!", Toast.LENGTH_SHORT).show()
        }

    }

    private inner class GoalAdapter(var goals: List<Goal>)
        :RecyclerView.Adapter<GoalHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
            val view = layoutInflater.inflate(R.layout.goal_todo, parent, false)
            return GoalHolder(view)
        }

        override fun getItemCount() = goals.size

        override fun onBindViewHolder(holder: GoalHolder, position: Int) {
            val goal = goals[goals.size - position - 1]
            holder.bind(goal)
        }
    }

    companion object{
        fun newInstance(): GoalListFragment {
            return GoalListFragment()
        }
    }

    fun deleteCurrentGoal(goal: Goal){
        goalListViewModel.deleteGoal(goal)
    }

    fun toggleStrikeThrough(theTextView: TextView, isChecked: Boolean){
        if (isChecked){
            theTextView.setPaintFlags(theTextView.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        }
        else{
            theTextView.setPaintFlags(theTextView.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }
    }


}