package com.healthyorg.android.healthyapp.focusTimer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healthyorg.android.healthyapp.R
import kotlinx.android.synthetic.main.activity_focus.*

private const val TAG = "FocusActivity"

class FocusActivity: AppCompatActivity() {
        lateinit var countDownTimer: CountDownTimer
        lateinit var startButton: Button
        lateinit var resetButton: Button
        lateinit var timeText: EditText
        lateinit var timeSetButton: Button
    var start = 600_000L
    var timer = start

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_focus)
//      yhe first value
            setTextTimer()
            startButton = findViewById(R.id.btn_main_start)
            resetButton = findViewById(R.id.btn_main_rest)
            timeSetButton = findViewById(R.id.add_time_button)
            timeText = findViewById(R.id.time)
            timeSetButton.setOnClickListener {
                start = (((timeText.text.toString().toDouble())*60)*1000).toLong()
                countDownTimer = object : CountDownTimer(timer,1000){
                    //            end of timer
                    override fun onFinish() {
                        Toast.makeText(this@FocusActivity,"end timer",Toast.LENGTH_SHORT).show()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        timer = millisUntilFinished
                        setTextTimer()
                    }

                }.start()
                restTimer()
            }
            startButton.setOnClickListener {
                startTimer()
            }
            resetButton.setOnClickListener {
                restTimer()
            }
        }


        //    btn start
        private fun startTimer() {
            startButton.isEnabled = false
            countDownTimer = object : CountDownTimer(timer,1000){
                //            end of timer
                override fun onFinish() {
                    Toast.makeText(this@FocusActivity,"end timer",Toast.LENGTH_SHORT).show()
                }

                override fun onTick(millisUntilFinished: Long) {
                    timer = millisUntilFinished
                    setTextTimer()
                }

            }.start()
        }


        //    btn restart
        private fun restTimer() {
            startButton.isEnabled = true
            timer = start
            setTextTimer()
            countDownTimer.cancel()
        }

        //  timer format
        fun setTextTimer() {
            var m = (timer / 1000) / 60
            var s = (timer / 1000) % 60

            var format = String.format("%02d:%02d", m, s)

            tv_main_timer.setText(format)
        }
    }
