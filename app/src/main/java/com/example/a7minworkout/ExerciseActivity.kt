package com.example.a7minworkout

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.a7minworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

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
                val timeRest = 10
                val progressTimerValue: Int = timeRest - restProgress
                binding?.progressbar?.progress = progressTimerValue
                binding?.tvTimer?.text = progressTimerValue.toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }

        }.start()

    }

    private fun setExerciseProgressbar() {
        binding?.progressbarExercise?.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                val timeExercise = 30
                val progressTimerValue: Int = timeExercise - exerciseProgress
                binding?.progressbarExercise?.progress = progressTimerValue
                binding?.tvTimerExercise?.text = progressTimerValue.toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < (exerciseList?.size ?: 0) - 1) {
                    setUpRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulation! You have completed the 7 minutes workout.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }.start()

    }

    private fun setUpRestView() {
        binding?.flRest?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE
        binding?.flExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        stopRestTimer()

        exerciseList?.let {
            binding?.tvUpcomingExerciseName?.text = it[currentExercisePosition + 1].getName()
        }

        setRestProgressbar()
    }

    private fun stopRestTimer() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
    }

    private fun setUpExerciseView() {
        binding?.flRest?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE
        binding?.flExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        stopExerciseTimer()

        exerciseList?.let {
            binding?.ivImage?.setImageResource(it[currentExercisePosition].getImage())
            binding?.tvExercise?.text = it[currentExercisePosition].getName()
        }

        setExerciseProgressbar()
    }

    private fun stopExerciseTimer() {
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopRestTimer()
        stopExerciseTimer()
        binding = null
    }

}