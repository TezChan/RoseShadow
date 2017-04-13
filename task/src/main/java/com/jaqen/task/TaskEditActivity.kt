package com.jaqen.task

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.jaqen.task.bean.TaskItem
import com.jaqen.task.databinding.ActivityTaskEditBinding
import com.jaqen.task.fragments.TaskItemEditFragment
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.buildSequence

class TaskEditActivity : AppCompatActivity() {

    private var dataBinding: ActivityTaskEditBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_task_edit)

    }


}
