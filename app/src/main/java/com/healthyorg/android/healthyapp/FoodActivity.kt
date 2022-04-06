package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.healthyorg.android.healthyapp.database.FoodDatabase
import com.healthyorg.android.healthyapp.database.WeightDatabase
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodListFragment
import com.healthyorg.android.healthyapp.foodactivityclasses.FoodListViewModel
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal

private const val TAG = "FoodActivity"

class FoodActivity: AppCompatActivity() {
    private lateinit var genericFoodButton: Button


    private lateinit var foodTypeEditText: EditText
    private lateinit var foodCalsEditText: EditText
    private lateinit var entryButton: Button

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        genericFoodButton = findViewById(R.id.generics_list_button)

        //TODO("Replace tempList with a list of food data class objects and generate list based off titles")
        val tempList: Array<String> = arrayOf("chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli")
        var boolArray = BooleanArray(tempList.size)

        genericFoodButton.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.generic_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choice", null)
                .setMultiChoiceItems(tempList, BooleanArray(tempList.size)){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            val alertDialog = builder.create()
            alertDialog.show()
            val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setOnClickListener{
                Log.i(TAG, boolArray[0].toString())
                alertDialog.dismiss()
                //TODO("Implement addition of selected items to room database")
                boolArray = BooleanArray(tempList.size)
                alertDialog.dismiss()
            }
        }

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