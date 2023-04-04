package com.example.caqao.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.caqao.R

class FaqsAdapter (private var mList : List<FaqsData>):
        RecyclerView.Adapter<FaqsAdapter.FaqsViewHolder>(){

            inner class FaqsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
                val question: TextView = itemView.findViewById(R.id.question)
                val answer: TextView = itemView.findViewById(R.id.answer)
                val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
                val expand_more: LinearLayout = itemView.findViewById(R.id.expand_more)

                fun collapseExpandedView(){
                    answer.visibility = View.GONE
                }
            }

    private fun isAnyItemExpanded(position: Int){
        val temp = mList.indexOfFirst {
            it.isExpandable
        }
        if (temp >= 0 && temp != position){
            mList[temp].isExpandable = false
            notifyItemChanged(temp , 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return FaqsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FaqsViewHolder, position: Int) {
        val FaqsData = mList[position]
        holder.question.text = FaqsData.question
        holder.answer.text = FaqsData.answer

        val isExpandable: Boolean = FaqsData.isExpandable
        holder.answer.visibility = if (isExpandable) View.VISIBLE  else View.GONE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            FaqsData.isExpandable = !FaqsData.isExpandable
            notifyItemChanged(position , Unit)

            if (FaqsData.isExpandable){
                holder.expand_more.animate().rotation(180f)}
            else{
                holder.expand_more.animate().rotation(0f)
            }
        }
    }

    override fun onBindViewHolder(
        holder: FaqsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        if(payloads.isNotEmpty() && payloads[0] == 0){
            holder.collapseExpandedView()
        }else{
            super.onBindViewHolder(holder, position, payloads)

        }
    }

}