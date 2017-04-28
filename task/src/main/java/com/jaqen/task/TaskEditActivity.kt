package com.jaqen.task

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jaqen.task.bean.TaskItem
import com.jaqen.task.databinding.ActivityTaskEditBinding
import com.jaqen.task.viewmodels.TaskViewModel

class TaskEditActivity : AppCompatActivity() {
    private object RequestCode{
        val ADD_ITEM = 100
    }

    private var dataBinding: ActivityTaskEditBinding? = null
    private val viewModel = TaskViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_task_edit)

        viewModel.addNewItem.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                startActivityForResult(Intent(this@TaskEditActivity, TaskItemEditActivity::class.java), RequestCode.ADD_ITEM)
            }

        })
        dataBinding?.task = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            when (requestCode){
                RequestCode.ADD_ITEM -> viewModel.addTaskItem(
                        data?.getSerializableExtra(TaskItemEditActivity.RequestData.DATA) as TaskItem)
                else -> {}
            }
        }
    }
}
