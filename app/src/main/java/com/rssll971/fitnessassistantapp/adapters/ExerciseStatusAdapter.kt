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
 * Adapter show user how many exercises were added in current workout
 * and which of them is finished
 */
class ExerciseStatusAdapter(private val itemsList: ArrayList<ExerciseModel>,
                            private val context: Context)
    : RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemExerciseNumberBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_exercise_number, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assign data
        val model: ExerciseModel = itemsList[position]
        with(holder){
            binding.tvItemExerciseNumber.text = (position + 1).toString()
            /** Change background and text colors, when exercise was ended*/
            if (model.isFinished){
                itemView.background =
                    ContextCompat.getDrawable(context, R.drawable.circle_green_blue)
                binding.tvItemExerciseNumber
                    .setTextColor(ContextCompat.getColor(context, R.color.myWhite))
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}