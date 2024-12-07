package com.example.mydemoapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar

class SeekBarControllerArea @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var seekBar: SeekBar? = null
    private var lastTouchX: Float = 0f
    private var progressChangeSensitivity: Float = 0.3f // 灵敏度系数，根据需要调整

    init {
        // 可以在这里进行初始化操作，如果需要的话
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val currentX = event.x
                if (lastTouchX != 0f) {
                    val deltaX = currentX - lastTouchX
                    // 更新 SeekBar 的进度
                    seekBar?.let { bar ->
                        val diff = ((deltaX * progressChangeSensitivity).toInt())
                        bar.incrementProgressBy(diff)
                    }
                }

                lastTouchX = currentX
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                lastTouchX = 0f
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setSeekBar(seekBar: SeekBar) {
        this.seekBar = seekBar
    }
}

