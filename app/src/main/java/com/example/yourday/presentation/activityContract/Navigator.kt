package com.example.yourday.presentation.activityContract

import android.os.Bundle
import androidx.fragment.app.Fragment

interface Navigator {
    fun next(fragment: Fragment, arg: Bundle?)
    fun back()
}

fun Fragment.navigator() = requireActivity() as Navigator