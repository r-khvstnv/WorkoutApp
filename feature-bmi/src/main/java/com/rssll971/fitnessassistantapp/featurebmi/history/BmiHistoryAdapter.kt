/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.featurebmi.databinding.ItemBmiHistoryBinding
import com.rssll971.fitnessassistantapp.featurebmi.utils.Utils

class BmiHistoryAdapter(private val context: Context):
    RecyclerView.Adapter<BmiHistoryAdapter.ViewHolder>() {
    private var bmiParamList: List<BmiParam> = listOf()

        class ViewHolder(val binding: ItemBmiHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBmiHistoryBinding =
            ItemBmiHistoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bmi = bmiParamList[position]

        with(holder.binding){
            tvItemDate.text = Utils.formatToDate(bmi.date)
            tvItemWeight.text = bmi.weight.toString()
            tvItemBmiIndex.text = Utils.getRoundedFloat(bmi.bmiIndex)
            tvItemBmiStatus.text = Utils.getBmiIndexStatus(bmi.bmiIndex, context = context)
            tvItemBmiStatus.setBackgroundColor(Utils.getBmiIndexStatusColor(bmi.bmiIndex, context = context))
        }
    }

    override fun getItemCount(): Int {
        return bmiParamList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<BmiParam>){
        bmiParamList = list
        notifyDataSetChanged()
    }
}