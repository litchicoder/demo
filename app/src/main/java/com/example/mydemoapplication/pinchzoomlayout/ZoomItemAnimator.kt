package com.example.mydemoapplication.pinchzoomlayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.example.mydemoapplication.pinchzoomlayout.scale.Scale
import com.example.mydemoapplication.pinchzoomlayout.scale.ScaleManager

class ZoomItemAnimator : ItemAnimator(), OnScaleGestureListener {
    private var layoutManager: GridLayoutManager? = null
    private var recyclerView: ZoomingRecyclerView? = null
    private var isRunning = false
    private val animatedSet = ArrayList<AnimatedItem>()
    private var animator: AnimatorSet? = null
    private var scale: Scale? = null

    private val scaleHandler = ScaleManager { scale ->
        if (this@ZoomItemAnimator.scale != null) {
            animateItems()
        } else {
            setScale(scale)
        }
    }

    private fun setScale(scale: Scale) {
        this.scale = scale
        when (scale.type) {
            Scale.TYPE_SCALE_DOWN -> {
                incrementSpanCount()
            }

            Scale.TYPE_SCALE_UP -> {
                decrementSpanCount()
            }
        }
    }

    fun setup(recyclerView: ZoomingRecyclerView) {
        this.recyclerView = recyclerView
        this.layoutManager = recyclerView.layoutManager as GridLayoutManager?
        this.recyclerView!!.setOnScaleGestureListener(this)
        this.recyclerView!!.itemAnimator = this
    }

    override fun animateDisappearance(viewHolder: RecyclerView.ViewHolder, preLayoutInfo: ItemHolderInfo, postLayoutInfo: ItemHolderInfo?): Boolean {
        val ai = AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.DISAPPEARANCE)
            .build()
        animatedSet.add(ai)
        return false
    }

    override fun animateAppearance(viewHolder: RecyclerView.ViewHolder, preLayoutInfo: ItemHolderInfo?, postLayoutInfo: ItemHolderInfo): Boolean {
        val ai = AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.APPEARANCE)
            .build()
        animatedSet.add(ai)
        return false
    }

    override fun animatePersistence(viewHolder: RecyclerView.ViewHolder, preLayoutInfo: ItemHolderInfo, postLayoutInfo: ItemHolderInfo): Boolean {
        val ai = AnimatedItem.Builder()
            .setViewHolder(viewHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.PERSISTENCE)
            .build()
        animatedSet.add(ai)
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        val ai = AnimatedItem.Builder()
            .setViewHolder(newHolder)
            .setPreRect(preLayoutInfo)
            .setPostRect(postLayoutInfo)
            .setType(AnimatedItem.Type.CHANGE)
            .build()
        animatedSet.add(ai)
        return false
    }

    override fun runPendingAnimations() {
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
    }

    override fun endAnimations() {
    }

    override fun isRunning(): Boolean {
        return isRunning || (animator != null && animator!!.isRunning)
    }

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        onScale(detector.scaleFactor)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        animatedSet.clear()
        scaleHandler.reset()
        onScale(detector.scaleFactor)
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        isRunning = false
        finishAnimation(ArrayList(animatedSet))
        scale = null
        animatedSet.clear()
        scaleHandler.reset()
    }

    private fun finishAnimation(items: ArrayList<AnimatedItem>) {
        animator = AnimatorSet()
        val duration = (500 * (1.0f - scale!!.scale)).toLong()
        animator!!.setDuration(duration)
        animator!!.interpolator = DecelerateInterpolator()
        animator!!.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isRunning = false
                this@ZoomItemAnimator.animator = null
            }
        })

        val animators: MutableList<Animator> = ArrayList()
        for (ai in items) {
            addItemAnimators(ai, animators)
        }
        Log.d(TAG, "finishAnimation: $items.")
        animator!!.playTogether(animators)
        animator!!.start()
    }

    private fun addItemAnimators(ai: AnimatedItem, animators: MutableList<Animator>) {
        val post = ai.postRect

        val view = ai.viewHolder?.itemView
        post?.apply {
            val viewAnimator: Animator = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofInt("top", top),
                PropertyValuesHolder.ofInt("left", left),
                PropertyValuesHolder.ofInt("bottom", bottom),
                PropertyValuesHolder.ofInt("right", right)
            )
            viewAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    ai.viewHolder?.let { dispatchAnimationFinished(it) }
                }
            })
            animators.add(viewAnimator)
        }
    }

    private fun onScale(incrementalScaleFactor: Float) {
        scaleHandler.onScale(incrementalScaleFactor)
    }

    private fun decrementSpanCount() {
        var spanCount = layoutManager!!.spanCount
        if (spanCount > 1) {
            layoutManager!!.spanCount = --spanCount
            notifyDataSetChanged()
            isRunning = true
        } else {
            scaleHandler.reset()
        }
    }

    private fun incrementSpanCount() {
        var spanCount = layoutManager!!.spanCount
        layoutManager!!.spanCount = ++spanCount
        notifyDataSetChanged()
        isRunning = true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyDataSetChanged() {
        recyclerView!!.adapter!!.notifyDataSetChanged()
    }

    private fun animateItems() {
        for (h in animatedSet) {
            scaleItem(h)
        }
    }

    private fun scaleItem(h: AnimatedItem) {
        val itemVIew = h.viewHolder?.itemView
        val preRect = h.preRect ?: Rect()
        val postRect = h.postRect ?: Rect()
        val top = preRect.top + (scale!!.scale * (postRect.top - preRect.top)).toInt()
        val left = preRect.left + (scale!!.scale * (postRect.left - preRect.left)).toInt()
        val bottom = preRect.bottom + (scale!!.scale * (postRect.bottom - preRect.bottom)).toInt()
        val right = preRect.right + (scale!!.scale * (postRect.right - preRect.right)).toInt()
        itemVIew?.top = top
        itemVIew?.left = left
        itemVIew?.bottom = bottom
        itemVIew?.right = right
    }

    companion object {
        private const val TAG = "ZoomItemAnimator"
    }
}
