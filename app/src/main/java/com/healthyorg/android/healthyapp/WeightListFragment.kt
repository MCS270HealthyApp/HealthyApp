package com.healthyorg.android.healthyapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "WeightListFragment"

class WeightListFragment: Fragment() {
    private lateinit var weightRecyclerView: RecyclerView
    private var adapter: WeightAdapter? = null

    private val weightListViewModel: WeightListViewModel by lazy {
        ViewModelProviders.of(this).get(WeightListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_weight_list, container, false)

        weightRecyclerView =
            view.findViewById(R.id.weight_recycler_view) as RecyclerView
        weightRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI(){
        val weights = weightListViewModel.weights
        adapter = WeightAdapter(weights)
        weightRecyclerView.adapter = adapter
    }

    private inner class WeightHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var weight: Daily_Weight

        private val weightTextView: TextView = itemView.findViewById(R.id.weight_value)
        private val dateTextView: TextView = itemView.findViewById(R.id.weight_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(weight: Daily_Weight){
            this.weight = weight
            weightTextView.text = this.weight.input_weight.toString()
            dateTextView.text = this.weight.date.toString()
        }

        override fun onClick(v: View){
            Toast.makeText(context, "Weight from ${weight.date} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class WeightAdapter(var weights: List<Daily_Weight>)
        :RecyclerView.Adapter<WeightHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightHolder {
            val view = layoutInflater.inflate(R.layout.list_item_daily_weight, parent, false)
            return WeightHolder(view)
        }

        override fun getItemCount() = weights.size

        override fun onBindViewHolder(holder: WeightHolder, position: Int) {
            val weight = weights[position]
            holder.bind(weight)
        }
    }

    companion object{
        fun newInstance(): WeightListFragment {
            return WeightListFragment()
        }
    }
}