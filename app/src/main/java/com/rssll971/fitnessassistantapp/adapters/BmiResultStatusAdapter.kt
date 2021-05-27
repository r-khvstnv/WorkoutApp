package com.rssll971.fitnessassistantapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.models.BmiHistoryModel
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.databinding.ItemBmiDateBinding

/**
 * Next class show all information of users bmi data rom history
 */
class BmiResultStatusAdapter(val context: Context,
                             private val bmiHistoryList: ArrayList<BmiHistoryModel>) :
    RecyclerView.Adapter<BmiResultStatusAdapter.MyViewHolder>() {

    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemBmiDateBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_bmi_date, parent, false)
       )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assign right data
        val item: BmiHistoryModel = bmiHistoryList[position]

        with(holder){
            binding.tvItemDate.text = item.date
            binding.tvItemWeight.text = String.format("%.2f", item.weight)
            binding.tvItemHeight.text = String.format("%.2f", item.height)
            binding.tvItemBmiIndex.text = String.format("%.2f", item.bmiIndex)
        }
    }

    override fun getItemCount(): Int {
        return bmiHistoryList.size
    }
}