package com.seunghyun.cube

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

private const val CHANGE_INTERVAL = 10

class MainActivity : AppCompatActivity() {
    private val audio by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    private val dataBase = Firebase.database

    private var previousAngle = 0
    private var currentSkin = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataBase.getReference("angle").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val angle = snapshot.value.toString().toInt()
                angleChanged(angle)
                angleText.text = "angle : $angle"
            }
        })

        dataBase.getReference("skin").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                previousAngle = 0
                currentSkin = snapshot.value.toString().toInt()
                skinText.text = "skin : $currentSkin"
            }
        })
    }

    private fun angleChanged(angle: Int) {
        val angleChanged = angle - previousAngle
        Log.d("testing", "angle $angle, changed: $angleChanged")

        if (currentSkin == 1) {
            val brightness = getBrightness()
            if (angleChanged >= CHANGE_INTERVAL) setBrightness(brightness + 50)
            else if (angleChanged <= CHANGE_INTERVAL * -1) setBrightness(brightness - 50)

        } else if (currentSkin == 2) {
            if (angleChanged >= CHANGE_INTERVAL) increaseVolume()
            else if (angleChanged <= CHANGE_INTERVAL * -1) decreaseVolume()
        }

        previousAngle = angle
    }

    private fun increaseVolume() {
        audio.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_RAISE,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun decreaseVolume() {
        audio.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_LOWER,
            AudioManager.FLAG_SHOW_UI
        )
    }

    private fun getBrightness() =
        Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)

    private fun setBrightness(value: Int) {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, value)
        val layoutParams = window.attributes
        layoutParams.screenBrightness = value / 255f
        window.attributes = layoutParams
    }
}
