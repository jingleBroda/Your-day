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
    private lateinit var task:TaskDay
    private var localAdapterOnClickListener:View.OnClickListener? = null

    override fun bind(info: TaskDay, adapterOnClickListener: View.OnClickListener?) {
        with(item){
            task = info
            localAdapterOnClickListener = adapterOnClickListener

            taskName.text = info.name
            run{
                var timeString = if(info.time.hour<10){
                    "0${info.time.hour}"
                } else{
                    "${info.time.hour}"
                }
                timeString = if(info.time.minute<10){
                    timeString+":"+"0${info.time.minute}"
                } else{
                    timeString+":"+"${info.time.minute}"
                }
                timeValue.text = timeString
            }
            initTaskComplete()

            //TODO() taskSticker
        }
    }

    private fun initTaskComplete(){
        with(item) {
            if (task.complete) {
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
                with(task){
                    v.tag = TaskDay(
                        id,
                        day,
                        name,
                        time,
                        sticker,
                        prTaskCompleteClick
                    )
                    localAdapterOnClickListener?.onClick(v)
                }
            }
        }
    }
}