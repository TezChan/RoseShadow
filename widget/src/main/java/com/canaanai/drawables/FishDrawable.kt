package com.canaanai.drawables

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator

/**
 * @author chenp
 * @version 2017-07-21 16:43
 */
class FishDrawable: Drawable() {
    private val mPaint: Paint = Paint()
    private var mAlpha: Int = 1
    private var mColorFilter: ColorFilter? = null
    private val mainPoint = PointF(0f,0f)
    private val HEAD_RADIUS = 100.0f
    private val mPath = Path()


    var fishAngle = 0.0
        get() = Math.toDegrees(field)
        set(value) {field = Math.toRadians(value)}

    override fun draw(canvas: Canvas?) {
        canvas?.let {
            drawWave(it)
            drawMainBody(it)
            drawFins(it)
            drawTail(it)
            drawTailfin(it)
        }
    }

    override fun setAlpha(alpha: Int) {
        mAlpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mColorFilter = colorFilter
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    var length = 8.38f * HEAD_RADIUS * 1.2f

    private var curMove = 0.0f
    private var curMove2 = 0.0f

    private fun drawWave(canvas: Canvas){

        val pointDA1 = PointF(curMove2 - length, 370f)
        val pointDA2 = PointF(curMove2 - length * 0.75f, 270f)
        val pointDA23 = PointF(curMove2 - length * 0.5f, 370f)
        val pointDA3 = PointF(curMove2 - length * 0.25f, 470f)
        val pointDAB = PointF(curMove2, 370f)
        val pointDB1 = PointF(curMove2 + length * 0.25f, 270f)
        val pointDB12 = PointF(curMove2 + length * 0.5f, 370f)
        val pointDB2 = PointF(curMove2 + length * 0.75f, 470f)
        val pointDB3 = PointF(curMove2 + length, 370f)

        mPaint.color = Color.parseColor("#FFF2B2")
        mPath.reset()
        mPath.moveTo(pointDA1.x, pointDA1.y)
        mPath.quadTo(pointDA2.x, pointDA2.y, pointDA23.x, pointDA23.y)
        mPath.quadTo(pointDA3.x, pointDA3.y, pointDAB.x, pointDAB.y)
        mPath.quadTo(pointDB1.x, pointDB1.y, pointDB12.x, pointDB12.y)
        mPath.quadTo(pointDB2.x, pointDB2.y, pointDB3.x, pointDB3.y)
        mPath.lineTo(pointDB3.x, intrinsicHeight.toFloat())
        mPath.lineTo(pointDA1.x, intrinsicHeight.toFloat())
        mPath.close()

        canvas.drawPath(mPath, mPaint)

        val pointA1 = PointF(curMove - length, 400f)
        val pointA2 = PointF(curMove - length * 0.75f, 300f)
        val pointA23 = PointF(curMove - length * 0.5f, 400f)
        val pointA3 = PointF(curMove - length * 0.25f, 500f)
        val pointAB = PointF(curMove, 400f)
        val pointB1 = PointF(curMove + length * 0.25f, 300f)
        val pointB12 = PointF(curMove + length * 0.5f, 400f)
        val pointB2 = PointF(curMove + length * 0.75f, 500f)
        val pointB3 = PointF(curMove + length, 400f)

        mPaint.color = Color.parseColor("#FFD300")
        mPath.reset()
        mPath.moveTo(pointA1.x, pointA1.y)
        mPath.quadTo(pointA2.x, pointA2.y, pointA23.x, pointA23.y)
        mPath.quadTo(pointA3.x, pointA3.y, pointAB.x, pointAB.y)
        mPath.quadTo(pointB1.x, pointB1.y, pointB12.x, pointB12.y)
        mPath.quadTo(pointB2.x, pointB2.y, pointB3.x, pointB3.y)
        //mPath.cubicTo(pointA2.x, pointA2.y, pointA3.x, pointA3.y, pointAB.x, pointAB.y)
        //mPath.cubicTo(pointB1.x, pointB1.y, pointB2.x, pointB2.y, pointB3.x, pointB3.y)
        mPath.lineTo(pointB3.x, intrinsicHeight.toFloat())
        mPath.lineTo(pointA1.x, intrinsicHeight.toFloat())
        mPath.close()

        canvas.drawPath(mPath, mPaint)
    }

    private fun drawMainBody(canvas: Canvas){

    }

    private fun drawFins(canvas: Canvas){

    }

    private fun drawTail(canvas: Canvas){

    }

    private fun drawTailfin(canvas: Canvas){

    }

    val animator: ValueAnimator = ValueAnimator.ofFloat(0f, 8.38f * HEAD_RADIUS * 1.2f)
    val animatorD: ValueAnimator = ValueAnimator.ofFloat(0f, 8.38f * HEAD_RADIUS * 1.2f)

    private fun initAnim(){

        //animator.setTarget(curMove)
        animator.duration = 4500
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.RESTART
        animator.addUpdateListener {
            curMove = it.animatedValue as Float
            invalidateSelf()
        }
        animator.start()
    }

    private fun initAnimD(){
        //animator.setTarget(curMove)
        animatorD.duration = 3000
        animatorD.interpolator = LinearInterpolator()
        animatorD.repeatCount = ValueAnimator.INFINITE
        animatorD.repeatMode = ValueAnimator.RESTART
        animatorD.addUpdateListener {
            curMove2 = it.animatedValue as Float
            invalidateSelf()
        }
        animatorD.start()
    }

    fun start(){
        initAnimD()
        initAnim()
    }

    fun stop(){
        animator.cancel()
        animatorD.cancel()
    }
}