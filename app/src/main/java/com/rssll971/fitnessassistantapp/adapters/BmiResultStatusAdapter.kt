package com.rssll971.fitnessassistantapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rssll971.fitnessassistantapp.modelclasses.BmiHistoryModelClass
import com.rssll971.fitnessassistantapp.R

/**
 * Next class show all information of users bmi data rom history
 */
class BmiResultStatusAdapter(val context: Context,
                             private val bmiHistoryList: ArrayList<BmiHistoryModelClass>) :
    RecyclerView.Adapter<BmiResultStatusAdapter.MyViewHolder>() {

    /**
     * Class with item components
     */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvItemDate: TextView? = null
        var tvItemWeight: TextView? = null
        var tvItemHeight: TextView? = null
        var tvItemIndex: TextView? = null
        init {
            tvItemDate = itemView.findViewById(R.id.tv_item_bmi_date)
            tvItemWeight = itemView.findViewById(R.id.tv_item_bmi_weight)
            tvItemHeight = itemView.findViewById(R.id.tv_item_bmi_height)
            tvItemIndex = itemView.findViewById(R.id.tv_item_bmi_bmiIndex)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(
           LayoutInflater.from(context).inflate(R.layout.item_bmi_date, parent, false)
       )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assign right data
        val item: BmiHistoryModelClass = bmiHistoryList[position]
        holder.tvItemDate!!.text = item.getDate()
        holder.tvItemWeight!!.text = String.format("%.2f", item.getWeight())
        holder.tvItemHeight!!.text = String.format("%.2f", item.getHeight())
        holder.tvItemIndex!!.text = String.format("%.2f", item.getBmiIndex())
    }

    override fun getItemCount(): Int {
        return bmiHistoryList.size
    }
}