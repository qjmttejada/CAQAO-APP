package com.example.caqao.fragments

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastTwoItemBottomMarginDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        // Calculate how many items are in the adapter
        val totalItemCount = parent.adapter?.itemCount ?: 0

        // Add margin to the bottom of the last two items
        if (position >= totalItemCount - 2) {
            outRect.bottom = margin
        }
    }
}
