package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.FoodDatabase
import com.healthyorg.android.healthyapp.database.WeightDatabase
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodListFragment
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodListViewModel
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

class FoodActivity: AppCompatActivity() {

    private lateinit var foodTypeEditText: EditText
    private lateinit var foodCalsEditText: EditText
    private lateinit var entryButton: Button

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        foodCalsEditText = findViewById(R.id.food_cals_entry)
        foodTypeEditText = findViewById(R.id.food_type_entry)
        entryButton = findViewById(R.id.food_submit_button)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.food_fragment_container)

        entryButton.setOnClickListener{
            foodListViewModel.addMeal(Meal(food_type = foodTypeEditText.text.toString(), food_cals = foodCalsEditText.text.toString().toDouble()))
        }

        if(currentFragment == null){
            val fragment = FoodListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.food_fragment_container, fragment).commit()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            FoodDatabase::class.java, "food-database"
        ).build()
    }
}