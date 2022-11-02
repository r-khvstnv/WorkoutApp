/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.featurebmi.databinding.ItemBmiHistoryBinding
import com.rssll971.fitnessassistantapp.featurebmi.utils.Utils

internal class BmiHistoryAdapter(private val context: Context):
    ListAdapter<BmiParam, BmiHistoryAdapter.ViewHolder>(BmiHistoryDiff()) {

        class ViewHolder(val binding: ItemBmiHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBmiHistoryBinding =
            ItemBmiHistoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bmi = getItem(position)

        with(holder.binding){
            tvItemDate.text = Utils.formatToDate(bmi.date)
            tvItemWeight.text = bmi.weight.toString()
            tvItemBmiIndex.text = Utils.getRoundedFloat(bmi.bmiIndex)
            tvItemBmiStatus.text = Utils.getBmiIndexStatus(bmi.bmiIndex, context = context)
            tvItemBmiStatus.setBackgroundColor(Utils.getBmiIndexStatusColor(bmi.bmiIndex, context = context))
        }
    }
}

private class BmiHistoryDiff: DiffUtil.ItemCallback<BmiParam>() {
    override fun areItemsTheSame(oldItem: BmiParam, newItem: BmiParam): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: BmiParam, newItem: BmiParam): Boolean {
        return oldItem.id == newItem.id && oldItem.date == newItem.date
    }

}