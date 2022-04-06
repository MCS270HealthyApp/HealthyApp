package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_goals.*

class GoalsActivity: AppCompatActivity() {

    private lateinit var todoAdapter: GoalAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        todoAdapter = GoalAdapter(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        //adds the goal to the list
        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Goal(todoTitle)
                todoAdapter.addToDo(todo)
                etTodoTitle.text.clear()
            }
        }
        //deletes checked goals after clicked again
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteToDo()
        }

    }

}