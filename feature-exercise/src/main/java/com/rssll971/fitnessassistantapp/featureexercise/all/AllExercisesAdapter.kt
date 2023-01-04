/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.featureexercise.databinding.ItemExerciseBinding

internal class AllExercisesAdapter(
    private val context: Context,
    private val onItemClicked: (Int) -> Unit,
    private val onItemDelete: (ExerciseParam) -> Unit,
): ListAdapter<ExerciseParam, AllExercisesAdapter.ViewHolder>(AllExercisesDiff()) {

    class ViewHolder(val binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemExerciseBinding =
            ItemExerciseBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = getItem(position)

        with(holder.binding){
            tvItemName.text = exercise.name
            tvItemDescription.text = exercise.description

            ivItemDelete.setOnClickListener {
                onItemDelete(exercise)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClicked(exercise.id)
        }
    }
}

private class AllExercisesDiff: DiffUtil.ItemCallback<ExerciseParam>(){
    override fun areItemsTheSame(oldItem: ExerciseParam, newItem: ExerciseParam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ExerciseParam, newItem: ExerciseParam): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.description == newItem.description &&
                oldItem.imagePath == newItem.imagePath
    }
}