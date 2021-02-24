package com.rssll971.fitnessassistantapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Class responsible for whole recycle adapter.
 * Implement members of ExerciseList and current Context
 */
class ExerciseStatusAdapter(private val itemsList: ArrayList<ExerciseModelClass>, val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder>(){

    /** Class with custom item*/
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var llItem: LinearLayout? = null
        var tvItem: TextView? = null
        init {
            llItem = itemView.findViewById(R.id.ll_item)
            tvItem = itemView.findViewById(R.id.tv_item_exercise_number)
        }
    }
    /**
     * Add item with needed content in RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_exercise_number, parent, false))
    }
    /**
     * Assign every item with right content
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assign data
        val layoutModel: ExerciseModelClass = itemsList[position]
        holder.tvItem!!.text = (position + 1).toString()
        /**
         * Change background and text colors, when exercise was ended
         */
        if (layoutModel.getIsFinished()){
            holder.llItem!!.background = ContextCompat.getDrawable(context, R.drawable.circle_green_blue_color)
            holder.tvItem!!.setTextColor(ContextCompat.getColor(context, R.color.myWhite))
        }
    }
    /**
     * Return amount of items we have overall
     */
    override fun getItemCount(): Int {
        return itemsList.size
    }

}