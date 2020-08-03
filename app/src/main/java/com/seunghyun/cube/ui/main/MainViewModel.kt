package com.seunghyun.cube.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seunghyun.cube.model.FunctionItem
import com.seunghyun.cube.util.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val initialFunctionItems = listOf(
    FunctionItem(1, "", ""),
    FunctionItem(2, "", ""),
    FunctionItem(3, "", ""),
    FunctionItem(4, "", ""),
    FunctionItem(5, "", ""),
    FunctionItem(6, "", "")
)

private const val ROTATION_INCREASE_UPDATE_INTERVAL = 500L

class MainViewModel : ViewModel() {
    val skinNumber = MutableLiveData(1)
    val functionItems = MutableLiveData(initialFunctionItems)
    val rotationIncrease = MutableLiveData(0)

    private val db = Firebase.database
    private var angleBefore = 0
    private var rotationIncreaseSum = 0

    init {
        db.getReference("data/angle").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val angle = snapshot.value.toString().toInt()
                rotationIncreaseSum += angle - angleBefore
                angleBefore = angle
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        db.getReference("data/skin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                skinNumber.value = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                rotationIncrease.postValue(rotationIncreaseSum)
                rotationIncreaseSum = 0
                delay(ROTATION_INCREASE_UPDATE_INTERVAL)
            }
        }
    }
}
