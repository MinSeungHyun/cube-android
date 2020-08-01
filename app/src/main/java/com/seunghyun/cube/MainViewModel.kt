package com.seunghyun.cube

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {
    val angle = MutableLiveData<Int>()
    val skinNumber = MutableLiveData<Int>()
    val functionItems = listOf(
        FunctionItem(1, "무드등 켜기 / 끄기"),
        FunctionItem(2, "에어컨 켜기 / 끄기"),
        FunctionItem(3, "선풍기 켜기 / 끄기"),
        FunctionItem(4, "노래 재생 / 일시정지"),
        FunctionItem(5, "다음 곡"),
        FunctionItem(6, "이전 곡")
    )

    private val db = Firebase.database

    init {
        angle.value = 0
        skinNumber.value = 1
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
