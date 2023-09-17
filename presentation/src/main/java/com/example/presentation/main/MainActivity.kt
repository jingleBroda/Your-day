package com.example.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.main.activityContract.Navigator

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val navHost = supportFragmentManager.findFragmentById(
            R.id.FragmentLayout
        ) as NavHostFragment
        navController = navHost.navController

        if (savedInstanceState != null) {
            val fragment = supportFragmentManager.findFragmentByTag("createTask")
            if (fragment != null) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
    }

    override fun next(fragment: Fragment, arg: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}