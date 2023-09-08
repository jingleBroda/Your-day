package com.example.yourday.presentation.fragment.mainFragment.mainFragmentUtils.recViewUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.TaskDay

class TaskDayAdapterDiffUtils(
    private val oldList:List<TaskDay>,
    private val newList:List<TaskDay>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].serialNumberInList == newList[newItemPosition].serialNumberInList


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when{
        oldList[oldItemPosition].serialNumberInList != newList[newItemPosition].serialNumberInList ->{
            false
        }

        oldList[oldItemPosition].name != newList[newItemPosition].name ->{
            false
        }

        oldList[oldItemPosition].time != newList[newItemPosition].time ->{
            false
        }

        oldList[oldItemPosition].sticker != newList[newItemPosition].sticker ->{
            false
        }

        oldList[oldItemPosition].complete != newList[newItemPosition].complete ->{
            false
        }

        else -> true
    }
}