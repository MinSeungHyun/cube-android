package com.seunghyun.cube.ui.function

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FunctionViewModel : ViewModel() {
    val skinNumber: MutableLiveData<Int> = MutableLiveData(1)
}
