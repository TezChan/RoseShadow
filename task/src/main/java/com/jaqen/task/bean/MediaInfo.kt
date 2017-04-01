package com.jaqen.task.bean

/**
 * @author chenp
 * @version 2017-03-31 11:38
 */
class MediaInfo(var type:MediaType, var path: String, var name: String) {
    enum class MediaType{
        IMAGE, VIDEO, AUDIO
    }
}