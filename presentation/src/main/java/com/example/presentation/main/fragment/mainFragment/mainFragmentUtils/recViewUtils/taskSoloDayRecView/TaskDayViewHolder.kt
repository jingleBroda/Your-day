package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.taskSoloDayRecView

import android.view.View
import android.widget.ImageView
import com.example.domain.presentationModel.TaskDay
import com.example.presentation.R
import com.example.presentation.databinding.DayTaskLayoutBinding
import com.example.presentation.main.globalUtils.YourDayViewHolder

class TaskDayViewHolder(
    private val item: DayTaskLayoutBinding
): YourDayViewHolder<TaskDay>(item.root), View.OnClickListener {
    private var prTaskCompleteClick = false

    override fun bind(info: TaskDay) {
        with(item){
            taskName.text = info.name
            timeValue.text = info.time
            initTaskComplete(info.complete)

            //TODO() taskSticker
        }
    }

    private fun initTaskComplete(complete:Boolean){
        with(item) {
            if (complete) {
                prTaskCompleteClick = true
                taskComplete.setImageResource(R.drawable.task_complite_ic)
            } else {
                prTaskCompleteClick = false
                taskComplete.setImageResource(R.drawable.task_uncomplite_ic)
            }
            taskComplete.setOnClickListener(this@TaskDayViewHolder)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.taskComplete->{
                v as ImageView
                if (prTaskCompleteClick) v.setImageResource(R.drawable.task_uncomplite_ic)
                else v.setImageResource(R.drawable.task_complite_ic)
                prTaskCompleteClick = !prTaskCompleteClick
                //TODO сохранение статуса выполнения задания()
            }
        }
    }
}