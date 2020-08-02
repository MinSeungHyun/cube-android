package com.seunghyun.cube.model

import java.io.Serializable

data class FunctionItem(val skinNumber: Int, val device: String, val function: String) : Serializable {
    override fun toString() = if (device.isNotBlank() && function.isNotBlank()) "$device $function"
    else "기능을 선택해주세요."

    fun toStringIgnoreEmpty() = "$device $function"
}
