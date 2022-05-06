package com.healthyorg.android.healthyapp.foodactivityclasses

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
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
    /*Various buttons, layouts, views, graphs, and certain variables declared
    * for global access*/
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
    private var gustieFoodItems: GustieFoodItems = GustieFoodItems()

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        //Layouts and buttons initialized
        foodGraphLayout = findViewById(R.id.food_graph_layout)
        foodListLayout = findViewById(R.id.food_list_layout)
        foodGraphButton = findViewById(R.id.food_graph_button)
        foodGraphReturnButton = findViewById(R.id.food_graph_return_button)
        foodGraph = findViewById(R.id.food_graph)
        genericFoodButton = findViewById(R.id.generics_list_button)
        favoriteListButton = findViewById(R.id.favorite_foods_list_button)
        gustieFoodsButton = findViewById(R.id.gustie_foods_button)

        gustieFoodsList = emptyArray()

        //Data queried from the foodListViewModel
        val favoriteFoodListLiveData: LiveData<List<FavoriteMeal>> = foodListViewModel.favoriteFoodList
        val suggestedFoodItems: List<Meal> = foodListViewModel.genericFoodSelectionList
        var suggestedFoodsNameList: Array<String> = emptyArray()
        for (item in suggestedFoodItems){
            suggestedFoodsNameList += item.food_type
        }

        //favoriteFoodList initialized and translated from LiveData format
        //using an observer
        var favoriteFoodList: List<FavoriteMeal> = emptyList()
        favoriteFoodListLiveData.observe(
            this
        ) { favoriteMeals ->
            favoriteMeals?.let {
                Log.i(TAG, "Got favorite meals ${favoriteMeals.size}")
                favoriteFoodList = favoriteMeals
            }
        }

        //Web parsing called on activity creation to reduce risk of data not being present
        //when user wants to access the menu
        GustieFoodLinks().execute()

        gustieFoodsButton.setOnClickListener {
            //If the food items havent been fully parsed, the user is not allowed to view dialog
            if(gustieFoodItems.status != AsyncTask.Status.FINISHED){
                val toast = Toast.makeText(this, "Still Collecting Foods, Try Again Soon!", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.TOP, 0, 0)
                toast.show()
            } else{ //If all items are pulled the user can view the dialog
                Log.i(TAG, "gustieFoodsList size ${gustieFoodsList.size}")
                //Array created using food names to fill multiChoice
                var gustieFoodsNameArr: Array<String> = emptyArray()
                for(item in gustieFoodsList) {
                    gustieFoodsNameArr += item.food_type + ", " + item.food_cals + " Calories"
                }
                //Boolean array used to facilitate checking whether user selected an item
                val gusBoolArray = BooleanArray(gustieFoodsNameArr.size)
                //Multichoice dialog created so user can select multiple food items
                val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                    .setTitle(getString(R.string.gustie_food_dialog_title))
                    .setCancelable(true)
                    .setNegativeButton("Close", null)
                    .setPositiveButton("Submit Choices", null)
                    .setMultiChoiceItems(gustieFoodsNameArr, BooleanArray(gustieFoodsNameArr.size)){ dialog, which, isChecked ->
                        gusBoolArray[which] = isChecked
                    }
                //Dialog built and presented
                val alertDialog = builder.create()
                alertDialog.show()
                /*Positive response button uses the boolArray to query parsed foods
                * and add them to the database of eaten foods
                * */
                val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                posButton.setOnClickListener{
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

        //This button adjusts the layout to present the calorie summary graph to the user
        foodGraphButton.setOnClickListener {
            foodListLayout.visibility = View.GONE
            foodGraphLayout.visibility = View.VISIBLE
            var eatenMeals: List<Meal>
            /*The foodList is in livedata form and must use an observer in an active view to
            * dynamically update the data*/
            foodListViewModel.foodListLiveData.observe(
                this,
                Observer { meals ->
                    meals?.let{
                        Log.i(TAG, "Got meals ${meals.size}")
                        eatenMeals = meals
                        //Using the list of foods, last seven days averaged and returned
                        val dailyCals: List<Double> = foodListViewModel.calcDailyCals(eatenMeals)
                        //Using jjoe64's graphView, a graph is generated from the data
                        val series: BarGraphSeries<DataPoint>
                        var dataPointArr = emptyArray<DataPoint>()
                        for (i in dailyCals.indices){
                            dataPointArr += DataPoint(i.toDouble(), dailyCals[i])
                        }
                        series = BarGraphSeries(dataPointArr)
                        //The below material is styling
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

        //Does the inverse of the graph button and changes layout item visibility
        foodGraphReturnButton.setOnClickListener {
            foodListLayout.visibility = View.VISIBLE
            foodGraphLayout.visibility = View.GONE
            //Removes the previous series from the view so multiple sets
            //are not graphed side-by-side
            foodGraph.removeAllSeries()
        }

        //On button click presents a user with a multiChoice dialog of their favorite foods
        favoriteListButton.setOnClickListener{
           Log.i(TAG, "favoriteFoodList size ${favoriteFoodList.size}")
            //Multichoice dialogs use an array of strings to give list items text
           var favoriteFoodsNameList: Array<String> = emptyArray()
           for(item in favoriteFoodList) {
               favoriteFoodsNameList += item.food_type + ", " + item.food_cals + " Calories"
           }
            //Boolean array facilitates checking selected items
           val favBoolArray = BooleanArray(favoriteFoodsNameList.size)
           val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
               .setTitle(getString(R.string.favorite_food_dialog_title))
               .setCancelable(true)
               .setNegativeButton("Close", null)
               .setPositiveButton("Submit Choices", null)
               .setMultiChoiceItems(favoriteFoodsNameList, BooleanArray(favoriteFoodsNameList.size)){ dialog, which, isChecked ->
                   favBoolArray[which] = isChecked
               }
            //Dialog built and presented
           val alertDialog = builder.create()
           alertDialog.show()
            //If a user clicks submit their responses are checked and the appropriate food items are added
            //to the eaten foods database
           val posButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
           posButton.setOnClickListener{
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

        //Pulls up the suggested foods list in the form of a dialog
        genericFoodButton.setOnClickListener{
            //Builder defines appropriate pieces of the dialog creating a multichoice dialog
            var boolArray = BooleanArray(suggestedFoodsNameList.size)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this, R.style.gacDialog)
                .setTitle(getString(R.string.generic_food_dialog_title))
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .setPositiveButton("Submit Choices", null)
                .setMultiChoiceItems(suggestedFoodsNameList, BooleanArray(suggestedFoodsNameList.size)){ dialog, which, isChecked ->
                    boolArray[which] = isChecked
                }
            //Dialog built and presented
            val alertDialog = builder.create()
            alertDialog.show()
            //Positive response button adds items
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
                alertDialog.dismiss()
            }
        }

        //Related editText fields and entry buttons assigned
        foodCalsEditText = findViewById(R.id.food_cals_entry)
        foodTypeEditText = findViewById(R.id.food_type_entry)
        entryButton = findViewById(R.id.food_submit_button)
        favoriteEntryButton = findViewById(R.id.favorite_food_entry_button)

        //Fragment container assigned
        val currentFragment  = supportFragmentManager.findFragmentById(R.id.food_fragment_container)

        //Data in editText fields entered into the database on button click
        entryButton.setOnClickListener{
            foodListViewModel.addMeal(Meal(food_type = foodTypeEditText.text.toString(), food_cals = foodCalsEditText.text.toString().toDouble()))
        }

        //Data in editText fields stored into the favorite foods database
        favoriteEntryButton.setOnClickListener{
            foodListViewModel.addFavoriteMeal(FavoriteMeal(food_type = foodTypeEditText.text.toString(), food_cals = foodCalsEditText.text.toString().toDouble()))
        }

        //If the fragment has not been initialized, then initialize it
        if(currentFragment == null){
            val fragment = FoodListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.food_fragment_container, fragment).commit()
        }
    }

    /*AsyncTask parses the Gustavus Adolphus daily Caf menu.
    * Being async allows the tasks to run off the UI thread and
    * therefore not crash the activity*/
    inner class GustieFoodLinks: AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            Log.i(TAG, "Started collecting links")
            //Jsoup document created and parsed
            var doc: Document
            try {
                doc = Jsoup.connect("https://gustavus.edu/diningservices/menu/").get()
                //Document elements filtered to anchor tags with hrefs linking to menu items
                gustieFoodLinksElements = doc.select("a[href^='/diningservices/menu/item/']")
                Log.i(TAG, "All links collected")
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        /*The below function only runs after the website has been fully parsed
        * allowing GustieFoodItems to begin with the full list of hrefs*/
        override fun onPostExecute(result: Void?) {
            gustieFoodItems.execute()
            super.onPostExecute(result)
        }
    }

    /*AsyncTask parses each menu item's nutritional facts.
   * Being async allows the tasks to run off the UI thread and
   * therefore not crash the activity*/
    inner class GustieFoodItems: AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg p0: Void?): Void? {
            Log.i(TAG, "Started collecting food items")
            var doc: Document
            //The list of hrefs is looped so that each item is individually parsed
            for (item in gustieFoodLinksElements) {
                try {
                    doc = Jsoup.connect("https://gustavus.edu${item.attr("href")}").get()
                    //Calories parsed from the document
                    var calories = doc.select("#nutritionalFactsTop dd").text()
                    /*There are multiple exception cases.
                    * The calorie field can be empty, incorrectly entered, or
                    * formatted poorly and in such cases the program will default to
                    * 0.0 so a meal object can be created without error*/
                    if(calories.isEmpty()){
                        calories = "0.0"
                    } else{
                        calories = calories.split(" ")[0]
                        if(!calories.isDigitsOnly()){
                            calories = "0.0"
                        }
                    }
                    //The list of daily menu items is appended with a meal object created
                    //using the calories and the always present name heading selected from the doc
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