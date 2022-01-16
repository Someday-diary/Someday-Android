package com.diary.someday.view.decoration

import android.app.Activity
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.diary.someday.R
import com.diary.someday.di.application.Application

class CalendarDecorator(context: Activity?, currentDay: CalendarDay) : DayViewDecorator {
    private val drawableGreen: Drawable = context?.getDrawable(R.drawable.select_circle_green)!!
    private val drawableBlue: Drawable = context?.getDrawable(R.drawable.select_circle_blue)!!
    private val drawablePurple: Drawable = context?.getDrawable(R.drawable.select_circle_purple)!!
    private val drawableYellow: Drawable = context?.getDrawable(R.drawable.select_circle_yellow)!!
    private val drawableRed: Drawable = context?.getDrawable(R.drawable.select_circle_red)!!
    private val myDay = currentDay

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        when (Application.themeSettingColor.getThemeTypeColor()) {
            1 -> {
                view.setBackgroundDrawable(drawableGreen)
            }
            2 -> {
                view.setBackgroundDrawable(drawableBlue)
            }
            3 -> {
                view.setBackgroundDrawable(drawablePurple)
            }
            4 -> {
                view.setBackgroundDrawable(drawableYellow)
            }
            5 -> {
                view.setBackgroundDrawable(drawableRed)
            }
        }
    }

}