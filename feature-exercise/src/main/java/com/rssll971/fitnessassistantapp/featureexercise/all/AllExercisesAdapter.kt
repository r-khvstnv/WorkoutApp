/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.featureexercise.databinding.ItemExerciseBinding
import com.rssll971.fitnessassistantapp.featureexercise.utils.ItemCallback

internal class AllExercisesAdapter(
    private val context: Context,
    private val callback: ItemCallback
): RecyclerView.Adapter<AllExercisesAdapter.ViewHolder>() {

    private var exerciseList: List<Exercise> = listOf()

    class ViewHolder(val binding: ItemExerciseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemExerciseBinding =
            ItemExerciseBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]

        with(holder.binding){
            tvItemName.text = exercise.name
            tvItemDescription.text = exercise.description

            ivItemDelete.setOnClickListener {
                callback.onDelete(exercise = exercise)
            }
        }

        holder.itemView.setOnClickListener {
            callback.onClick(exercise.id)
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Exercise>){
        exerciseList = list
        notifyDataSetChanged()
    }

}