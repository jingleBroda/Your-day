package com.example.yourday.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.yourday.R
import com.example.yourday.databinding.ActivityMainBinding
import com.example.yourday.presentation.activityContract.Navigator

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        val navHost = supportFragmentManager.findFragmentById(
            R.id.FragmentLayout
        ) as NavHostFragment
        navController = navHost.navController
        //NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun next(fragment: Fragment, arg: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun back() {
        TODO("Not yet implemented")
    }
}