package com.jaqen.task.viewmodels

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableList
import com.jaqen.task.BR
import com.jaqen.task.R
import com.jaqen.task.bean.TaskItem
import com.kelin.mvvmlight.base.ViewModel
import com.kelin.mvvmlight.command.ReplyCommand
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import rx.functions.Action0

/**
 * @author chenp
 * @version 2017-04-12 17:27
 */
class TaskViewModel :ViewModel{
    val title = ObservableField<String>()
    val isEditting = ObservableBoolean(false)

    val cmdStart = ReplyCommand<Any>(Action0 {

    })

    val cmdEnd = ReplyCommand<Any>(Action0 {

    })

    val cmdAdd = ReplyCommand<Any>(Action0 {

    })

    val taskItems = ObservableArrayList<TaskItem>()
    val onItemBind = OnItemBind<ViewModel> { itemBinding, position, item ->
        when(position){
            0 -> itemBinding.set(ItemBinding.VAR_NONE, R.layout.view_task_item_start)
                    .bindExtra(BR.cmdStart, cmdStart)
                    .bindExtra(BR.isEditing, isEditting)
            taskItems.size - 1 -> itemBinding.set(ItemBinding.VAR_NONE, R.layout.view_task_item_end)
                    .bindExtra(BR.isEditing, isEditting)
                    .bindExtra(BR.cmdEnd, cmdEnd)
            else -> itemBinding.set(BR.taskItem, R.layout.view_item_task_item_list)
        }
    }
    val editCommand = ReplyCommand<Any>(Action0 {
        isEditting.set(!isEditting.get())
    })
}