package com.example.mydemoapplication.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.mydemoapplication.R
import kotlin.math.abs
import kotlin.math.min
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class ConnectingView : View {

    val TAG = "ConnectingView"

    private var paint: Paint? = null
    private var timePaint: Paint? = null
    private var timeUnitPaint: Paint? = null
    private var currentAngle = 0f // 当前扇形的角度
    private var currentTime = 0L // 剩余时长
    private val ovalRectF by lazy {
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = (min(width.toDouble(), height.toDouble()) / 2).toInt() // 扇形半径
        RectF((width / 2 - radius), (height / 2 - radius), (width / 2 + radius), (height / 2 + radius))
    }

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        setBackgroundResource(R.mipmap.image_loading_bg)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint?.color = Color.parseColor("#CCFFFFFF") // 扇形颜色
        paint?.style = Paint.Style.FILL
        paint?.strokeWidth = 10f // 扇形边框宽度

        timePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timePaint?.color = Color.parseColor("#FF000000") // 剩余时长颜色
        timePaint?.textSize = 44f
        timePaint?.style = Paint.Style.FILL
        timePaint?.isFakeBoldText = true

        timeUnitPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        timeUnitPaint?.color = Color.parseColor("#FF000000") // 剩余时长单位颜色
        timeUnitPaint?.textSize = 44f
        timeUnitPaint?.style = Paint.Style.FILL
        timeUnitPaint?.isFakeBoldText = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制扇形
        canvas.drawArc(ovalRectF, -90f, -currentAngle, true, paint!!)
        // 绘制时长
        timePaint?.let { paint ->
            val text = "$currentTime"
            val unitText = "s"
            val textWidth = paint.measureText(text)
            val unitTextWidth = timeUnitPaint?.measureText(unitText) ?: 0f
            val textX = (width - textWidth - unitTextWidth) / 2f
            val textY = (height / 2 + abs((paint.ascent() + paint.descent()).toDouble()) / 2).toFloat()
            canvas.drawText(text, textX, textY, paint)
            // 绘制时长单位
            timeUnitPaint?.let { unitPaint ->
                canvas.drawText(unitText, textX + textWidth, textY, unitPaint)
            }
        }
    }

    fun startAnimation(duration: Long) {
        //扇形旋转动画
        val totalAngle = 360f
        val animator = ValueAnimator.ofFloat(totalAngle, 0f)
        animator.setDuration(duration) // 动画持续时间
        animator.repeatCount = 0
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            currentAngle = animatedValue
            currentTime = ((animatedValue / totalAngle) * duration.toDuration(DurationUnit.MILLISECONDS).inWholeSeconds).toLong()
            invalidate() // 重绘View
        }
        animator.start()
    }

    fun setTimeTextSize(textSize: Float): ConnectingView {
        timePaint?.textSize = textSize
        return this
    }

    fun setTimeUnitTextSize(textSize: Float): ConnectingView {
        timeUnitPaint?.textSize = textSize
        return this
    }
}