package com.jaqen.task.utils

/**
 * @author chenp
 * @version 2017-04-11 17:27
 */
class TimeUtil {
    companion object{
        @JvmStatic
        fun duringTime2Str(during: Long): String{
            val builder = StringBuilder()
            val second = during % 60
            val mDuring = (during - second) / 60
            val minute = mDuring % 60
            val hour = (mDuring - minute) / 60

            builder.append(hour)
            builder.append(":")
            builder.append(minute)
            builder.append(":")
            builder.append(second)

            return builder.toString()
        }
    }
}