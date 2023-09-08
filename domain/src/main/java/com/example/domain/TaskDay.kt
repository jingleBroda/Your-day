package com.example.domain

data class TaskDay(
    val serialNumberInList:Int, //для DiffUtil
    val name:String,
    val time:String, //Time???
    val sticker:Int, //Resourse
    val complete:Boolean
):InfoInViewHolder()