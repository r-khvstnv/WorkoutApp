package com.rssll971.fitnessassistantapp

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Next class show all users exercises
 */
class UserExercisesAdapter(val context: Context,
                           private val userExerciseList: ArrayList<ExerciseModelClass>) :
    RecyclerView.Adapter<UserExercisesAdapter.MyViewHolder>() {
    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvUserExerciseName: TextView = itemView.findViewById(R.id.tv_item_activity_selectable_name)
        val llUserExercise: LinearLayout = itemView.findViewById(R.id.ll_item_activity_selectable)
        val ivEditExercise: ImageView = itemView.findViewById(R.id.iv_item_activities_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_activities_selectable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ExerciseModelClass = userExerciseList[position]
        //show exercise name
        holder.tvUserExerciseName.text = item.getName()
        /**
         * Next statement responsible for correct text size and show edit icon,
         * when user want to change own exercise
         */
        if (context is ActivitiesCatalogActivity){
            holder.ivEditExercise.visibility = View.VISIBLE
            holder.tvUserExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
        }
        else if (context is MainActivity){
            holder.ivEditExercise.visibility = View.GONE
            holder.tvUserExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        }
        /**
         * Next listener change exercise background when user choose exercise for doing
         * or
         * go to another dialog while user want change exercise
         */
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