package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.dayWeekRecView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.presentationModel.DayWeek
import com.example.presentation.databinding.WeekDayLayoutBinding
import com.example.presentation.main.globalUtils.YourDayRecyclerAdapter

class DayWeekAdapter(
    private var listDay: List<DayWeek>
    ): YourDayRecyclerAdapter<DayWeekViewHolder, DayWeek>(listDay) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeekViewHolder =
        DayWeekViewHolder(WeekDayLayoutBinding.inflate(LayoutInflater.from(parent.context)))

    override fun getItemCount(): Int = listDay.size

    override fun onBindViewHolder(holder: DayWeekViewHolder, position: Int) =
        holder.bind(listDay[position])

    override fun update(newList: List<DayWeek>) {
        val diffUtils = DayWeekAdapterDiffUtils(listDay, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        listDay = newList
        diffResult.dispatchUpdatesTo(this)
    }
}