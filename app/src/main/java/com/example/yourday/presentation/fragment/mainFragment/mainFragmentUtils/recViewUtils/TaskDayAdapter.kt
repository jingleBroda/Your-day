package com.example.yourday.presentation.fragment.mainFragment.mainFragmentUtils.recViewUtils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.TaskDay
import com.example.yourday.databinding.DayTaskLayoutBinding
import com.example.yourday.presentation.globalUtils.YourDayRecyclerAdapter

class TaskDayAdapter(
    private var listTaskDay:List<TaskDay>
): YourDayRecyclerAdapter<TaskDayViewHolder, TaskDay>(listTaskDay) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDayViewHolder =
        TaskDayViewHolder(DayTaskLayoutBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = listTaskDay.size

    override fun onBindViewHolder(holder: TaskDayViewHolder, position: Int) =
        holder.bind(listTaskDay[position])

    override fun update(newList: List<TaskDay>) {
        val diffUtils = TaskDayAdapterDiffUtils(listTaskDay, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        listTaskDay = newList
        diffResult.dispatchUpdatesTo(this)
    }
}