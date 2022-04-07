package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.GoalsDatabase
import com.healthyorg.android.healthyapp.database.WeightDatabase
import kotlinx.android.synthetic.main.activity_goals.*
import org.json.JSONObject.NULL

class GoalsActivity: AppCompatActivity() {

    private lateinit var todoAdapter: GoalAdapter

    private val goalsRepository = GoalsRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)


        todoAdapter = GoalAdapter(mutableListOf())


        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        //adds the goal to the list

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Goal(todoTitle)
                todoAdapter.addToDo(todo)
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
        ).build()
    }
    private fun addGoal(goal: Goal){
        goalsRepository.insertGoal(goal)
    }



}
