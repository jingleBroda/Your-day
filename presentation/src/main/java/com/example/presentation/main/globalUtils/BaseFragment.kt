package com.example.presentation.main.globalUtils

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment

abstract class BaseFragment(@LayoutRes contentLayoutId:Int):DaggerFragment(contentLayoutId)