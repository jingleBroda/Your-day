package com.example.presentation.main.fragment.mainFragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.presentationModel.DayWeek
import com.example.domain.presentationModel.TaskDay
import com.example.presentation.R
import com.example.presentation.databinding.FragmentMainMenuBinding
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.DaySelectionHelper
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.dayWeekRecView.DayWeekAdapter
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.taskSoloDayRecView.TaskDayAdapter
import com.example.presentation.main.globalUtils.BaseFragment
import com.example.presentation.main.globalUtils.GridSpacingItemDecoration

class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu), View.OnClickListener {
    private var switchButtonMode = true
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var daySelectionHelper: DaySelectionHelper
    private lateinit var taskDayAdapter: TaskDayAdapter
    private lateinit var dayWeekAdapter: DayWeekAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainMenuBinding.bind(view)
        with(binding){
            val dayOnClick:(String)->Unit = {
                if(it == daySelectionHelper.getToday()){
                    taskDayAdapter.update(testGenerateTask())
                    taskDayRecView.visibility = View.VISIBLE
                    emptyTaskWarning.root.visibility = View.INVISIBLE
                }
                else {
                    taskDayRecView.visibility = View.INVISIBLE
                    emptyTaskWarning.root.visibility = View.VISIBLE
                }
            }
            val updateWeekClick:()->Unit = {
                if(!switchButtonMode){
                    if(testGenerateDayWeek().isNotEmpty()){
                        dayWeekAdapter.update(testGenerateDayWeek())
                        taskDayRecView.visibility = View.VISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                    else{
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.VISIBLE
                    }
                }
            }
            daySelectionHelper = DaySelectionHelper(
                selector = daySelection,
                nextWeekClick = updateWeekClick,
                previousWeekClick = updateWeekClick,
                dayOnClick = dayOnClick
            )

            taskDayAdapter = TaskDayAdapter(testGenerateTask())
            dayWeekAdapter = DayWeekAdapter(testGenerateDayWeek())
            taskDayRecView.layoutManager = GridLayoutManager(requireActivity(),1)
            taskDayRecView.addItemDecoration(
                GridSpacingItemDecoration(1,50,true)
            )
            taskDayRecView.adapter =  taskDayAdapter

            switchUiButton.switchUiButton.setOnClickListener(this@MainMenuFragment)
            addTaskButton.addTaskButton.setOnClickListener(this@MainMenuFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    //TODO потом удалить
    private fun testGenerateTask():List<TaskDay> = listOf(
        TaskDay(
            0,
            "Сисечки",
            "9:00",
            0,
            true
        ),
        TaskDay(
            1,
            "Попочки",
            "10:00",
            0,
            true
        ),
        TaskDay(
            2,
            "Писечки",
            "11:00",
            0,
            false
        ),
        TaskDay(
            3,
            "Пиво",
            "12:00",
            0,
            false
        ),
        TaskDay(
            4,
            "Водка",
            "13:00",
            0,
            false
        ),
    )

    //TODO потом удалить
    private fun testGenerateDayWeek():List<DayWeek>{
        val result = mutableListOf<DayWeek>()
        val dayNameList = resources.getStringArray(R.array.day_week_name)
        val iterator = dayNameList.iterator()
        var i = 0
        daySelectionHelper.getDayRange().forEach {
            val dayWeek = DayWeek(
                i,
                if(it == daySelectionHelper.getToday()) testGenerateTask() else listOf(),
                0,
                it,
                iterator.next()
            )
            i++
            if(dayWeek.taskList.isNotEmpty()) result.add(dayWeek)
        }
        return result
    }


    private fun changeSwitchButton(){
        with(binding){
            //проверяем, какой вид отображения нужно показать (при true показываем вид недели, при else показываем конкретный день)
            if(switchButtonMode){
                //изменяем вид кнопки
                switchUiButton.switchUiIc.setImageResource(R.drawable.day_vector)
                switchUiButton.switchUiText.text = getString(R.string.day_string)
                with(daySelection){
                    //меняем вид верхней панели управления показа дней
                    listDayLayout.visibility = View.GONE
                    timeInterval.visibility = View.VISIBLE

                    taskDayRecView.adapter = dayWeekAdapter
                    if(testGenerateDayWeek().isNotEmpty()){
                        dayWeekAdapter.update(testGenerateDayWeek())
                        taskDayRecView.visibility = View.VISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                    else{
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.VISIBLE
                    }
                }
            }
            else{
                //изменяем вид кнопки
                switchUiButton.switchUiIc.setImageResource(R.drawable.week_vector)
                switchUiButton.switchUiText.text = getString(R.string.week_string)
                with(daySelection){
                    //меняем вид верхней панели управления показа дней
                    listDayLayout.visibility = View.VISIBLE
                    timeInterval.visibility = View.GONE

                    taskDayRecView.adapter = taskDayAdapter
                    if(daySelectionHelper.getActualDay() == daySelectionHelper.getToday()){
                        taskDayAdapter.update(testGenerateTask())
                        taskDayRecView.visibility = View.VISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                    else {
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.VISIBLE
                    }
                }
            }
            switchButtonMode = !switchButtonMode
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskButton->{  }

            R.id.switchUiButton->{ changeSwitchButton() }
        }
    }
}