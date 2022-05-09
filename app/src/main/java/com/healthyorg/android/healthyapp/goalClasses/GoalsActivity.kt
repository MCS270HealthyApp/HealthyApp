package com.healthyorg.android.healthyapp.goalClasses

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.database.GoalsDatabase
import kotlinx.android.synthetic.main.activity_goals.*
import kotlinx.android.synthetic.main.goal_todo.*


class GoalsActivity : AppCompatActivity() {
    //buttons and texts
    private lateinit var goalEntryButton: Button
    private lateinit var goalEditText: EditText

    private val goalListViewModel: GoalListViewModel by lazy {
        ViewModelProvider(this).get(GoalListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        //initialized buttons and texts
        goalEntryButton = findViewById(R.id.btnAddTodo)
        goalEditText = findViewById(R.id.etTodoTitle)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.goal_fragment_container)

        //Data in editText field entered into the database on button click
        goalEntryButton.setOnClickListener{
            goalListViewModel.addGoal(Goal(title = goalEditText.text.toString()))
        }
        //If the fragment has not been initialized, then initialize it
        if(currentFragment == null){
            val fragment = GoalListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.goal_fragment_container, fragment).commit()
        }
    }
}
