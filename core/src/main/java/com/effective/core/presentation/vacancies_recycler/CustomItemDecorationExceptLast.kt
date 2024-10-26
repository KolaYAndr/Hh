package com.effective.core.presentation.vacancies_recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecorationExceptLast(
    private val horizontal: Int = 0,
    private val vertical: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) top = vertical

            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) return
            right = horizontal
        }
    }
}
