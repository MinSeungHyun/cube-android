package com.seunghyun.cube.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.seunghyun.cube.R

class Utils {
    companion object {
        @JvmStatic
        fun skinNumberToName(numbers: Array<String>, number: Int) = numbers[number - 1]

        @JvmStatic
        @BindingAdapter("skinColor")
        fun skinNumberToColor(view: View, number: Int) {
            val context = view.context
            val colors = arrayOf(
                context.getColor(R.color.red),
                context.getColor(R.color.orange),
                context.getColor(R.color.yellow),
                context.getColor(R.color.green),
                context.getColor(R.color.blue),
                context.getColor(R.color.purple)
            )
            view.setBackgroundColor(colors[number - 1])
        }

        @JvmStatic
        @BindingAdapter("skinColorTint")
        fun skinNumberToColorTint(view: View, number: Int) {
            val context = view.context
            val colors = arrayOf(
                R.color.red,
                R.color.orange,
                R.color.yellow,
                R.color.green,
                R.color.blue,
                R.color.purple
            )
            view.backgroundTintList = context.resources.getColorStateList(colors[number - 1], null)
        }

        @JvmStatic
        fun <T> MutableLiveData<List<T>>.edit(block: MutableList<T>.() -> Unit) {
            val mutableList = this.value?.toMutableList() ?: return
            this.value = mutableList.apply(block)
        }
    }
}
