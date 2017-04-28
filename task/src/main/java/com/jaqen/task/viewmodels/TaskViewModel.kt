package com.jaqen.task.viewmodels

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.jaqen.task.R
import com.jaqen.task.bean.TaskItem
import com.kelin.mvvmlight.base.ViewModel
import com.kelin.mvvmlight.command.ReplyCommand
import rx.functions.Action0
import java.util.*

/**
 * @author chenp
 * @version 2017-04-12 17:27
 */
class TaskViewModel :ViewModel{
    val timer = Timer()

    val title = ObservableField<String>()
    val isEditting = ObservableBoolean(false)
    val addNewItem = ObservableBoolean(false)

    val cmdStart = ReplyCommand<Any>(Action0 {
    })

    val cmdEnd = ReplyCommand<Any>(Action0 {
        if (isEditting.get()){
            addNewItem.set(!addNewItem.get())
        }
    })

    val cmdAdd = ReplyCommand<Any>(Action0 {

    })

    val editCommand = ReplyCommand<Any>(Action0 {
        isEditting.set(!isEditting.get())
    })


    val taskItemControlListener = object : TaskItemViewModel.TaskItemControlListener{
        override fun onItemRemoved(position: Int) {

        }

        override fun onTimeStart() {

        }

        override fun onTimeEnd(position: Int) {
        }

        override fun onItemEdited(position: Int) {
        }

    }

}