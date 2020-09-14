package com.example.bnccapplication.lookup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnccapplication.R
import com.example.bnccapplication.hotline.HotlineData
import com.example.bnccapplication.lookup.LookupData

class LookupAdapter(private val displayList: MutableList<LookupData>) : RecyclerView.Adapter<LookupViewHolder>() {
    private val lookUpList : MutableList<LookupData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        lookUpList.clear()
        lookUpList.addAll(displayList)
        return LookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_look_up, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(displayList[position])
    }

    override fun getItemCount(): Int {
        return displayList.size
    }

    fun updateData(newList: List<LookupData>) {
        displayList.clear()
        displayList.addAll(newList)

        notifyDataSetChanged()
    }

    fun filterData(query: String){
        displayList.clear()
        displayList.addAll(lookUpList.filter { it.provinceName.toLowerCase().contains(query.toLowerCase().toRegex()) }.toMutableList())

        notifyDataSetChanged()
    }
}