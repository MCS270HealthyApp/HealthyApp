package com.healthyorg.android.healthyapp.goalClasses

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.healthyorg.android.healthyapp.MoodClasses.Daily_Mood
import com.healthyorg.android.healthyapp.MoodClasses.MoodListViewModel
import com.healthyorg.android.healthyapp.R
import kotlinx.android.synthetic.main.goal_todo.view.*

class GoalAdapter(
    var todos: MutableList<Goal>
) : RecyclerView.Adapter<GoalAdapter.TodoViewHolder>() {


    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.goal_todo,parent,false))

    }


    // an extra function to add item
    fun addToDo(todo1: Goal){
        todos.add(todo1)
        notifyItemInserted(todos.size-1)
    }

    fun deleteToDo(){
        // what you are removing is any item that has been checked,
        // which means your isChecked is set to true
        todos.removeAll{
                todo -> todo.isChecked
        }
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        // onBindViewholder now binds the ids from your xml file
        val curTodo = todos[position]
        holder.itemView.apply {
            goalTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(goalTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(goalTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }





}