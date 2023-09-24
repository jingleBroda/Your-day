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
import com.example.presentation.main.receiver.CheckTomorrowTaskNotifyReceiver
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    private var alarmManager:AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Base_Theme_YourDay)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val navHost = supportFragmentManager.findFragmentById(
            R.id.FragmentLayout
        ) as NavHostFragment
        navController = navHost.navController
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        startTomorrowTaskAlarm()
    }

    private fun startTomorrowTaskAlarm() {
        val intent = Intent(this, CheckTomorrowTaskNotifyReceiver::class.java)
        intent.flags = Intent.FLAG_RECEIVER_FOREGROUND
        var pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_MUTABLE
        )

        if (pendingIntent == null) {
            Log.d("testRepeatAlarm", "Create")
            // PendingIntent не существует, нужно создать и запустить действие
            pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 21)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        } else {
            Log.d("testRepeatAlarm", "NotCreate")
        }
    }
}