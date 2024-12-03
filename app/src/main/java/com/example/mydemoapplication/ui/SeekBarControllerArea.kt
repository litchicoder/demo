package com.example.mydemoapplication.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.ProgressBar
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import kotlin.math.abs

class SeekBarControllerArea(context: Context?, attrs: AttributeSet?) : AppCompatSeekBar(
    context!!, attrs
) {
    private val paint: Paint
    var mProgress = 0
    private val maxProgress = 100
    private val density: Float
    private val downX // 记录按下时的X坐标
            = 0f
    private var lastValue =0
    var mChangeListener: OnSeekBarChangeListener? = null

    init {
        density = resources.displayMetrics.density
        paint = Paint()
        paint.isAntiAlias = true
        setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            var startProgress = progress
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                if (lastValue == 0) {
//                    lastValue = seekBar?.progress?:0
//                } else {
//                    val v = (seekBar?.progress ?: 0) - lastValue
//                    mProgress += v
//                }
                if (abs(progress - startProgress) > 1) {
                    startProgress = progress
                } else {
                    lastValue = progress
                    mChangeListener?.onProgressChanged(progress)
                }
                Log.d("litchi","onProgressChanged progress:$progress mProgress:$mProgress fromUser:$fromUser")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.d("litchi","onStartTrackingTouch progress:${seekBar?.progress}")
                startProgress = seekBar?.progress?:0
                mChangeListener?.onStartTrackingTouch()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.d("litchi","onStopTrackingTouch progress:${seekBar?.progress}")
                lastValue = 0
                mChangeListener?.onStopTrackingTouch()
            }

        })
    }

    override fun onDraw(canvas: Canvas) {}

    interface OnSeekBarChangeListener {

        fun onProgressChanged(progress: Int)

        fun onStartTrackingTouch()

        fun onStopTrackingTouch()
    }
}