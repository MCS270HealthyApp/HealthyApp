package com.healthyorg.android.healthyapp.foodactivityclasses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.R
import com.healthyorg.android.healthyapp.Daily_Weight

private const val TAG = "FoodListFragment"

class FoodListFragment: Fragment() {
    private lateinit var foodRecyclerView: RecyclerView
    private var adapter: FoodAdapter? = FoodAdapter(emptyList())

    private val foodListViewModel: FoodListViewModel by lazy {
        ViewModelProviders.of(this).get(FoodListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_list, container, false)

        foodRecyclerView =
            view.findViewById(R.id.food_recycler_view) as RecyclerView
        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun updateUI(meals: List<Meal>){
        adapter = FoodAdapter(meals)
        foodRecyclerView.adapter = adapter
    }

    private inner class FoodHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var food: Meal

        private val foodTypeTextView: TextView = itemView.findViewById(R.id.food_type_value)
        private val foodCalsTextView: TextView = itemView.findViewById(R.id.food_cals_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.food_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(food: Meal){
            this.food = food
            foodTypeTextView.text = this.food.food_type.toString()
            foodCalsTextView.text = "${this.food.food_cals.toString()} Calories"
            dateTextView.text = this.food.date.toString()
        }

        override fun onClick(v: View){
            Toast.makeText(context, "Food from ${food.date} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

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
}