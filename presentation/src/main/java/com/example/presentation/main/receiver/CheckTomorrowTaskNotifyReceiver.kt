package com.example.presentation.main.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.domain.dataAbstract.useCase.GetDayTaskUseCase
import com.example.domain.presentationModel.TaskDay
import com.example.presentation.main.globalUtils.goAsync
import dagger.android.DaggerBroadcastReceiver
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class CheckTomorrowTaskNotifyReceiver : DaggerBroadcastReceiver() {
    @Inject
    lateinit var getDayTaskUseCase:GetDayTaskUseCase
    private var taskList = listOf<TaskDay>()

    override fun onReceive(context: Context, intent: Intent) {
        goAsync {
            Log.d("testRepeatAlarm", "Alarm Start!")
            withContext(this.coroutineContext) {
                taskList = getDayTaskUseCase.invoke(getTomorrow())
            }
            if(taskList.isNotEmpty()){
                taskList.forEach { task->
                    createTomorrowTaskNotify(context, task)
                    delay(100)
                }
            }
            else{
                Log.d("testRepeatAlarm", "taskList empty!")
            }
        }
        super.onReceive(context, intent)
    }

    private fun getTomorrow():String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = if(calendar.get(Calendar.MONTH) != 12) calendar.get(Calendar.MONTH) else 0
        val years = calendar.get(Calendar.YEAR)
        return "${day}.${month+1}.$years"
    }

    private fun createTomorrowTaskNotify(context:Context, task:TaskDay){
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, task.time.hour)
        calendar.set(Calendar.MINUTE, task.time.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val massageHourNotify =
            if(task.time.hour < 10) "0${task.time.hour}" else "${task.time.hour}"
        val massageMinNotify =
            if(task.time.minute < 10) "0${task.time.minute}" else "${task.time.minute}"

        val alarmIntent = Intent(context, TaskNotifyReceiver::class.java).let { intent->
            intent.putExtra(
                TaskNotifyReceiver.keyTaskDayMessage,
                "${task.name} $massageHourNotify:$massageMinNotify"
            )

            //формируем requestCode
            var requestCodeString = ""
            run{
                var dotCount = 0
                task.day.forEach {
                    if(it != '.'){
                        if(dotCount != 2) requestCodeString+=it
                    }
                    else{
                        dotCount++
                    }
                }
            }
            requestCodeString += task.time.hour.toString() + task.time.minute.toString()

            PendingIntent.getBroadcast(
                context,
                requestCodeString.toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }

        val alarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, null)
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setAlarmClock(
            alarmClockInfo,
            alarmIntent
        )
    }
}