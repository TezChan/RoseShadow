package com.jaqen.task.viewmodels

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.databinding.ObservableLong
import com.jaqen.task.bean.MediaInfo
import com.jaqen.task.bean.TaskItem
import com.kelin.mvvmlight.base.ViewModel
import com.kelin.mvvmlight.command.ReplyCommand
import rx.functions.Action0
import rx.functions.Action1
import java.util.*

/**
 * @author chenp
 * @version 2017-03-10 16:35
 */
class TaskItemEditViewModel() : ViewModel{

    //val time = ObservableLong()
    val title = ObservableField<String>()
    val hour = ObservableField<String>()
    val minute = ObservableField<String>()
    val second = ObservableField<String>()
    val soundName = ObservableField<String>()
    val isSoundEnabled = ObservableBoolean()
    val isShocked = ObservableBoolean()
    val text = ObservableField<String>()
    val soundPath = ObservableField<String>()
    val isGetForSound = ObservableBoolean()
    val isCancel = ObservableBoolean()
    val isComplete = ObservableBoolean()
    val mediaInfo = ObservableField<MediaInfo>()

    var taskItem: TaskItem? = null
        set(value) {
            if (value == null){
                hour.set(null)
                minute.set(null)
                second.set(null)
                soundName.set(null)
                isSoundEnabled.set(false)
                isShocked.set(false)
                text.set(null)
                soundPath.set(null)
                mediaInfo.set(null)
                title.set("")
            }else{
                //time.set(value.time)
                val secondTime= value.time
                val _second = secondTime % 60
                val _minute = ((secondTime - _second) / 60) % 60
                val _hour = (secondTime - _minute * 60 - _second) / 3600

                hour.set(_hour.toString())
                minute.set(_minute.toString())
                second.set(_second.toString())
                soundName.set(value.soundName)
                isSoundEnabled.set(value.isSoundEnabled)
                isShocked.set(value.isShocked)
                text.set(value.text)
                soundPath.set(value.soundPath)
                mediaInfo.set(value.media)
                title.set(value.title)

                field = value
            }
        }

    constructor(taskItem: TaskItem): this(){
        this.taskItem = taskItem
    }

    val cmdDefaultSound = ReplyCommand<Any>(Action0 {
        soundPath.set("")
        soundName.set("")
    })

    val cmdComplete = ReplyCommand<Any>(Action0 {

        taskItem = TaskItem((hour.get()?.toLong() ?: 0) * 3600
                + (minute.get()?.toLong() ?: 0) * 60
                + (second.get()?.toLong() ?: 0),
                soundName.get(),
                soundPath.get(), isSoundEnabled.get(),
                isShocked.get(), text.get(), mediaInfo.get(), title.get())

        isComplete.set(true)
        isComplete.set(false)
    })

    val cmdFocusChanged = ReplyCommand<Boolean>(Action1 {

        if (!it){
            var _second = second.get()?.toLong() ?: 0
            var _minute = minute.get()?.toLong() ?: 0
            var _hour = hour.get()?.toLong() ?: 0
            var _temp = _second

            _second = _temp % 60
            _minute += (_temp - _second) / 60
            _temp = _minute
            _minute = _temp % 60
            _hour += (_temp - _minute) / 60

            second.set(_second.toString())
            minute.set(_minute.toString())
            hour.set(_hour.toString())
        }
    })

    val cmdCancel = ReplyCommand<Any>(Action0 {
        isCancel.set(true)
    })

    val cmdSelectSound = ReplyCommand<Any>(Action0 {
        isGetForSound.set(true)
        isGetForSound.set(false)
    })

    fun setSound(soundPath: String, soundName: String){
        this.soundPath.set(soundPath)
        this.soundName.set(soundName)
    }

    fun setMedia(media: MediaInfo){
        mediaInfo.set(media)
    }
}