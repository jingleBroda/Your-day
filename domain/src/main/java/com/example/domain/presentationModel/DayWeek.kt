package com.example.domain.presentationModel

data class DayWeek(
    val serialNumberInList:Int, //для DiffUtil
    val taskList:List<TaskDay>,
    val sticker:Int, //Resourse
    val date:String,
    val weekDayName:String,
):InfoInViewHolder()