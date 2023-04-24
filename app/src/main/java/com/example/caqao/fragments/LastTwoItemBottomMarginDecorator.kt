package com.example.caqao.fragments

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastTwoItemBottomMarginDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = 2
        val itemCount = state.itemCount

        // Determine if the view is in the last row
        val lastRow = position >= itemCount - (itemCount % spanCount)
        val lastTwoRows = position >= itemCount - spanCount * 2 && position < itemCount - spanCount

        // Apply margin to the bottom of the view if it is in the last row
        if (lastRow || lastTwoRows) {
            outRect.bottom = margin
        }
    }
}
