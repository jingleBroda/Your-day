package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.dayWeekRecView

import com.example.domain.presentationModel.DayWeek
import com.example.presentation.R
import com.example.presentation.databinding.WeekDayLayoutBinding
import com.example.presentation.main.globalUtils.YourDayViewHolder

class DayWeekViewHolder(
    private val item: WeekDayLayoutBinding,
    ) : YourDayViewHolder<DayWeek>(item.root) {

    override fun bind(info: DayWeek) {
        with(item){
            dayName.text = info.weekDayName
            dateDay.text = info.date
            numberOfTask.text = this.root.context.getString(
                R.string.task_number_string,
                info.taskList.size
            )
        }
    }
}