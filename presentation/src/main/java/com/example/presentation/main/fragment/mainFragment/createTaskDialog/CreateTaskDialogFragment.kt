package com.example.presentation.main.fragment.mainFragment.createTaskDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.presentationModel.TaskDay
import com.example.domain.presentationModel.TimeDay
import com.example.presentation.R
import com.example.presentation.databinding.CreateTaskLayoutBinding
import com.example.presentation.main.globalUtils.ViewModelFactory
import dagger.android.support.DaggerDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTaskDialogFragment(
    private val putTaskDay:(TaskDay)->Unit
):DaggerDialogFragment(), View.OnClickListener {
    private lateinit var binding:CreateTaskLayoutBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<CreateTaskViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showsDialog = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateTaskLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            addTaskDbAndCloseDialog.setOnClickListener(this@CreateTaskDialogFragment)
            timePicker2.setIs24HourView(true)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskDbAndCloseDialog->{
                //TODO нужно сделать проверку ввода текста. Нужно сделать обязательным ввод имени и даты. Если дата введена неправильно, то уведомить. Если дата, которая должна быть вида 1.1.1970 введина как 01.01.1970 или 1.01.1970 то она должна пропускаться, НО ее надо форматнутьв нужный вариант!!!

                with(binding){
                    val createTask = TaskDay(
                        0,
                        dayTaskInTextInputLayout.text.toString(),
                        nameTaskInTextInputLayout.text.toString(),
                        TimeDay(timePicker2.hour, timePicker2.minute),
                        0,
                        false
                    )
                    createTaskProgress.visibility = View.VISIBLE
                    addTaskDbAndCloseDialog.visibility = View.GONE
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.createTask(createTask)
                        withContext(Dispatchers.Main){
                            putTaskDay.invoke(createTask)
                            dismiss()
                        }
                    }
                }
            }
        }
    }
}