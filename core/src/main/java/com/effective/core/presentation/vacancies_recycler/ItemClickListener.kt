package com.effective.core.presentation.vacancies_recycler

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ItemClickListener(
    context: Context,
    private val rv: RecyclerView,
    private val onClick: (Int) -> Unit
) : RecyclerView.SimpleOnItemTouchListener() {

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = rv.getChildAdapterPosition(child)
                    if (position != RecyclerView.NO_POSITION) {
                        onClick(position)
                    }
                }

                return true
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        return false
    }
}
