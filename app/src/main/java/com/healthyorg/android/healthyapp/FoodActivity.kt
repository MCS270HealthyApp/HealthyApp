package com.healthyorg.android.healthyapp

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "FoodActivity"

class FoodActivity: AppCompatActivity() {
    private lateinit var genericFoodButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        genericFoodButton = findViewById(R.id.generics_list_button)

        //TODO("Replace tempList with a list of food data class objects and generate list based off titles")
        val tempList: Array<String> = arrayOf("chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli","chicken", "apples", "potatoes", "broccoli")
        val boolArray: Array<Boolean> = Array(tempList.size){false}

        genericFoodButton.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.generic_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choice", null)
                .setMultiChoiceItems(tempList, null){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            val alertDialog = builder.create()
            alertDialog.show()
            val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setOnClickListener{
                Log.i(TAG, boolArray[0].toString())
                alertDialog.dismiss()
                //TODO("Implement addition of selected items to room database")
                //TODO("Reset boolarray after each submission")
            }
        }
    }

}