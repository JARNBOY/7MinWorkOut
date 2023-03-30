package com.example.a7minworkout

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.a7minworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setUpRestView()

    }

    private fun setRestProgressbar() {
        binding?.progressbar?.progress = restProgress
        restTimer = object: CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                val progressTimerValue: Int = 10 - restProgress
                binding?.progressbar?.progress = progressTimerValue
                binding?.tvTimer?.text = progressTimerValue.toString()
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Here now we will start the exercise.",
                     Toast.LENGTH_LONG
                ).show()
            }

        }.start()

    }

    private fun setUpRestView() {
        stopTimer()
        setRestProgressbar()
    }

    private fun stopTimer() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopTimer()
        binding = null
    }

}