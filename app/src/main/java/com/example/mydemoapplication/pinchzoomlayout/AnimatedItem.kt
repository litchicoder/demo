package com.example.mydemoapplication.pinchzoomlayout

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo

class AnimatedItem(val viewHolder: RecyclerView.ViewHolder?, val preRect: Rect?, val postRect: Rect?, val type: Type?) {
    enum class Type {
        DISAPPEARANCE, APPEARANCE, PERSISTENCE, CHANGE
    }


    class Builder {
        private var viewHolder: RecyclerView.ViewHolder? = null

        private var preRect: Rect? = null
        private var postRect: Rect? = null

        private var type: Type? = null

        fun setViewHolder(viewHolder: RecyclerView.ViewHolder?): Builder {
            this.viewHolder = viewHolder
            return this
        }

        fun setPreRect(preLayoutInfo: ItemHolderInfo?): Builder {
            if (preLayoutInfo == null) {
                preRect = Rect()
            } else {
                this.preRect = Rect(
                    preLayoutInfo.left, preLayoutInfo.top,
                    preLayoutInfo.right, preLayoutInfo.bottom
                )
            }
            return this
        }

        fun setPostRect(postLayoutInfo: ItemHolderInfo?): Builder {
            if (postLayoutInfo == null) {
                postRect = Rect()
            } else {
                this.postRect = Rect(
                    postLayoutInfo.left, postLayoutInfo.top,
                    postLayoutInfo.right, postLayoutInfo.bottom
                )
            }
            return this
        }

        fun setType(type: Type?): Builder {
            this.type = type
            return this
        }

        fun build(): AnimatedItem {
            return AnimatedItem(viewHolder, preRect, postRect, type)
        }
    }
}
