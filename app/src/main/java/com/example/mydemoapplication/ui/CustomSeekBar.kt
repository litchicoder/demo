package com.example.mydemoapplication.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CustomSeekBar(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    init {
        isClickable = false
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFC0C0C0.toInt() // 灰色
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(4).toFloat()
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFCDDC39.toInt() // 绿色
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(4).toFloat()
    }

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF607D8B.toInt() // 蓝色
    }

    private val progressRect = RectF()
    private val thumbRadius = dpToPx(10).toFloat()
    private var progress = 0f
    private var maxProgress = 100f
    private var isDragging = false

    init {
//        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar, 0, 0)
        try {
//            maxProgress = typedArray.getFloat(R.styleable.CustomSeekBar_maxProgress, maxProgress)
        } finally {
//            typedArray.recycle()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                progress = (x - 0) / progressRect.width()
                progress = Math.max(0f, Math.min(progress, 1f))
                isDragging = true
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制SeekBar的轨道
        canvas.drawRect(progressRect, paint)
        // 绘制SeekBar的进度
        canvas.drawRect(progressRect.left, progressRect.top, progressRect.left + progress * progressRect.width(), progressRect.bottom, progressPaint)
        // 绘制滑块
        canvas.drawCircle(progressRect.left + progress * progressRect.width(), height / 2f, thumbRadius, thumbPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = dpToPx(40)
        setMeasuredDimension(width, height)
        progressRect.set(thumbRadius, height / 2f - thumbRadius, width - thumbRadius, height / 2f + thumbRadius)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = Math.max(0f, Math.min(progress, 1f))
        invalidate()
    }

    fun getMaxProgress(): Float {
        return maxProgress
    }

    fun setMaxProgress(maxProgress: Float) {
        this.maxProgress = maxProgress
    }
}
