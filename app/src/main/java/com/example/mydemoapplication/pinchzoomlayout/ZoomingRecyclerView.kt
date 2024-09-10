package com.example.mydemoapplication.pinchzoomlayout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import androidx.core.view.ScaleGestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class ZoomingRecyclerView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(
    context!!, attrs, defStyle
) {
    private val scaleGestureDetector: ScaleGestureDetector
    private var onScaleGestureListener: OnScaleGestureListener? = null

    init {
        scaleGestureDetector = ScaleGestureDetector(context!!, object : OnScaleGestureListener {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                if (isScaleListenerSet) {
                    return onScaleGestureListener!!.onScale(detector)
                }
                return false
            }

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                if (isScaleListenerSet) {
                    return onScaleGestureListener!!.onScaleBegin(detector)
                }
                return false
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
                if (isScaleListenerSet) {
                    onScaleGestureListener!!.onScaleEnd(detector)
                }
            }

            private val isScaleListenerSet: Boolean
                get() = onScaleGestureListener != null
        })
        ScaleGestureDetectorCompat.setQuickScaleEnabled(scaleGestureDetector, false)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val ia = itemAnimator
        var handled = scaleGestureDetector.onTouchEvent(ev)
        if (ia == null || !ia.isRunning) {
            handled = handled or super.dispatchTouchEvent(ev)
        }
        return handled
    }

    override fun setItemAnimator(animator: ItemAnimator?) {
        super.setItemAnimator(animator)
    }

    fun setOnScaleGestureListener(onScaleGestureListener: OnScaleGestureListener?) {
        this.onScaleGestureListener = onScaleGestureListener
    }
}
