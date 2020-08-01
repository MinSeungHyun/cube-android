package com.seunghyun.cube.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.seunghyun.cube.model.FunctionItem
import com.seunghyun.cube.util.MutableLiveData

private val initialFunctionItems = listOf(
    FunctionItem(1, "", ""),
    FunctionItem(2, "", ""),
    FunctionItem(3, "", ""),
    FunctionItem(4, "", ""),
    FunctionItem(5, "", ""),
    FunctionItem(6, "", "")
)

class MainViewModel : ViewModel() {
    val angle = MutableLiveData(0)
    val skinNumber = MutableLiveData(1)
    val functionItems = MutableLiveData(initialFunctionItems)
    private val db = Firebase.database

    init {
        db.getReference("angle").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                angle.value = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        db.getReference("skin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                skinNumber.value = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
