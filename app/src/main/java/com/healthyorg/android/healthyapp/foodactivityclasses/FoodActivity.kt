package com.healthyorg.android.healthyapp.foodactivityclasses

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.healthyorg.android.healthyapp.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

private const val TAG = "FoodActivity"

class FoodActivity: AppCompatActivity() {
    private lateinit var gustieFoodsButton: Button
    private lateinit var genericFoodButton: Button
    private lateinit var foodTypeEditText: EditText
    private lateinit var foodCalsEditText: EditText
    private lateinit var entryButton: Button
    private lateinit var favoriteEntryButton: Button
    private lateinit var favoriteListButton: Button
    private lateinit var foodGraphButton: Button
    private lateinit var foodGraphReturnButton: Button
    private lateinit var foodListLayout: LinearLayout
    private lateinit var foodGraphLayout: LinearLayout
    private lateinit var foodGraph: GraphView
    private lateinit var gustieFoodLinksElements: Elements
    private lateinit var gustieFoodsList: Array<Meal>

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        foodGraphLayout = findViewById(R.id.food_graph_layout)
        foodListLayout = findViewById(R.id.food_list_layout)
        foodGraphButton = findViewById(R.id.food_graph_button)
        foodGraphReturnButton = findViewById(R.id.food_graph_return_button)
        foodGraph = findViewById(R.id.food_graph)
        genericFoodButton = findViewById(R.id.generics_list_button)
        favoriteListButton = findViewById(R.id.favorite_foods_list_button)
        gustieFoodsButton = findViewById(R.id.gustie_foods_button)

        gustieFoodsList = emptyArray()

        val favoriteFoodListLiveData: LiveData<List<FavoriteMeal>> = foodListViewModel.favoriteFoodList
        val suggestedFoodItems: List<Meal> = foodListViewModel.genericFoodSelectionList
        var suggestedFoodsNameList: Array<String> = emptyArray()
        for (item in suggestedFoodItems){
            suggestedFoodsNameList += item.food_type
        }
        var boolArray = BooleanArray(suggestedFoodsNameList.size)

        var favoriteFoodList: List<FavoriteMeal> = emptyList()
        favoriteFoodListLiveData.observe(
            this
        ) { favoriteMeals ->
            favoriteMeals?.let {
                Log.i(TAG, "Got favorite meals ${favoriteMeals.size}")
                favoriteFoodList = favoriteMeals
            }
        }

        val gustieFoodItems = GustieFoodItems()
        GustieFoodLinks().execute()
        gustieFoodItems.execute()

