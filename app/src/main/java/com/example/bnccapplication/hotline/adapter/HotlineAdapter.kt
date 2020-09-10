package com.example.bnccapplication.hotline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bnccapplication.R
import com.example.bnccapplication.hotline.HotlineData

class HotlineAdapter(private val hotlineList: MutableList<HotlineData>) : RecyclerView.Adapter<HotlineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotlineViewHolder {
        return HotlineViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hotline, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotlineViewHolder, position: Int) {
        holder.bind(hotlineList[position])
    }

    override fun getItemCount(): Int {
        return hotlineList.size
    }

    fun updateData(newList: List<HotlineData>) {
        hotlineList.clear()
        hotlineList.addAll(newList)

        notifyDataSetChanged()
    }
}