package com.healthyorg.android.healthyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class QuotesActivity : AppCompatActivity() {

    private lateinit var newQuoteButton: Button
    private lateinit var quoteTextView: TextView

    //An array to hold all of the quotes. It ain't pretty, but it works :)
    var quotes: Array<String> = arrayOf("You can do this!", "Take a moment to relax", "Take a deep breath",
    "Everything is going to be fine", "You've got this!", "Nice work today!", "Don't stop until you're proud",
    "Every day may not be good, but there is something good in every day", "You tried :)",
    "Develop success from failures. Discouragement and failure are two of the surest stepping stones to success",
    "Don't let yesterday take up too much of today!", "Create your own opportunities!",
    "Start your day with positive thoughts!", "Don't settle for average!", "There's no need to hurry",
    "Hang in there!", "The greater the difficulty, the greater the glory in overcoming it",
    "Never settle for second best!", "Remember to take care of yourself!")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        //Linking the textview that contains the quote text
        //and the button that displays a new one with their xml counterparts
        newQuoteButton = findViewById(R.id.new_quote_button)
        quoteTextView = findViewById(R.id.quote_text_view)

        //Randomly select and display a quote from the array. Very complicated stuff here!
        newQuoteButton.setOnClickListener {
            val rando = (0..18).random()
            val currentQuote = quotes[rando]
            quoteTextView.setText(currentQuote)
        }


    }
}