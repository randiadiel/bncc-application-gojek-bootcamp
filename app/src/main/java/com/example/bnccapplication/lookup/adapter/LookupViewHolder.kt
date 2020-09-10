package com.example.bnccapplication.lookup.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bnccapplication.lookup.LookupData
import kotlinx.android.synthetic.main.item_look_up.view.*

class LookupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data : LookupData){
        itemView.tv_item_lookup_provinceName.text = data.provinceName
        itemView.tv_item_lookup_confirmedCase.text = "${data.numberOfConfirmedCases}"
        itemView.tv_item_lookup_recoveredCase.text = "${data.numberOfRecoveredCases}"
        itemView.tv_item_lookup_deathCase.text = "${data.numberOfDeathCases}"
    }
}