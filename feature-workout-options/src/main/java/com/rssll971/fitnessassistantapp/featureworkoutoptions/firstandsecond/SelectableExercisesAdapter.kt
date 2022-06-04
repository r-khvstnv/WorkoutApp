package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.ItemExerciseSelectableBinding

class SelectableExercisesAdapter(
    private val context: Context
): RecyclerView.Adapter<SelectableExercisesAdapter.ViewHolder>() {
    private var exerciseList: List<Exercise> = listOf()
    private var selectedIdList: ArrayList<Int> = arrayListOf()

    class ViewHolder(val binding: ItemExerciseSelectableBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemExerciseSelectableBinding =
            ItemExerciseSelectableBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]

        with(holder.binding){
            tvItemName.text = exercise.name
            ivItemSelected.visibility = if (selectedIdList.contains(exercise.id)){
                View.VISIBLE
            } else{
                View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            addOrDeleteIdFromList(exercise.id, position = position)
        }

    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    private fun addOrDeleteIdFromList(value: Int, position: Int){
        if (selectedIdList.contains(value)){
            selectedIdList.remove(value)
        } else{
            selectedIdList.add(value)
        }
        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateExerciseList(list: List<Exercise>){
        exerciseList = list
        notifyDataSetChanged()
    }

    fun getSelectedExercisesIdList(): ArrayList<Int>{
        return selectedIdList
    }

}