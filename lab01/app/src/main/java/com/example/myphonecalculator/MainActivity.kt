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
        inputLine.text = "0"
        /*
        val inputLine:TextView = findViewById(R.id.math_operation)
        inputLine.setText("0")
        val btnCM:TextView = findViewById(R.id.btn_change)
        btnCM.setOnClickListener {changeModToScience()}
        val btn0:TextView = findViewById(R.id.btn_0)
        btn0.setOnClickListener {handler("0")}
        val btnDot:TextView = findViewById(R.id.btn_dot)
        btnDot.setOnClickListener {handler(".")}
        val btnEqual:TextView = findViewById(R.id.btn_eq)
        btnEqual.setOnClickListener {printResult()}
        val btn1:TextView = findViewById(R.id.btn_1)
        btn1.setOnClickListener {handler("1")}
        val btn2:TextView = findViewById(R.id.btn_2)
        btn2.setOnClickListener {handler("2")}
        val btn3:TextView = findViewById(R.id.btn_3)
        btn3.setOnClickListener {handler("3")}
        val btnAdd:TextView = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {handler("+")}
        val btn4:TextView = findViewById(R.id.btn_4)
        btn4.setOnClickListener {handler("4")}
        val btn5:TextView = findViewById(R.id.btn_5)
        btn5.setOnClickListener {handler("5")}
        val btn6:TextView = findViewById(R.id.btn_6)
        btn6.setOnClickListener {handler("6")}
        val btnSub:TextView = findViewById(R.id.btn_sub)
        btnSub.setOnClickListener {handler("-")}
        val btn7:TextView = findViewById(R.id.btn_7)
        btn7.setOnClickListener {handler("7")}
        val btn8:TextView = findViewById(R.id.btn_8)
        btn8.setOnClickListener {handler("8")}
        val btn9:TextView = findViewById(R.id.btn_9)
        btn9.setOnClickListener {handler("9")}
        val btnMul:TextView = findViewById(R.id.btn_mul)
        btnMul.setOnClickListener {handler("×")}
        val btnAC:TextView = findViewById(R.id.btn_AC)
        btnAC.setOnClickListener {clean()}
        val btnBack:TextView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {stepBack()}
        val btnProc:TextView = findViewById(R.id.btn_proc)
        btnProc.setOnClickListener {percentButtonHandler()}
        val btnDiv:TextView = findViewById(R.id.btn_div)
        btnDiv.setOnClickListener {handler("÷")}*/

    }

}