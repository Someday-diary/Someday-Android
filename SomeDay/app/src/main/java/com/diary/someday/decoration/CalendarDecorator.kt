package com.diary.someday.decoration

import android.app.Activity
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.diary.someday.R

class CalendarDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable = context?.getDrawable(R.drawable.exist_diary_decorator)!!
    private val myDay = currentDay

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(drawable)
    }

}