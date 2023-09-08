package com.example.yourday.presentation.globalUtils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.InfoInViewHolder

abstract class YourDayViewHolder<T: InfoInViewHolder>(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(info:T)
}