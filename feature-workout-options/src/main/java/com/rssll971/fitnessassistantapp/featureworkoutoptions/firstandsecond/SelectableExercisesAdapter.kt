/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.ItemExerciseSelectableBinding

class SelectableExercisesAdapter(
    private val context: Context
): ListAdapter<ExerciseParam, SelectableExercisesAdapter.ViewHolder>(SelectableExercisesDiff()) {
    private var selectedIdList: ArrayList<Int> = arrayListOf()

    class ViewHolder(val binding: ItemExerciseSelectableBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemExerciseSelectableBinding =
            ItemExerciseSelectableBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)

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

    private fun addOrDeleteIdFromList(value: Int, position: Int){
        if (selectedIdList.contains(value)){
            selectedIdList.remove(value)
        } else{
            selectedIdList.add(value)
        }
        notifyItemChanged(position)
    }

    fun getSelectedExercisesIdList(): ArrayList<Int>{
        return selectedIdList
    }
}

private class SelectableExercisesDiff: DiffUtil.ItemCallback<ExerciseParam>(){
    override fun areItemsTheSame(oldItem: ExerciseParam, newItem: ExerciseParam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseParam, newItem: ExerciseParam): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name
    }
}