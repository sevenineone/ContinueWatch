package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

class MainActivity2 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPrefs: SharedPreferences

    private var backgroundThread = Thread {
        while (true) {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.text, secondsElapsed++)
                }
                Thread.sleep(1000)
            }
        }
    }

    init {
        backgroundThread.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getPreferences(Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onResume() {
        super.onResume()
        secondsElapsed = sharedPrefs.getInt(SECONDS, 0)
    }

    override fun onStop() {
        super.onStop()
        with(sharedPrefs.edit()) {
            putInt(SECONDS, secondsElapsed)
            apply()
        }
    }

    companion object {
        const val SECONDS = "Seconds elapsed"
    }

}