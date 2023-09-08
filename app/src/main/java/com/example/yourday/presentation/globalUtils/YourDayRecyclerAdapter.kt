package com.example.yourday.presentation.globalUtils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.InfoInViewHolder

abstract class YourDayRecyclerAdapter<VH:ViewHolder, DATA:InfoInViewHolder>(
    list:List<DATA>
):RecyclerView.Adapter<VH>() {
    abstract fun update(newList:List<DATA>)
}