package com.example.mydemoapplication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.annotation.IntDef
import kotlin.math.abs

/**
 * `SeekBarControllerArea` 类继承自 `View`，主要用于统一控制一组 `SeekBar` 的进度变化，通过触摸操作来实现。
 * 它可以处理单指触摸事件，根据触摸点在水平/垂直方向的移动来相应地调整所管理的 `SeekBar` 的进度，同时禁止双指拖动操作。
 */
class SeekBarControllerArea @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    @IntDef(ControlOrientation.HORIZONTAL, ControlOrientation.VERTICAL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ControlOrientation {
        companion object {
            const val HORIZONTAL = LinearLayout.HORIZONTAL
            const val VERTICAL = LinearLayout.VERTICAL
        }
    }

    //滑动方向
    private var orientation = ControlOrientation.HORIZONTAL

    // 存储一组SeekBar，用于统一控制它们的进度
    private var seekBarList: Array<out SeekBar> = arrayOf()

    // 记录上一次触摸的X坐标，用于计算触摸点的位移
    private var lastTouchX: Float = 0f
    // 记录上一次触摸的Y坐标，用于计算触摸点的位移
    private var lastTouchY: Float = 0f
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var downTime: Long= 0
    // 进度变化的灵敏度系数，通过调整该值可以改变SeekBar进度随触摸移动变化的敏感度，根据实际需要进行调整
    private var progressChangeSensitivity: Float = 0.2f

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount > 1) {
            return super.onInterceptTouchEvent(event)
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                startX = event.x
                startY = event.y
                return if (isDragEvent(lastTouchX, event.x)) {
                    true
                } else {
                    super.onInterceptTouchEvent(event)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                return if (isDragEvent(lastTouchX, event.x)) {
                    true
                } else {
                    super.onInterceptTouchEvent(event)
                }
            }
        }

        lastTouchX = event.x
        lastTouchY = event.y
        return super.onInterceptTouchEvent(event)
    }

    /**
     * 重写onTouchEvent方法，用于处理触摸事件
     * @param event 触摸事件对象，包含了触摸的各种信息，如触摸点坐标、触摸动作类型等
     * @return 返回值表示是否消费了该触摸事件，返回true表示消费，false表示不消费，未处理的事件会继续向上传递给父视图
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 如果触摸点数量大于1，即双指或多指触摸，直接返回false，表示不处理该触摸事件，禁止双指拖动相关操作
        if (event.pointerCount > 1) {
            return false
        }

        when (event.action) {
            // 当触摸动作为ACTION_DOWN（按下）或者ACTION_MOVE（移动）时
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // 获取当前触摸点的X/Y坐标
                val currentX = event.x
                val currentY = event.y
                if (orientation == ControlOrientation.HORIZONTAL) {
                    // 如果上一次触摸的X坐标不为0，说明不是首次触摸，开始计算触摸点的位移以及对应的SeekBar进度变化
                    if (lastTouchX != 0f) {
                        // 计算本次触摸点与上一次触摸点在X轴方向的位移差值
                        val deltaX = currentX - lastTouchX
                        // 根据位移差值和灵敏度系数，计算出SeekBar进度应该变化的值，这里乘以灵敏度系数可以调整进度变化的幅度
                        val diff = ((deltaX * progressChangeSensitivity))
                        // 调用updateSeekBar方法，根据计算出的进度差值来更新所有SeekBar的进度
                        updateSeekBar(Math.round(diff))
                        // 尝试声明当前的拖动操作，请求父视图不要拦截后续的触摸事件，确保能完整处理拖动相关的触摸事件
                        attemptClaimDrag()
                    }
                } else if (orientation == ControlOrientation.VERTICAL) {
                    // 如果上一次触摸的Y坐标不为0，说明不是首次触摸，开始计算触摸点的位移以及对应的SeekBar进度变化
                    if (lastTouchY != 0f) {
                        // 计算本次触摸点与上一次触摸点在X轴方向的位移差值
                        val deltaY = currentY - lastTouchY
                        // 根据位移差值和灵敏度系数，计算出SeekBar进度应该变化的值，这里乘以灵敏度系数可以调整进度变化的幅度
                        val diff = ((deltaY * progressChangeSensitivity))
                        // 调用updateSeekBar方法，根据计算出的进度差值来更新所有SeekBar的进度
                        updateSeekBar(Math.round(diff))
                        // 尝试声明当前的拖动操作，请求父视图不要拦截后续的触摸事件，确保能完整处理拖动相关的触摸事件
                        attemptClaimDrag()
                    }
                }

                // 更新上一次触摸的X坐标为当前触摸的X/Y坐标，为下一次触摸事件处理做准备
                lastTouchX = currentX
                lastTouchY = currentY
                // 返回true，表示已经消费了该触摸事件，不再向上传递
                return true
            }

            // 当触摸动作为ACTION_UP（抬起）或者ACTION_CANCEL（触摸事件取消，例如被父视图拦截等情况）时
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 将上一次触摸的X坐标重置为0，准备下一次触摸操作
                lastTouchX = 0f
                // 返回true，表示已经消费了该触摸事件，不再向上传递
                return true
            }
        }

        // 如果以上触摸动作都未匹配，调用父类的onTouchEvent方法来处理触摸事件，按照默认的视图触摸处理逻辑进行
        return super.onTouchEvent(event)
    }

    private fun isDragEvent(lastTouchPos: Float, currentPos: Float): Boolean {
        val delta = currentPos - lastTouchPos
        val upTime = System.currentTimeMillis()
        val moveDuration = upTime - downTime
        return abs(delta) > CLICK_THRESHOLD && moveDuration > ViewConfiguration.getTapTimeout()
    }

    /**
     * 更新SeekBar的进度的私有方法
     * @param diff SeekBar进度需要变化的值，经过四舍五入后传递进来，用于统一调整所有SeekBar的进度
     */
    private fun updateSeekBar(diff: Int) {
        // 遍历存储的所有SeekBar，对每个SeekBar调用incrementProgressBy方法，按照传入的diff值来增加进度
        seekBarList.forEach {
            it.incrementProgressBy(diff)
        }
    }

    /**
     * 尝试声明用户的拖动操作，并请求禁止任何祖先视图拦截触摸事件
     * 这样可以确保在拖动过程中，该视图能够完整地接收到触摸事件，不会被父视图等拦截而导致拖动操作不流畅或中断
     */
    private fun attemptClaimDrag() {
        parent?.requestDisallowInterceptTouchEvent(true);
    }

    /**
     * 设置需要控制的SeekBar的方法，可以传入多个SeekBar作为可变参数
     * @param seekBar 可变参数形式的SeekBar对象，用于指定要被该控制器管理进度的SeekBar
     */
    fun setSeekBar(vararg seekBar: SeekBar) {
        this.seekBarList = seekBar
    }

//    /**
//     * 设置进度滑动方向
//     */
//    fun setOrientation(@ControlOrientation orientation: Int) {
//        this.orientation = orientation
//    }

    companion object {
        private const val CLICK_THRESHOLD = 0.2f
    }
}

