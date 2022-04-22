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


class GoalsActivity : AppCompatActivity() {
    private lateinit var goalEntryButton: Button
    private lateinit var goalEditText: EditText

    private val goalListViewModel: GoalListViewModel by lazy {
        ViewModelProvider(this).get(GoalListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        goalEntryButton = findViewById(R.id.btnAddTodo)
        goalEditText = findViewById(R.id.etTodoTitle)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.goal_fragment_container)

        goalEntryButton.setOnClickListener{
            goalListViewModel.addGoal(Goal(title = goalEditText.text.toString()))
        }


        if(currentFragment == null){
            val fragment = GoalListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.goal_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            GoalsDatabase::class.java, "database-name"
        ).build()
    }
}
