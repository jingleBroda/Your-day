package com.example.yourday.presentation.fragment.mainFragment

import android.os.Bundle
import android.view.View
import com.example.yourday.R
import com.example.yourday.databinding.FragmentMainMenuBinding
import com.example.yourday.presentation.fragment.BaseFragment
import com.example.yourday.presentation.fragment.mainFragment.mainFragmentUtils.DaySelectionHelper

class MainMenuFragment : BaseFragment(R.layout.fragment_main_menu), View.OnClickListener {
    private var switchButtonMode = true
    private lateinit var binding:FragmentMainMenuBinding
    private lateinit var daySelectionHelper: DaySelectionHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainMenuBinding.bind(view)
        with(binding){
            daySelectionHelper = DaySelectionHelper(
                daySelection
            ){
                println(it)
            }
            switchUiButton.switchUiButton.setOnClickListener(this@MainMenuFragment)
            addTaskButton.addTaskButton.setOnClickListener(this@MainMenuFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun changeSwitchButton(){
        if(switchButtonMode){
            binding.switchUiButton.switchUiIc.setImageResource(R.drawable.day_vector)
            binding.switchUiButton.switchUiText.text = getString(R.string.day_string)
        }
        else{
            binding.switchUiButton.switchUiIc.setImageResource(R.drawable.week_vector)
            binding.switchUiButton.switchUiText.text = getString(R.string.week_string)
        }
        switchButtonMode = !switchButtonMode
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskButton->{  }

            R.id.switchUiButton->{ changeSwitchButton() }
        }
    }
}