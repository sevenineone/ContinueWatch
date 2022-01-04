package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(SECONDS, secondsElapsed)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        secondsElapsed = savedInstanceState.getInt(SECONDS)
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        const val SECONDS = "Seconds elapsed"
    }

}