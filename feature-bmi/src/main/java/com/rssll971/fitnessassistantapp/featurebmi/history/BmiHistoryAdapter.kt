package com.rssll971.fitnessassistantapp.featurebmi.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import com.rssll971.fitnessassistantapp.featurebmi.databinding.ItemBmiHistoryBinding
import com.rssll971.fitnessassistantapp.featurebmi.utils.Utils

class BmiHistoryAdapter(private val context: Context):
    RecyclerView.Adapter<BmiHistoryAdapter.ViewHolder>() {
    private var bmiList: List<Bmi> = listOf()

        class ViewHolder(val binding: ItemBmiHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBmiHistoryBinding =
            ItemBmiHistoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bmi = bmiList[position]

        with(holder.binding){
            tvItemDate.text = Utils.formatToDate(bmi.date)
            tvItemWeight.text = bmi.weight.toString()
            tvItemBmiIndex.text = Utils.getRoundedFloat(bmi.bmiIndex)
            tvItemBmiStatus.text = Utils.getBmiIndexStatus(bmi.bmiIndex, context = context)
            tvItemBmiStatus.setBackgroundColor(Utils.getBmiIndexStatusColor(bmi.bmiIndex, context = context))
        }
    }

    override fun getItemCount(): Int {
        return bmiList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Bmi>){
        bmiList = list
        notifyDataSetChanged()
    }
}