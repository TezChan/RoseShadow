package com.jaqen.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jaqen.task.bean.TaskItem
import com.jaqen.task.fragments.TaskItemEditFragment

class TaskItemEditActivity : AppCompatActivity() {
    object RequestData{
        @JvmStatic
        val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_item_edit)

        val taskItemFragment = supportFragmentManager.findFragmentById(R.id.fragmentTaskEdit) as TaskItemEditFragment

        taskItemFragment.setTaskItemEditListener(object : TaskItemEditFragment.TaskItemEditListener{
            override fun onEditCancel() {

            }

            override fun onEditComplete(taskItem: TaskItem?) {
                Toast.makeText(this@TaskItemEditActivity, taskItem?.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}
