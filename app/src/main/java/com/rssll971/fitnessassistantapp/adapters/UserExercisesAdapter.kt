package com.rssll971.fitnessassistantapp.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.ExerciseAdapterCallback
import com.rssll971.fitnessassistantapp.models.ExerciseModel
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.databinding.ItemActivitiesSelectableBinding
import com.rssll971.fitnessassistantapp.fragments.ExerciseCatalogFragment
import com.rssll971.fitnessassistantapp.fragments.StartWorkoutFragment

/**
 * Next class show all users exercises
 */
class UserExercisesAdapter(private val context: Context,
                           private val userExerciseList: ArrayList<ExerciseModel>,
                           private val fragment: Fragment,
                           private val callback: ExerciseAdapterCallback) :
    RecyclerView.Adapter<UserExercisesAdapter.MyViewHolder>() {
    private val idList = ArrayList<Int>()

    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemActivitiesSelectableBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_activities_selectable, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: ExerciseModel = userExerciseList[position]
        with(holder){
            //show exercise name
            binding.tvItemExerciseName.text = item.name
            /**
             * Next statement responsible for correct text size and show edit icon,
             * when user want to change own exercise
             */
            if (fragment is ExerciseCatalogFragment){
                binding.ivItemEdit.visibility = View.VISIBLE
                binding.tvItemExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
            }
            else if (fragment is StartWorkoutFragment){
                binding.ivItemEdit.visibility = View.GONE
                binding.tvItemExerciseName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                /**Show visually is exercise was selected for workout or not */
                itemView.isSelected = idList.contains(item.id)

            }
            /**
             * Next listener change exercise background when user choose exercise for doing
             * or
             * go to another dialog while user want change exercise
             */
            itemView.setOnClickListener {
                if (fragment is ExerciseCatalogFragment){
                    callback.onItemEditing(item, true)
                }
                else if (fragment is StartWorkoutFragment){
                    if (itemView.isSelected){
                        itemView.isSelected = false
                        callback.onItemSelected(position, false)
                        idList.remove(item.id)
                    } else{ //add item in list for preparation
                        itemView.isSelected = true
                        callback.onItemSelected(position, true)
                        idList.add(item.id)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return userExerciseList.size
    }
}