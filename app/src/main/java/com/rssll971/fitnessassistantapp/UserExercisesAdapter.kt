package com.rssll971.fitnessassistantapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserExercisesAdapter(val context: Context,
                           private val userExerciseList: ArrayList<ExerciseModelClass>) :
    RecyclerView.Adapter<UserExercisesAdapter.MyViewHolder>() {
    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvUserExerciseName: TextView = itemView.findViewById(R.id.tv_item_activity_selectable_name)
        val llUserExercise: LinearLayout = itemView.findViewById(R.id.ll_item_activity_selectable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_activities_selectable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ExerciseModelClass = userExerciseList[position]

        holder.tvUserExerciseName.text = item.getName()



        holder.llUserExercise.setOnClickListener {
            if (context is ActivitiesCatalogActivity){
                context.showUserExerciseDialog(false, item)
            }
            else if (context is MainActivity){
                if (holder.llUserExercise.isSelected){
                    context.prepareExerciseList(position, false)
                    holder.llUserExercise.isSelected = false
                }
                else{
                    context.prepareExerciseList(position, true)
                    holder.llUserExercise.isSelected = true
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return userExerciseList.size
    }
}