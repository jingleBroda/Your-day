package com.example.presentation.main.globalUtils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.presentationModel.InfoInViewHolder

abstract class YourDayViewHolder<T: InfoInViewHolder>(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(info:T, adapterOnClickListener:View.OnClickListener? = null)
}