package com.example.domain.presentationModel

data class TaskDay(
    val id:Long, //для DiffUtil
    val day:String,
    val name:String,
    val time:TimeDay, //Time???
    val sticker:Int, //Resourse
    val complete:Boolean
): InfoInViewHolder()