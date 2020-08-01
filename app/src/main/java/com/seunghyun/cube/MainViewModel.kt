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
    val skin = MutableLiveData<Int>()

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
                skin.value = snapshot.value.toString().toInt()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
