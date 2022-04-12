package com.healthyorg.android.healthyapp.goalClasses

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.database.GoalsDatabase
import kotlinx.android.synthetic.main.activity_goals.*

private const val TAG = "GoalActivity"

class GoalsActivity: AppCompatActivity() {

    private lateinit var todoAdapter: GoalAdapter

    private val goalsRepository = GoalsRepository.get()
    private val goalListLiveData = goalsRepository.getAllGoals()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)


        todoAdapter = GoalAdapter(mutableListOf())

        val nameObserver = Observer<List<Goal>>{ goals ->
            goals?.let {
                Log.i(TAG, "Got goals ${goals.size}")
                updateUI(goals)
            }
        }
        goalListLiveData.observe(this, nameObserver)

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        //adds the goal to the list

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Goal(todoTitle)
                etTodoTitle.text.clear()
                addGoal(todo)
            }
        }
        //deletes checked goals after clicked again
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteToDo()
        }
        val db = Room.databaseBuilder(
            applicationContext,

            GoalsDatabase::class.java, "database-name"

        ).fallbackToDestructiveMigration()
    }
    private fun updateUI(goals: List<Goal>){
        todoAdapter.todos = goals as MutableList<Goal>
    }
    private fun addGoal(goal: Goal){
        goalsRepository.insertGoal(goal)
    }
}
