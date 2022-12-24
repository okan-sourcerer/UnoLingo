package com.example.unolingo.utils

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class AutoFitGridLayoutManager(context: Context?, columnWidth: Int) :
    GridLayoutManager(context, 3) {
    private var columnWidth = 0
    private var columnWidthChanged = true

    init {
        Log.d("AutoFitGridLayoutManager", "width is: $columnWidth")
        setColumnWidth(columnWidth)
    }

    private fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth
            columnWidthChanged = true
        }
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (columnWidthChanged && columnWidth > 0) {
            val totalSpace: Int = if (orientation == VERTICAL) {
                width - paddingRight - paddingLeft
            } else {
                height - paddingTop - paddingBottom
            }
            val spanCount = 1.coerceAtLeast(totalSpace / columnWidth)
            setSpanCount(spanCount)
            columnWidthChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}