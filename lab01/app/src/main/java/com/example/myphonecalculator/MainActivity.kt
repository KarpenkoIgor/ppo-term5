package com.example.myphonecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

    private var isOnStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputLine:TextView = findViewById(R.id.math_operation)
        inputLine.setText("0")
        val btnCM:TextView = findViewById(R.id.btn_change)
        btnCM.setOnClickListener {changeMod()}
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
        btnDiv.setOnClickListener {handler("÷")}

    }

    private fun handler(str:String){
        val inputLine:TextView = findViewById(R.id.math_operation)

        if(str.isDigitsOnly()) {
            var last = '+'
            for(dig in inputLine.text.reversed()){
                if(!dig.isDigit()){
                    if(dig == '.') last = dig
                    break
                }
                last = dig
            }
            if(last == '0') inputLine.text = inputLine.text.dropLast(1)
            inputLine.append(str)
        }
        else if (str == "."){
            var last = '+'
            for(dig in inputLine.text.reversed()){
                if(!dig.isDigit()){
                    if(dig == '.') last = dig
                    break
                }
                last = dig
            }
            if(inputLine.text.last().isDigit() && last != '.') inputLine.append(str)
        }
        else{
            if(inputLine.text.last() == '.'){
                inputLine.append("0" + str)
            }
            else if (inputLine.text.last() in arrayOf('+','-','×','÷')){
                inputLine.text = inputLine.text.dropLast(1)
                inputLine.append(str)
            }
            else inputLine.append(str)
        }

        updateResultLine()
    }

    private fun percentButtonHandler(){

        updateResultLine()
    }

    private fun getPriority(sym:String):Int{
        if(sym in arrayOf("+", "-")) return 0
        if(sym in arrayOf("×","÷")) return 1
        return 0
    }

    private fun operationWithArguments(a:Double, b:Double, op:String):Double{
        if(op == "+") return a + b
        if(op == "-") return a - b
        if(op == "×") return a * b
        if(op == "÷") return a / b
        return 0.0
    }

    private fun updateResultLine(){
        val inputLine:TextView = findViewById(R.id.math_operation)
        val resultLine:TextView = findViewById(R.id.result_text)
        var tempLine:String = inputLine.text.toString()
        if (tempLine.last() in arrayOf('+', '-', '.')) tempLine += "0"
        if (tempLine.last() in arrayOf('×','÷')) tempLine += "1"
        var operStack = arrayListOf<String>()
        var outArray = arrayListOf<String>()
        var curNum = ""
        for(sym in tempLine){
            if(sym.isDigit() || sym == '.') curNum += sym
            else{
                outArray.add(curNum)
                curNum = ""
                if(!operStack.isEmpty() && getPriority(operStack.last()) >= getPriority(sym.toString())){
                    outArray.add(operStack.last())
                    operStack.removeAt(operStack.size - 1)
                }
                operStack.add(sym.toString())
            }
        }
        outArray.add(curNum)
        if(!operStack.isEmpty()){
            for(i in 1..operStack.size){
                outArray.add(operStack.last())
                operStack.removeAt(operStack.size - 1)
            }
        }
        /*
        var result = ""
        for(item in outArray) result = result + ", " + item
        resultLine.setText(result)*/

        var numResult = arrayListOf<Double>()

        for(item in outArray){
            if(item.last().isDigit()){
                numResult.add(item.toDouble())
            }
            else{
                var first = numResult.last()
                numResult.removeAt(numResult.size - 1)
                var second = numResult.last()
                numResult.removeAt(numResult.size - 1)
                numResult.add(operationWithArguments(second,first,item))
            }
        }

        var result = "= " + numResult.last().toString()

        for(sym in result.reversed()){
            if(sym == '0'){
                result = result.dropLast(1)
                continue
            }
            if(sym == '.'){
                result = result.dropLast(1)
                break
            }
            break
        }

        resultLine.setText(result)
    }

    private fun clean(){
        val inputLine:TextView = findViewById(R.id.math_operation)
        val resultLine:TextView = findViewById(R.id.result_text)
        inputLine.setText("0")
        resultLine.setText("")
    }

    private fun stepBack(){
        val inputLine:TextView = findViewById(R.id.math_operation)
        inputLine.text = inputLine.text.dropLast(1)
        if(inputLine.text.length == 0) inputLine.setText("0")
        updateResultLine()
    }

    private fun changeMod(){

    }

    private fun printResult(){

    }
}