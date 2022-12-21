package com.example.unolingo.utils

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.recyclerview.widget.RecyclerView


object Animations {
    fun expand(view: RecyclerView) {
        val animation = expandAction(view)
        view.startAnimation(animation)
    }

    private fun expandAction(view: RecyclerView): Animation {
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val actualheight: Int = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
                    else (actualheight * interpolatedTime).toInt()
                view.requestLayout()
            }
        }
        animation.duration =
            (actualheight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
        return animation
    }

    fun collapse(view: RecyclerView) {
        val actualHeight: Int = view.measuredHeight
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        actualHeight - (actualHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }
        }
        animation.duration =
            (actualHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }
}