package com.canaanai.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.airbnb.lottie.LottieAnimationView

/**
 * @author chenp
 * @version 2017-04-18 15:30
 */
class KProgressBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : LottieAnimationView(context, attrs, defStyleAttr) {

    var isIndeterminate = true
    set(value) {
        loop(value)
        field = value

        if (value){
            playAnimation()
        }
    }

    init {
        val ta = getContext().obtainStyledAttributes(attrs, R.styleable.KProgressBar)

        val indeterminateJsonPath = ta.getString(R.styleable.KProgressBar_indeterminateJson)
        val indeterminateImageFolder = ta.getString(R.styleable.KProgressBar_indeterminateImageFolder)

        if (!isInEditMode){
            setAnimation(indeterminateJsonPath ?: "preloader.json")
            indeterminateImageFolder?.let {
                setImageAssetsFolder(it)
            }
        }

        isIndeterminate = ta.getBoolean(R.styleable.KProgressBar_isIndeterminate, true)

        ta.recycle()
    }

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context): this(context, null)

    fun hide(){
        cancelAnimation()
        visibility = View.INVISIBLE
    }

    fun show(){
        visibility = View.VISIBLE

        if (isIndeterminate)
            playAnimation()
    }
}