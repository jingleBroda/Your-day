package com.example.presentation.main.fragment.mainFragment.createTaskDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
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
    private val putTaskDay:((TaskDay)->Unit)?
):DaggerDialogFragment(), View.OnClickListener {
    constructor():this(null)

    private lateinit var binding:CreateTaskLayoutBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<CreateTaskViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(putTaskDay == null) dismiss()
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
            changeInputTypeDayTask.setOnCheckedChangeListener{ btnV, isChecked->
                if(isChecked){
                    dayTaskInTextInputLayout.inputType = EditorInfo.TYPE_TEXT_VARIATION_NORMAL
                }
                else{
                    dayTaskInTextInputLayout.inputType = EditorInfo.TYPE_DATETIME_VARIATION_DATE
                }
            }
            addTaskDbAndCloseDialog.setOnClickListener(this@CreateTaskDialogFragment)
            timePicker2.setIs24HourView(true)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.addTaskDbAndCloseDialog->{
                addTask()
            }
        }
    }

    private fun addTask(){
        with(binding){
            createTaskProgress.visibility = View.VISIBLE
            addTaskDbAndCloseDialog.visibility = View.GONE
            val verifiedData = checkData()
            if(verifiedData != null){
                val createTask = TaskDay(
                    (0L..1000L).random(),
                    verifiedData.day,
                    verifiedData.name,
                    TimeDay(timePicker2.hour, timePicker2.minute),
                    0,
                    false
                )

                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.getTaskReadDayUseCase(verifiedData.day)
                    withContext(Dispatchers.Main){
                        if(!viewModel.isDuplicateTask(createTask.time)){
                            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.createTask(createTask)
                                withContext(Dispatchers.Main){
                                    putTaskDay!!.invoke(createTask)
                                    dismiss()
                                }
                            }
                        }
                        else{
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.duplicate_task_error),
                                Toast.LENGTH_SHORT
                            ).show()
                            createTaskProgress.visibility = View.GONE
                            addTaskDbAndCloseDialog.visibility = View.VISIBLE
                        }
                    }
                }
            }
            else{
                createTaskProgress.visibility = View.GONE
                addTaskDbAndCloseDialog.visibility = View.VISIBLE
            }
        }
    }

    //Формальная проверка введенных данных. Полностью учитывать все ошибки ввода тут не хочу, поэтому пусть будет хоть так.
    private fun checkData():VerifiedData?{
        with(binding){
            val verifiedName = nameTaskInTextInputLayout.text.toString()
            var verifiedData = dayTaskInTextInputLayout.text.toString()
            if(verifiedName.isEmpty()){
                nameTaskInTextInputLayout.error = getString(R.string.empty_name_task_error_string)
                return null
            }
            else{
                if(verifiedName[0] == ' '){
                    nameTaskInTextInputLayout.error = getString(R.string.delete_space_error_string)
                    return null
                }
            }

            val datePars = mutableListOf("","","")
            var i = 0
            verifiedData.forEach {
                if(it == '.') i++ else datePars[i] = datePars[i]+it
            }
            if(datePars[0][0] == '0') datePars[0] = datePars[0].removeRange(0..0)
            if(datePars[1][0] == '0') datePars[1] = datePars[1].removeRange(0..0)
            verifiedData = datePars[0]+"."+datePars[1]+"."+datePars[2]
            return VerifiedData(verifiedData,verifiedName)
        }
    }

    private data class VerifiedData(
        val day:String,
        val name:String,
    )
}