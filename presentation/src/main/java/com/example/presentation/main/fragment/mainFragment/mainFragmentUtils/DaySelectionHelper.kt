package com.example.presentation.main.fragment.mainFragment.mainFragmentUtils

import android.widget.TextView
import com.example.presentation.R
import com.example.presentation.databinding.DaySelectionLayoutBinding
import java.util.Calendar

class DaySelectionHelper(
    private val selector: DaySelectionLayoutBinding,
    private val nextWeekClick:((intervalDay:List<String>)->Unit)? = null,
    private val previousWeekClick:((intervalDay:List<String>)->Unit)? = null,
    private val dayOnClick:((day:String)->Unit)? = null,
    ) {
    private val listDay = listOf(
        selector.day1,
        selector.day2,
        selector.day3,
        selector.day4,
        selector.day5,
        selector.day6,
        selector.day7,
    )
    private var activeListItem:TextView? = null

    private val calendar = Calendar.getInstance()
    private lateinit var actualDay:String
    private lateinit var todayDate:String
    private val dateList = mutableListOf<String>()

    init{
        initActualAndTodayDay()
        initCalendar()
    }

    private fun changeActiveListItem(day:TextView){
        if(activeListItem != null){
            paintDefaultDay(listDay[listDay.indexOf(activeListItem)])
        }
        activeListItem = day
        paintActiveDay(activeListItem!!)
    }

    private fun paintActiveDay(day:TextView) =
        day.setBackgroundResource(R.drawable.day_number_active_background)

    private fun paintDefaultDay(day:TextView) =
        day.setBackgroundResource(R.drawable.day_number_background)

    private fun initFun(){
        with(selector){
            //трансформируем range в list, чтобы получать по ходу обхода дней даты
            val iterator = dateList.iterator()
            var findActualDay = false
            //инит ячеек дней
            listDay.forEach{ day->
                val dateDay = iterator.next()

                //Вытаскиваем из даты вида 23.11.2000 только число дня (т.е. 23)
                var dayString = ""
                findDayString@ for(i in dateDay){
                    if(i =='.') break@findDayString else dayString+=i
                }

                day.text = dayString
                if(dateDay == actualDay){
                    findActualDay = true
                    changeActiveListItem(day)
                }

                day.setOnClickListener {
                    actualDay = dateDay
                    changeActiveListItem(day)
                    dayOnClick?.invoke(actualDay)
                }
            }

            if(!findActualDay) paintDefaultDay(activeListItem!!)

            //кнопки перехода на новую неделю
            previousWeek.setOnClickListener{
                initCalendar(false,
                    BackOrNextWeek.BACK
                )
                previousWeekClick?.invoke(dateList)
            }
            nextWeek.setOnClickListener{
                initCalendar(false,
                    BackOrNextWeek.NEXT
                )
                nextWeekClick?.invoke(dateList)
            }
        }
    }

    private fun defaultInitCalendar(){
        dateList.clear()
        val dayToMonday = when(calendar.get(Calendar.DAY_OF_WEEK)){
            Calendar.MONDAY-> 0
            Calendar.TUESDAY-> -1
            Calendar.WEDNESDAY-> -2
            Calendar.THURSDAY-> -3
            Calendar.FRIDAY-> -4
            Calendar.SATURDAY-> -5
            Calendar.SUNDAY-> -6
            else -> {throw Error("Unknown day")}
        }
        calendar.add(Calendar.DAY_OF_WEEK, dayToMonday)
        for(i in 1..7){
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = if(calendar.get(Calendar.MONTH) != 12) calendar.get(Calendar.MONTH) else 0
            val years = calendar.get(Calendar.YEAR)

            dateList.add("$day.${month+1}.$years")
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        //чтобы календарь не перескакивал на понедельник следующей недели, откатываем его до воскресения текущей недели
        calendar.add(Calendar.DAY_OF_YEAR, -1)
    }

    private fun initActualAndTodayDay(){
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = if(calendar.get(Calendar.MONTH) != 12) calendar.get(Calendar.MONTH) else 0
        val years = calendar.get(Calendar.YEAR)
        actualDay = "$day.${month+1}.$years"
        todayDate = "$day.${month+1}.$years"
    }

    private fun initCalendar(firsStart:Boolean = true, backOrNext: BackOrNextWeek? = null){
        if(firsStart){
            defaultInitCalendar()
            initFun()
        }
        else{
            if(backOrNext != null){
                if(backOrNext == BackOrNextWeek.NEXT){
                    calendar.add(Calendar.DAY_OF_YEAR, 7)
                    defaultInitCalendar()
                    initFun()
                }
                else{
                    calendar.add(Calendar.DAY_OF_YEAR, -7)
                    println(calendar.get(Calendar.DAY_OF_MONTH))
                    defaultInitCalendar()
                    initFun()
                }
            }
        }
        updateTimeIntervalUI()
    }

    private fun updateTimeIntervalUI(){
        selector.timeInterval.text = selector.root.context.getString(
            R.string.time_interval_example_string,
            dateList.first(),
            dateList.last()
        )
    }

    fun getToday():String = todayDate
    fun getDayRange():List<String> = dateList

    fun getActiveDay():String = actualDay

    fun saveInstanceState(){}

    private enum class BackOrNextWeek{
        BACK,
        NEXT
    }
}