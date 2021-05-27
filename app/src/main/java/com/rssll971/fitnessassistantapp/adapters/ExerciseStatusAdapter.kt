package com.rssll971.fitnessassistantapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.models.ExerciseModel
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.databinding.ItemExerciseNumberBinding

/**
 * Class responsible for whole recycle adapter.
 * Implement members of ExerciseList and current Context
 */
class ExerciseStatusAdapter(private val itemsList: ArrayList<ExerciseModel>,
                            private val context: Context)
    : RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder>(){


    /** Class with custom item*/
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemExerciseNumberBinding.bind(itemView)
    }
    /**
     * Add item with needed content in RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_exercise_number, parent, false))
    }
    /**
     * Assign every item with right content
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assign data
        val model: ExerciseModel = itemsList[position]
        with(holder){
            binding.tvItemExerciseNumber.text = (position + 1).toString()
            /**
             * Change background and text colors, when exercise was ended
             */
            if (model.isFinished){
                itemView.background =
                    ContextCompat.getDrawable(context, R.drawable.circle_green_blue)

                binding.tvItemExerciseNumber
                    .setTextColor(ContextCompat.getColor(context, R.color.myWhite))
            }
        }
    }
    /**
     * Return amount of items we have overall
     */
    override fun getItemCount(): Int {
        return itemsList.size
    }
}