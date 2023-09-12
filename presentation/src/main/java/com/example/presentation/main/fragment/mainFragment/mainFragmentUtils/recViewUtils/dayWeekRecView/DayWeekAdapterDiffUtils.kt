package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.dayWeekRecView

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.presentationModel.DayWeek

class DayWeekAdapterDiffUtils(
    private val oldList:List<DayWeek>,
    private val newList:List<DayWeek>,
):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].serialNumberInList == newList[newItemPosition].serialNumberInList

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when{
        oldList[oldItemPosition].serialNumberInList != newList[newItemPosition].serialNumberInList->{
            false
        }

        oldList[oldItemPosition].taskList != newList[newItemPosition].taskList->{
            false
        }

        oldList[oldItemPosition].sticker != newList[newItemPosition].sticker->{
            false
        }

        oldList[oldItemPosition].date != newList[newItemPosition].date->{
            false
        }

        oldList[oldItemPosition].weekDayName != newList[newItemPosition].weekDayName->{
            false
        }

        else-> true
    }
}