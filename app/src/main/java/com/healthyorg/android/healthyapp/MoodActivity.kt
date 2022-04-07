package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MoodActivity : AppCompatActivity() {

    private lateinit var happyButton: Button
    private lateinit var mehButton: Button
    private lateinit var badButton: Button
    private lateinit var reactionText: TextView

    var reactionArray: Array<String> = arrayOf("That's great to hear! Keep it up!", "Understandable! Hang in there!",
    "That's too bad. Things will get better soon! Maybe a motivational quote will help?")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)

        happyButton = findViewById(R.id.mood_happy_button)
        mehButton = findViewById(R.id.mood_meh_button)
        badButton = findViewById(R.id.mood_bad_button)
        reactionText = findViewById(R.id.mood_reaction_text)

        happyButton.setOnClickListener {
            reactionText.setText(reactionArray[0])
        }

        mehButton.setOnClickListener {
            reactionText.setText(reactionArray[1])
        }

        badButton.setOnClickListener {
            reactionText.setText(reactionArray[2])
        }
    }
}