        gustieFoodsButton.setOnClickListener {
            if(gustieFoodItems.status != AsyncTask.Status.FINISHED){
                val toast = Toast.makeText(this, "Still Collecting Foods, Try Again Soon!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
            } else{
                Log.i(TAG, "gustieFoodsList size ${gustieFoodsList.size}")
                var gustieFoodsNameList: Array<String> = emptyArray()
                for(item in gustieFoodsList) {
                    gustieFoodsNameList += item.food_type + ", " + item.food_cals + " Calories"
                }
                val gusBoolArray = BooleanArray(gustieFoodsNameList.size)
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    .setTitle(getString(R.string.gustie_food_dialog_title))
                    .setCancelable(true)
                    .setNegativeButton("Close", null)
                    .setPositiveButton("Submit Choices", null)
                    .setMultiChoiceItems(gustieFoodsNameList, BooleanArray(gustieFoodsNameList.size)){ dialog, which, isChecked ->
                        gusBoolArray[which] = isChecked
                    }
                val alertDialog = builder.create()
                alertDialog.show()
                val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                posButton.setOnClickListener{
                    Log.i(TAG, boolArray[0].toString())
                    var tempList: List<Meal> = emptyList()
                    for (item in gustieFoodsList){
                        if(gusBoolArray[gustieFoodsList.indexOf(item)]) {
                            tempList += Meal(item.food_type, item.food_cals)
                            Log.i(TAG, "Favorite meal item ${item.food_type} added")
                        }
                    }
                    foodListViewModel.addAllMeals(tempList)
                    alertDialog.dismiss()
                }
            }
        }

        foodGraphButton.setOnClickListener {
            foodListLayout.visibility = View.GONE
            foodGraphLayout.visibility = View.VISIBLE
            var eatenMeals: List<Meal>
            foodListViewModel.foodListLiveData.observe(
                this,
                Observer { meals ->
                    meals?.let{
                        Log.i(TAG, "Got meals ${meals.size}")
                        eatenMeals = meals
                        val dailyCals: List<Double> = foodListViewModel.calcDailyCals(eatenMeals)
                        val series: BarGraphSeries<DataPoint>
                        var dataPointArr = emptyArray<DataPoint>()
                        for (i in dailyCals.indices){
                            dataPointArr += DataPoint(i.toDouble(), dailyCals[i])
                        }
                        series = BarGraphSeries(dataPointArr)
                        series.spacing = 50
                        series.isDrawValuesOnTop
                        series.valuesOnTopColor = Color.RED
                        series.color = Color.YELLOW
                        foodGraph.gridLabelRenderer.verticalAxisTitle = "Calories"
                        series.title= "Calories of Last 7 Days"
                        foodGraph.addSeries(series)
                    }
                }
            )
        }

        foodGraphReturnButton.setOnClickListener {
            foodListLayout.visibility = View.VISIBLE
            foodGraphLayout.visibility = View.GONE
            foodGraph.removeAllSeries()
        }

        favoriteListButton.setOnClickListener{
           Log.i(TAG, "favoriteFoodList size ${favoriteFoodList.size}")
           var favoriteFoodsNameList: Array<String> = emptyArray()
           for(item in favoriteFoodList) {
               favoriteFoodsNameList += item.food_type + ", " + item.food_cals + " Calories"
           }
           val favBoolArray = BooleanArray(favoriteFoodsNameList.size)
           val builder: AlertDialog.Builder = AlertDialog.Builder(this)
               .setTitle(getString(R.string.favorite_food_dialog_title))
               .setCancelable(true)
               .setNegativeButton("Close", null)
               .setPositiveButton("Submit Choices", null)
               .setMultiChoiceItems(favoriteFoodsNameList, BooleanArray(favoriteFoodsNameList.size)){ dialog, which, isChecked ->
                   favBoolArray[which] = isChecked
               }
           val alertDialog = builder.create()
           alertDialog.show()
           val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
           posButton.setOnClickListener{
               Log.i(TAG, boolArray[0].toString())
               var tempList: List<Meal> = emptyList()
               for (item in favoriteFoodList){
                   if(favBoolArray[favoriteFoodList.indexOf(item)]) {
                       tempList += Meal(item.food_type, item.food_cals)
                       Log.i(TAG, "Favorite meal item ${item.food_type} added")
                   }
               }
               foodListViewModel.addAllMeals(tempList)
               alertDialog.dismiss()
           }
        }

        genericFoodButton.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.generic_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(suggestedFoodsNameList, BooleanArray(suggestedFoodsNameList.size)){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            val alertDialog = builder.create()
            alertDialog.show()
            val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            posButton.setOnClickListener{
                Log.i(TAG, boolArray[0].toString())
                var tempList: List<Meal> = emptyList()
                for (item in suggestedFoodItems){
                    if(boolArray[suggestedFoodItems.indexOf(item)]) {
                        tempList += item
                        Log.i(TAG, "Meal item ${item.food_type} added")
                    }
                }
                foodListViewModel.addAllMeals(tempList)

                boolArray = BooleanArray(suggestedFoodsNameList.size)
                alertDialog.dismiss()
            }
        }

        foodCalsEditText = findViewById(R.id.food_cals_entry)
        foodTypeEditText = findViewById(R.id.food_type_entry)
        entryButton = findViewById(R.id.food_submit_button)
        favoriteEntryButton = findViewById(R.id.favorite_food_entry_button)

        val currentFragment  = supportFragmentManager.findFragmentById(R.id.food_fragment_container)

        entryButton.setOnClickListener{
            foodListViewModel.addMeal(Meal(food_type = foodTypeEditText.text.toString(), food_cals = foodCalsEditText.text.toString().toDouble()))
        }

        favoriteEntryButton.setOnClickListener{
            foodListViewModel.addFavoriteMeal(FavoriteMeal(food_type = foodTypeEditText.text.toString(), food_cals = foodCalsEditText.text.toString().toDouble()))
        }

        if(currentFragment == null){
            val fragment = FoodListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.food_fragment_container, fragment).commit()
        }
    }

    inner class GustieFoodLinks: AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            Log.i(TAG, "Started collecting links")
            var doc: Document
            try {
                doc = Jsoup.connect("https://gustavus.edu/diningservices/menu/").get()
                gustieFoodLinksElements = doc.select("a[href^='/diningservices/menu/item/']")
                Log.i(TAG, "All links collected")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
    }

    inner class GustieFoodItems: AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            Log.i(TAG, "Started collecting food items")
            var doc: Document
            for (item in gustieFoodLinksElements) {
                try {
                    doc = Jsoup.connect("https://gustavus.edu${item.attr("href")}").get()
                    var calories = doc.select("#nutritionalFactsTop dd").text()
                    if(calories.isEmpty()){
                        calories = "0.0"
                    } else{
                        calories = calories.split(" ")[0]
                    }
                    gustieFoodsList += Meal(doc.select("div h2:first-of-type").text(), calories.toDouble())
                    Log.i(TAG, doc.select("div h2:first-of-type").text() + doc.select("#nutritionalFactsTop dd").text())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }
    }
}