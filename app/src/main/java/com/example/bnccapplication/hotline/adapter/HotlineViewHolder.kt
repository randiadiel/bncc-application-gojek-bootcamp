package com.example.bnccapplication.hotline.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bnccapplication.hotline.HotlineData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hotline.view.*
import kotlinx.android.synthetic.main.item_look_up.view.*

class HotlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: HotlineData){
        itemView.tv_item_hotline_name.text = data.name
        itemView.tv_item_hotline_phone.text = data.phone
        if(data.imgIcon.isNotBlank()) Picasso.get().load(data.imgIcon).into(itemView.iv_item_hotline_image)
    }
}