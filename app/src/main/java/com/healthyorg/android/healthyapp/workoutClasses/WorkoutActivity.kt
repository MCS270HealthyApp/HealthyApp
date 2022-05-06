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
    private lateinit var workoutEntryButton: Button
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
        workoutEntryButton = findViewById(R.id.workout_submit_button)
        workoutEditText = findViewById(R.id.workout_name)
        typeEditText = findViewById(R.id.workout_type)
        calorieEditText = findViewById(R.id.calorie_used)
        listButton = findViewById(R.id.favorite_workout_list_button)
        favoriteEntryButton = findViewById(R.id.favorite_workout_entry_button)
        suggestedWorkoutButton = findViewById(R.id.suggested_workout_button)

        val favoriteWorkoutListLiveData: LiveData<List<Favorite_Workouts>> = workoutListViewModel.favoriteWorkoutList

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

        listButton.setOnClickListener{
            Log.i(com.healthyorg.android.healthyapp.workoutClasses.TAG, "favoriteWorkoutList size ${favoriteWorkoutList.size}")
            var favoriteWorkoutsNameList: Array<String> = emptyArray()
            for(item in favoriteWorkoutList) {
                favoriteWorkoutsNameList += item.workoutName + ", " + item.workoutType +", "+item.calorieBurned + " Calories"
            }
            var favBoolArray = BooleanArray(favoriteWorkoutsNameList.size)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                .setTitle(getString(R.string.favorite_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(favoriteWorkoutsNameList, BooleanArray(favoriteWorkoutsNameList.size)){ dialog, which, isChecked ->
                    favBoolArray[which] = isChecked
                }
            val alertDialog = builder.create()
            alertDialog.show()
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

        favoriteEntryButton.setOnClickListener{
            workoutListViewModel.addFavoriteWorkout(Favorite_Workouts(workoutName = workoutEditText.text.toString(), workoutType = typeEditText.text.toString(), calorieBurned = calorieEditText.text.toString().toDouble()))
        }

        workoutEntryButton.setOnClickListener{
            workoutListViewModel.addWorkout(Daily_Workout(workoutName = workoutEditText.text.toString(), workoutType = typeEditText.text.toString(), calorieBurned = calorieEditText.text.toString().toDouble()))
        }

        suggestedWorkoutButton.setOnClickListener{
            var suggestedWorkoutsList: Array<String> = emptyArray()
            for (item in workoutListViewModel.suggestedWorkoutList){
                suggestedWorkoutsList += item.workoutName
            }
            var boolArray = BooleanArray(suggestedWorkoutsList.size)

            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                .setTitle("Select Items")
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(suggestedWorkoutsList, BooleanArray(suggestedWorkoutsList.size)){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            val alertDialog = builder.create()
            alertDialog.show()
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
        val db = Room.databaseBuilder(
            applicationContext,
            WorkoutDatabase::class.java, "database-name"
        ).build()
    }
}