package com.example.yourday.presentation.fragment.mainFragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.TaskDay
import com.example.yourday.R
import com.example.yourday.databinding.FragmentMainMenuBinding
import com.example.yourday.presentation.fragment.mainFragment.mainFragmentUtils.DaySelectionHelper
import com.example.yourday.presentation.fragment.mainFragment.mainFragmentUtils.recViewUtils.TaskDayAdapter
import com.example.yourday.presentation.globalUtils.BaseFragment
import com.example.yourday.presentation.globalUtils.GridSpacingItemDecoration

class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu), View.OnClickListener {
    private var switchButtonMode = true
    private lateinit var binding:FragmentMainMenuBinding
    private lateinit var daySelectionHelper: DaySelectionHelper
    private lateinit var taskDayAdapter:TaskDayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainMenuBinding.bind(view)
        with(binding){
            daySelectionHelper = DaySelectionHelper(
                daySelection
            ){
                if(it == "8.9.2023"){
                    taskRecView.visibility = View.VISIBLE
                    emptyTaskWarning.root.visibility = View.GONE
                }
                else {
                    taskRecView.visibility = View.GONE
                    emptyTaskWarning.root.visibility = View.VISIBLE
                }
            }
            taskDayAdapter = TaskDayAdapter(testGenerateTask())
            taskRecView.layoutManager = GridLayoutManager(requireActivity(),1)
            taskRecView.addItemDecoration(
                GridSpacingItemDecoration(1,50,true)
            )
            taskRecView.adapter = taskDayAdapter
            switchUiButton.switchUiButton.setOnClickListener(this@MainMenuFragment)
            addTaskButton.addTaskButton.setOnClickListener(this@MainMenuFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun testGenerateTask():List<TaskDay> = listOf(
        TaskDay(
            0,
            "Сисечки",
            "9:00",
            0,
            true
        ),
        TaskDay(
            0,
            "Попочки",
            "10:00",
            0,
            true
        ),
        TaskDay(
            0,
            "Писечки",
            "11:00",
            0,
            false
        ),
    )

    private fun changeSwitchButton(){
        if(switchButtonMode){
            binding.switchUiButton.switchUiIc.setImageResource(R.drawable.day_vector)
            binding.switchUiButton.switchUiText.text = getString(R.string.day_string)
        }
        else{
            binding.switchUiButton.switchUiIc.setImageResource(R.drawable.week_vector)
            binding.switchUiButton.switchUiText.text = getString(R.string.week_string)
        }
        switchButtonMode = !switchButtonMode
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskButton->{  }

            R.id.switchUiButton->{ changeSwitchButton() }
        }
    }
}