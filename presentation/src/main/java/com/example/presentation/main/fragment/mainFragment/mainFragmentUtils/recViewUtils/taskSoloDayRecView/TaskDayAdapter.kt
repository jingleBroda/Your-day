package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.taskSoloDayRecView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.presentationModel.TaskDay
import com.example.presentation.databinding.DayTaskLayoutBinding
import com.example.presentation.main.globalUtils.YourDayRecyclerAdapter

class TaskDayAdapter(
    private var listTaskDay:List<TaskDay>
): YourDayRecyclerAdapter<TaskDayViewHolder, TaskDay>(listTaskDay) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDayViewHolder =
        TaskDayViewHolder(DayTaskLayoutBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = listTaskDay.size

    override fun onBindViewHolder(holder: TaskDayViewHolder, position: Int) =
        holder.bind(listTaskDay[position])

    override fun update(newList: List<TaskDay>) {
        val sortedNewList = newList.sortedBy {
            it.time.hour * 60 + it.time.minute
        }
        val diffUtils = TaskDayAdapterDiffUtils(listTaskDay, sortedNewList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        listTaskDay = sortedNewList
        diffResult.dispatchUpdatesTo(this)
    }

    fun addOneTask(newTask:TaskDay) {
        val newList = listTaskDay.toMutableList()
        newList.add(newTask)
        val sortedNewList = newList.sortedBy {
            it.time.hour * 60 + it.time.minute
        }
        val diffUtils = TaskDayAdapterDiffUtils(listTaskDay, sortedNewList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        listTaskDay = sortedNewList
        diffResult.dispatchUpdatesTo(this)
    }

    fun listTaskDayIsEmpty() = listTaskDay.isEmpty()
}