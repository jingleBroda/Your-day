package com.example.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.main.activityContract.Navigator
import com.example.presentation.main.alarm.CheckTomorrowTaskNotifyReceiver
import java.util.Calendar

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    private var alarmManager:AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val navHost = supportFragmentManager.findFragmentById(
            R.id.FragmentLayout
        ) as NavHostFragment
        navController = navHost.navController
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        startTomorrowTaskAlarm()
    }

    private fun startTomorrowTaskAlarm(){
        val intent = Intent(this, CheckTomorrowTaskNotifyReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (pendingIntent == null) {
            Log.d("testRepeatAlarm", "Create")
            // PendingIntent не существует, нужно создать и запустить действие
            pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            )

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 0)

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
        else{
            Log.d("testRepeatAlarm", "NotCreate")
        }
    }

    override fun next(fragment: Fragment, arg: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}