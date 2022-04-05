package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.Date

class SleepActivity : AppCompatActivity() {

    private lateinit var sleepEditText: EditText
    private lateinit var sleepEnterButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        sleepEditText = findViewById(R.id.sleep_edit_text)
        sleepEnterButton = findViewById(R.id.sleep_enter_button)

        sleepEnterButton.setOnClickListener {
            var currentDateVar = Date()
            var sleepHours = sleepEditText.getText()
        }
    }
}