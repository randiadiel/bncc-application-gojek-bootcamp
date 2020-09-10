package com.example.bnccapplication.lookup.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnccapplication.R
import com.example.bnccapplication.lookup.LookupData

class LookupAdapter(private val lookUpList: MutableList<LookupData>) : RecyclerView.Adapter<LookupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookupViewHolder {
        return LookupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_look_up, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LookupViewHolder, position: Int) {
        holder.bind(lookUpList[position])
    }

    override fun getItemCount(): Int {
        return lookUpList.size
    }

}