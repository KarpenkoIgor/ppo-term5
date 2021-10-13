package com.example.myphonecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.content.res.Configuration
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private var currentAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val simpleCalculator = SimpleCalculator()
        val scientificCalculator = ScientificCalculator()
        var lastUsedFragment = SimpleCalculator()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, scientificCalculator)
            commit()
        }
        val inputLine:TextView = findViewById(R.id.math_operation)
        inputLine.text = "0"
    }

}