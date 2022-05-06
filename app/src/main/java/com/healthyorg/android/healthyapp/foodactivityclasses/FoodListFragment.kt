package com.healthyorg.android.healthyapp.foodactivityclasses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.R

private const val TAG = "FoodListFragment"

/**
 * Acts as a manager for the different food item fragments
 */
class FoodListFragment: Fragment() {
    //Declare the recycler view and initialize the adapter
    private lateinit var foodRecyclerView: RecyclerView
    private var adapter: FoodAdapter? = FoodAdapter(emptyList())

    //Data is drawn from the view model so an instance of it must be initialized
    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_list, container, false)

        //Layout relevant objects initialized
        foodRecyclerView =
            view.findViewById(R.id.food_recycler_view) as RecyclerView
        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Observe the relevant date from the view model and update the ui as the data set changes
        foodListViewModel.foodListLiveData.observe(
            viewLifecycleOwner,
            Observer { meals ->
                meals?.let{
                    Log.i(TAG, "Got meals ${meals.size}")
                    updateUI(meals)
                }
            }
        )
    }

    /**
     * Updates the UI so that the recycler view presents a current set of items
     */
    private fun updateUI(meals: List<Meal>){
        adapter = FoodAdapter(meals)
        foodRecyclerView.adapter = adapter
    }


    private inner class FoodHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var food: Meal

        //Relevant data and interactive fields initialized
        private val foodTypeTextView: TextView = itemView.findViewById(R.id.food_type_value)
        private val foodCalsTextView: TextView = itemView.findViewById(R.id.food_cals_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.food_date)
        private val foodDeleteButton: ImageButton = itemView.findViewById(R.id.food_delete_button)

        //Honestly no clue what this is doing here
        init {
            itemView.setOnClickListener(this)
        }

        /**
         * Bind a fragment and set its info based off Meal data
         */
        fun bind(food: Meal){
            //Sets up the onClick for the delete button
            foodDeleteButton.setOnClickListener {
                deleteCurrentFood(food)
            }
            this.food = food
            foodTypeTextView.text = this.food.food_type
            foodCalsTextView.text = "${this.food.food_cals.toString()} Calories"
            dateTextView.text = this.food.date.toString()
        }

        //Present a little toast to the user if they click an item in the view
        override fun onClick(v: View){
            Toast.makeText(context, "Food from ${food.date} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * An adapter for intra class use and controlling some data
     */
    private inner class FoodAdapter(var meals: List<Meal>)
        :RecyclerView.Adapter<FoodHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
            val view = layoutInflater.inflate(R.layout.list_item_meal, parent, false)
            return FoodHolder(view)
        }

        override fun getItemCount() = meals.size

        override fun onBindViewHolder(holder: FoodHolder, position: Int) {
            val weight = meals[meals.size - position - 1]
            holder.bind(weight)
        }
    }

    companion object{
        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }

    fun deleteCurrentFood(food: Meal){
        foodListViewModel.deleteFood(food)
    }
}