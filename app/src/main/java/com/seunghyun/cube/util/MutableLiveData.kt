package com.seunghyun.cube.util

import androidx.lifecycle.MutableLiveData

class MutableLiveData<T>(initialValue: T? = null) : MutableLiveData<T>() {
    init {
        value = initialValue
    }
}
