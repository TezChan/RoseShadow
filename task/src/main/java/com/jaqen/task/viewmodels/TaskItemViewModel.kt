package com.jaqen.task.viewmodels

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.text.format.DateUtils
import com.jaqen.task.bean.MediaInfo
import com.jaqen.task.bean.TaskItem
import com.jaqen.task.utils.TimeUtil
import com.kelin.mvvmlight.base.ViewModel
import com.kelin.mvvmlight.command.ReplyCommand
import com.kelin.mvvmlight.messenger.Messenger
import rx.functions.Action0
import java.util.*

/**
 * @author chenp
 * @version 2017-04-11 14:38
 */
class TaskItemViewModel(val taskItem: TaskItem): BaseItemViewModel() {

    /*companion object{
        val MESSAGE_TOKEN_EDIT = "taskItemEdit"
        val MESSAGE_TOKEN_REMOVE = "taskItemRemove"
        val MESSAGE_TOKEN_ITEM_START = "taskItemStart"
        val MESSAGE_TOKEN_ITEM_END = "taskItemEnd"
    }*/

    var listener: TaskItemControlListener? = null
    val timer = Timer()

    val isOverTime = ObservableBoolean(false)
    val isActivited = ObservableBoolean(false)
    val curTime = ObservableField<String>()
    val title = ObservableField<String>()
    val desc = ObservableField<String>()
    val media = ObservableField<MediaInfo>()

    var itemIndex = -1;

    val cmdEdit = ReplyCommand<Any>(Action0 {
        //Messenger.getDefault().send(itemIndex, MESSAGE_TOKEN_EDIT)
        listener?.onItemEdited(itemIndex)
    })

    val cmdRemove = ReplyCommand<Any>(Action0 {
        //Messenger.getDefault().send(itemIndex, MESSAGE_TOKEN_REMOVE)
        listener?.onItemRemoved(itemIndex)
    })

    fun setActivited(activited: Boolean){
        isActivited.set(activited)
    }

    init {
        isActivited.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                if (isActivited.get()){
                    timer.schedule(ItemIimerTask(taskItem.time), 0, 1000)
                    //Messenger.getDefault().send()
                }else{
                    timer.cancel()
                    //Messenger.getDefault().send(itemIndex, MESSAGE_TOKEN_ITEM_END)
                    listener?.onTimeEnd(itemIndex)
                }
            }
        })

        title.set(taskItem.title)
        desc.set(taskItem.text)
        media.set(taskItem.media)
        curTime.set(TimeUtil.duringTime2Str(taskItem.time))
    }

    inner class ItemIimerTask(var time: Long) : TimerTask(){
        override fun run() {
            if (time-- > 0){
                curTime.set(TimeUtil.duringTime2Str(time))
            }else{
                timer.cancel()
                isOverTime.set(true)
                isActivited.set(false)
            }
        }
    }

    interface TaskItemControlListener{
        fun onItemEdited(position: Int)
        fun onItemRemoved(position: Int)
        fun onTimeStart()
        fun onTimeEnd(position: Int)
    }
}