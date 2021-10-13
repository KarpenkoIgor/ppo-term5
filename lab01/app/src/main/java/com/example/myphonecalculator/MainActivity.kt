package com.example.myphonecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private var currentAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sipmleCalculator = SimpleCalculator()
        val scientificCalculator = ScientificCalculator()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, sipmleCalculator)
            commit()
        }
        val inputLine:TextView = findViewById(R.id.math_operation)
        val resultLine:TextView = findViewById(R.id.math_operation)
        inputLine.text = "0"
    }

}