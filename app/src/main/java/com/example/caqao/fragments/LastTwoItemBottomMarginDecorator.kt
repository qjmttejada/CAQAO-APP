package com.example.caqao.fragments

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LastTwoItemBottomMarginDecorator(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
        val position = parent.getChildAdapterPosition(view)

        val spanCount = layoutManager.spanCount
        val spanSizeLookup = layoutManager.spanSizeLookup

        val itemCount = parent.adapter?.itemCount ?:0

        // Determine if the view is in the last row
        var lastRow = true
        for (i in position + 1 until itemCount) {
            if (spanSizeLookup.getSpanIndex(i, spanCount) == 0) {
                lastRow = false
                break
            }
        }

        // Apply margin to the bottom of the view if it is in the last row or the second-last row
        if (lastRow || (position == itemCount - 2 && spanSizeLookup.getSpanIndex(position, spanCount) == 0)) {
            outRect.bottom = margin
        }

//        val position = parent.getChildAdapterPosition(view)
//        val spanCount = 2
//        val itemCount = parent.adapter?.itemCount ?: 0
//
//        // Determine if the view is in the last row
//        val lastRow = position >= itemCount - (itemCount % spanCount)
//
//        // Reset bottom margin for all items except those in the last row
//        if (!lastRow) {
//            outRect.bottom = 0
//        }
//
//        // Apply margin to the bottom of the last two items in the last row
//        if (lastRow && (position == itemCount - 2 || position == itemCount - 1)) {
//            outRect.bottom = margin
//        }
    }

    fun notifyItemInserted(adapter: RecyclerView.Adapter<*>, position: Int) {
        adapter.notifyItemInserted(position)
//        val recyclerView = adapter as? RecyclerView ?: return
//        val layoutManager = recyclerView.layoutManager as? GridLayoutManager ?: return
//        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//        val lastRowFirstItemPosition = lastVisibleItemPosition - 1
//        val lastRowLastItemPosition = adapter.itemCount - 1
//        if (lastVisibleItemPosition == lastRowFirstItemPosition || lastVisibleItemPosition == lastRowLastItemPosition) {
//            recyclerView.removeItemDecoration(this)
//            recyclerView.addItemDecoration(this)
//        }
    }

//    fun notifyItemRemoved(adapter: RecyclerView.Adapter<*>, position: Int) {
//        adapter.notifyItemRemoved(position)
//        val recyclerView = adapter as? RecyclerView ?: return
//        val layoutManager = recyclerView.layoutManager as? GridLayoutManager ?: return
//        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//        val lastRowFirstItemPosition = lastVisibleItemPosition - 1
//        val lastRowLastItemPosition = adapter.itemCount - 1
//        if (lastVisibleItemPosition == lastRowFirstItemPosition || lastVisibleItemPosition == lastRowLastItemPosition) {
//            recyclerView.removeItemDecoration(this)
//            recyclerView.addItemDecoration(this)
//        } else {
//            // check if the item was in the last row and update the decoration
//            val itemCount = adapter.itemCount
//            val lastRow = itemCount % layoutManager.spanCount == 0 || position >= itemCount - (itemCount % layoutManager.spanCount)
//            if (lastRow) {
//                recyclerView.removeItemDecoration(this)
//                recyclerView.addItemDecoration(this)
//            }
//        }
//    }


}

