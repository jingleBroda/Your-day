package com.example.presentation.main.fragment.mainFragment

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.presentationModel.DayWeek
import com.example.domain.presentationModel.TaskDay
import com.example.presentation.R
import com.example.presentation.databinding.FragmentMainMenuBinding
import com.example.presentation.main.receiver.TaskNotifyReceiver
import com.example.presentation.main.fragment.mainFragment.createTaskDialog.CreateTaskDialogFragment
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.DaySelectionHelper
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.dayWeekRecView.DayWeekAdapter
import com.example.presentation.main.fragment.mainFragment.mainFragmentUtils.recViewUtils.taskSoloDayRecView.TaskDayAdapter
import com.example.presentation.main.globalUtils.BaseFragment
import com.example.presentation.main.globalUtils.GridSpacingItemDecoration
import com.example.presentation.main.globalUtils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu), View.OnClickListener {
    private var switchButtonMode = true
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var daySelectionHelper: DaySelectionHelper
    private lateinit var taskDayAdapter: TaskDayAdapter
    private var dayWeekDataEmpty = true
    private lateinit var dayWeekAdapter: DayWeekAdapter
    private var alarmManager:AlarmManager? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<MainMenuViewModel> {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainMenuBinding.bind(view)
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        with(binding){
            run{
                val dayOnClick:(String)->Unit = { selectedDay->
                    val listTask = viewModel.weekTask[selectedDay]
                    if(!listTask.isNullOrEmpty()){
                        taskDayAdapter.update(listTask)
                        taskDayRecView.adapter = taskDayAdapter
                        taskDayRecView.visibility = View.VISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                    else{
                        taskDayAdapter.update(listOf())
                        taskDayRecView.adapter = taskDayAdapter
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.VISIBLE
                    }
                }

                val updateWeekClick:(List<String>)->Unit = { intervalDay->
                    run{
                        val saveTaskDayRecViewVisibility = taskDayRecView.visibility
                        val saveEmptyTaskWarningVisibility = emptyTaskWarning.root.visibility
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE

                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                            viewModel.getWeekTask(intervalDay)
                            withContext(Dispatchers.Main){
                                progressBar.visibility = View.GONE
                                updateDayWeekAdapter()
                                if(!switchButtonMode){
                                    if(dayWeekDataEmpty){
                                        taskDayRecView.visibility = View.VISIBLE
                                    }
                                    else{
                                        emptyTaskWarning.root.visibility = View.VISIBLE
                                    }
                                }
                                else{
                                    taskDayRecView.visibility = saveTaskDayRecViewVisibility
                                    emptyTaskWarning.root.visibility = saveEmptyTaskWarningVisibility
                                }
                            }
                        }
                    }
                }
                daySelectionHelper = DaySelectionHelper(
                    selector = daySelection,
                    nextWeekClick = updateWeekClick,
                    previousWeekClick = updateWeekClick,
                    dayOnClick = dayOnClick
                )
            }

            taskDayAdapter = TaskDayAdapter(listOf(), this@MainMenuFragment)
            dayWeekAdapter = DayWeekAdapter(listOf())
            initTask()
            taskDayRecView.layoutManager = GridLayoutManager(requireActivity(),1)
            taskDayRecView.addItemDecoration(
                GridSpacingItemDecoration(1,50,true)
            )
            taskDayRecView.adapter = taskDayAdapter

            switchUiButton.root.setOnClickListener(this@MainMenuFragment)
            addTaskButton.root.setOnClickListener(this@MainMenuFragment)
            deleteDbButton.root.setOnClickListener(this@MainMenuFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initTask(){
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getWeekTask(daySelectionHelper.getDayRange())
            withContext(Dispatchers.Main){
                binding.progressBar.visibility = View.GONE
                val taskList = viewModel.weekTask[daySelectionHelper.getToday()]
                if(!taskList.isNullOrEmpty()){
                    taskDayAdapter.update(taskList)
                    binding.taskDayRecView.visibility = View.VISIBLE
                }
                else{
                    binding.emptyTaskWarning.root.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun updateDayWeekAdapter(){
        dayWeekDataEmpty = false
        val result = mutableListOf<DayWeek>()
        var i = 0
        val dayNameList = resources.getStringArray(R.array.day_week_name)
        val iterator = dayNameList.iterator()
        viewModel.weekTask.forEach { (day, listTask) ->
            val localDayWeek = DayWeek(
                i,
                listTask,
                0,
                day,
                iterator.next()
            )
            i++
            if(listTask.isNotEmpty() && !dayWeekDataEmpty)
                dayWeekDataEmpty = true

            if(listTask.isNotEmpty()) result.add(localDayWeek)
        }
        if(dayWeekDataEmpty) dayWeekAdapter.update(result)
    }

    private fun changeSwitchButton(){
        with(binding){
            if(switchButtonMode){
                //режим ресайклера ПО ВСЕЙ НЕДЕЛЕ СРАЗУ
                switchUiButton.switchUiIc.setImageResource(R.drawable.day_vector)
                switchUiButton.switchUiText.text = getString(R.string.day_string)
                with(daySelection){
                    listDayLayout.visibility = View.GONE
                    timeInterval.visibility = View.VISIBLE

                    updateDayWeekAdapter()
                    taskDayRecView.adapter = dayWeekAdapter
                    if(dayWeekDataEmpty){
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
                //режим ресайклера ПО 1 ДНЮ
                switchUiButton.switchUiIc.setImageResource(R.drawable.week_vector)
                switchUiButton.switchUiText.text = getString(R.string.week_string)
                with(daySelection){
                    listDayLayout.visibility = View.VISIBLE
                    timeInterval.visibility = View.GONE

                    taskDayRecView.adapter = taskDayAdapter
                    if(taskDayAdapter.listTaskDayIsEmpty()){
                        taskDayRecView.visibility = View.INVISIBLE
                        emptyTaskWarning.root.visibility = View.VISIBLE
                    }
                    else{
                        taskDayRecView.visibility = View.VISIBLE
                        emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                }
            }
            switchButtonMode = !switchButtonMode
        }
    }

    private fun showCreateTaskDialogFragment(){
        val dialog = CreateTaskDialogFragment{ newTask->
            Log.d("testCreateTask", newTask.toString())
            Log.d("testCreateTask", viewModel.weekTask.toString())
            Log.d("testCreateTask", daySelectionHelper.getActiveDay())
            if(viewModel.weekTask.containsKey(newTask.day)){
                viewModel.addTaskInCreateDialog(newTask)

                updateDayWeekAdapter()
                if(daySelectionHelper.getActiveDay() == newTask.day){
                    taskDayAdapter.addOneTask(newTask)
                }

                if(!switchButtonMode){
                    binding.taskDayRecView.visibility = View.VISIBLE
                    binding.emptyTaskWarning.root.visibility = View.INVISIBLE
                }
                else{
                    if(daySelectionHelper.getActiveDay() == newTask.day){
                        binding.taskDayRecView.visibility = View.VISIBLE
                        binding.emptyTaskWarning.root.visibility = View.INVISIBLE
                    }
                }
            }
            notifyTodayTask(newTask)
        }
        dialog.show(parentFragmentManager, "createTask")
    }

    private fun notifyTodayTask(newTask: TaskDay){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, newTask.time.hour)
        calendar.set(Calendar.MINUTE, newTask.time.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        if(
            (Calendar.getInstance().timeInMillis < calendar.timeInMillis)&&
            (daySelectionHelper.getToday() == newTask.day)
        ){
            val massageHourNotify =
                if(newTask.time.hour < 10) "0${newTask.time.hour}" else "${newTask.time.hour}"
            val massageMinNotify =
                if(newTask.time.minute < 10) "0${newTask.time.minute}" else "${newTask.time.minute}"
            val alarmIntent = Intent(context, TaskNotifyReceiver::class.java).let { intent->
                intent.putExtra(
                    TaskNotifyReceiver.keyTaskDayMessage,
                    "${newTask.name} ${massageHourNotify}:${massageMinNotify}"
                )

                var requestCodeString = ""
                run{
                    var dotCount = 0
                    newTask.day.forEach {
                        if(it != '.'){
                            if(dotCount != 2) requestCodeString+=it
                        }
                        else{
                            dotCount++
                        }
                    }
                }
                requestCodeString += newTask.time.hour.toString() + newTask.time.minute.toString()
                PendingIntent.getBroadcast(
                    requireActivity().applicationContext,
                    requestCodeString.toInt(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

            val alarmClockInfo = AlarmClockInfo(calendar.timeInMillis, null)
            alarmManager?.setAlarmClock(
                alarmClockInfo,
                alarmIntent
            )
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskButton-> showCreateTaskDialogFragment()

            R.id.switchUiButton-> changeSwitchButton()

            R.id.deleteDbButton-> viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.deleteAllTask()
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        requireContext(),
                        "Delete All Task!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.taskComplete-> viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val task = v.tag as TaskDay
                viewModel.updateCompleteStatusTask(
                    task.day,
                    task.name,
                    task.complete
                )
                withContext(Dispatchers.Main){
                    initTask()
                }
            }
        }
    }
}