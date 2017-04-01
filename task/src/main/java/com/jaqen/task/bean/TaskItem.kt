package com.jaqen.task.bean

import java.io.Serializable

/**
 * @author chenp
 * @version 2017-03-10 17:00
 */
data class TaskItem(var time: Long, var soundName: String?, var soundPath: String?, var isSoundEnabled: Boolean, var isShocked: Boolean, var text: String?, var media: MediaInfo?, var title: String): Serializable