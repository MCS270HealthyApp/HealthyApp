package com.healthyorg.android.healthyapp.workoutClasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.database.WorkoutDatabase
import com.healthyorg.android.healthyapp.foodactivityclasses.FavoriteMeal
import com.healthyorg.android.healthyapp.foodactivityclasses.Meal
import kotlinx.android.synthetic.main.list_item_daily_workout.*

private const val TAG = "WorkoutActivity"

class WorkoutActivity : AppCompatActivity() {
    /*Various buttons, layouts, views, and certain variables declared
    * for global access*/
    private lateinit var workoutEntryButton: ImageButton
    private lateinit var workoutEditText: EditText
    private lateinit var typeEditText: EditText
    private lateinit var calorieEditText: EditText
    private lateinit var listButton: Button
    private lateinit var favoriteEntryButton: ImageButton
    private lateinit var suggestedWorkoutButton: Button

    private val workoutListViewModel: WorkoutListViewModel by lazy {
        ViewModelProviders.of(this).get(WorkoutListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        //Layouts and buttons initialized
        workoutEntryButton = findViewById(R.id.workout_submit_button)
        workoutEditText = findViewById(R.id.workout_name)
        typeEditText = findViewById(R.id.workout_type)
        calorieEditText = findViewById(R.id.calorie_used)
        listButton = findViewById(R.id.favorite_workout_list_button)
        favoriteEntryButton = findViewById(R.id.favorite_workout_entry_button)
        suggestedWorkoutButton = findViewById(R.id.suggested_workout_button)
        //Data queried from the foodListViewModel
        val favoriteWorkoutListLiveData: LiveData<List<Favorite_Workouts>> = workoutListViewModel.favoriteWorkoutList

        //favoriteFoodList initialized and translated from LiveData format
        //using an observer
        var favoriteWorkoutList: List<Favorite_Workouts> = emptyList()
        favoriteWorkoutListLiveData.observe(
            this
        ) { favoriteWorkouts ->
            favoriteWorkouts?.let {
                Log.i(com.healthyorg.android.healthyapp.workoutClasses.TAG, "Got favorite Workouts ${favoriteWorkouts.size}")
                favoriteWorkoutList = favoriteWorkouts
            }
        }



        val currentFragment  = supportFragmentManager.findFragmentById(R.id.workout_fragment_container)

        //On button click presents a user with a multiChoice dialog of their favorite foods
        listButton.setOnClickListener{
            Log.i(com.healthyorg.android.healthyapp.workoutClasses.TAG, "favoriteWorkoutList size ${favoriteWorkoutList.size}")
            //Multichoice dialogs use an array of strings to give list items text
            var favoriteWorkoutsNameList: Array<String> = emptyArray()
            for(item in favoriteWorkoutList) {
                favoriteWorkoutsNameList += item.workoutName + ", " + item.workoutType +", "+item.calorieBurned + " Calories"
            }
            //Boolean array facilitates checking selected items
            var favBoolArray = BooleanArray(favoriteWorkoutsNameList.size)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                .setTitle(getString(R.string.favorite_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(favoriteWorkoutsNameList, BooleanArray(favoriteWorkoutsNameList.size)){ dialog, which, isChecked ->
                    favBoolArray[which] = isChecked
                }
            //Dialog built and presented
            val alertDialog = builder.create()
            alertDialog.show()
            //If a user clicks submit their responses are checked and the appropriate food items are added
            //to the eaten foods database
            val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setOnClickListener{
                var tempList: List<Daily_Workout> = emptyList()
                for (item in favoriteWorkoutList){
                    if(favBoolArray[favoriteWorkoutList.indexOf(item)]) {
                        tempList += Daily_Workout(item.workoutName, item.workoutType, item.calorieBurned)
                    }
                }
                workoutListViewModel.addAllWorkouts(tempList)
                alertDialog.dismiss()
            }
        }
    //Data in editText fields stored into the favorite foods database
        favoriteEntryButton.setOnClickListener{
            workoutListViewModel.addFavoriteWorkout(Favorite_Workouts(workoutName = workoutEditText.text.toString(), workoutType = typeEditText.text.toString(), calorieBurned = calorieEditText.text.toString().toDouble()))
        }
    //Data in editText fields entered into the database on button click
        workoutEntryButton.setOnClickListener{
            workoutListViewModel.addWorkout(Daily_Workout(workoutName = workoutEditText.text.toString(), workoutType = typeEditText.text.toString(), calorieBurned = calorieEditText.text.toString().toDouble()))
        }

        suggestedWorkoutButton.setOnClickListener{
            var suggestedWorkoutsList: Array<String> = emptyArray()
            for (item in workoutListViewModel.suggestedWorkoutList){
                suggestedWorkoutsList += item.workoutName
            }
            //Builder defines appropriate pieces of the dialog creating a multichoice dialog
            var boolArray = BooleanArray(suggestedWorkoutsList.size)

            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                .setTitle("Select Items")
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(suggestedWorkoutsList, BooleanArray(suggestedWorkoutsList.size)){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            //Dialog built and presented
            val alertDialog = builder.create()
            alertDialog.show()
            //Positive response button adds items
            val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setOnClickListener{
                var tempList: List<Daily_Workout> = emptyList()
                for ((i, item) in workoutListViewModel.suggestedWorkoutList.withIndex()){
                    if (boolArray[i]) {
                        tempList += item
                    }
                }
                workoutListViewModel.addAllWorkouts(tempList)

                boolArray = BooleanArray(suggestedWorkoutsList.size)
                alertDialog.dismiss()
            }
        }

        if(currentFragment == null){
            val fragment = WorkoutListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.workout_fragment_container, fragment).commit()
        }
    }
}