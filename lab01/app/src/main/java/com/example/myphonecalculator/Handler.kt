package com.example.myphonecalculator

import android.view.View
import android.widget.TextView
import androidx.core.text.isDigitsOnly


object Handler {
    private var currentAnswer = ""

    public fun handler(v: View?, str:String){
        minTextSize(v)
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        if(this.currentAnswer != "") {
            if(str.last().isDigit()) inputLine.text = "0"
            else inputLine.text = this.currentAnswer
            this.currentAnswer = ""
        }
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
        updateResultLine(v)
    }

    fun percentButtonHandler(v: View?){
        updateResultLine(v)
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

    private fun updateResultLine(v: View?){
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        val resultLine: TextView = v.findViewById(R.id.result_text)
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

        //Leave only 6 digits after dot
        val finalAnswer = String.format("%.6f", numResult.last()).toDouble()
        var result = "= " + finalAnswer.toString()

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
        resultLine.text = result
    }

    fun clean(v: View?){
        minTextSize(v)
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        val resultLine: TextView = v.findViewById(R.id.result_text)
        inputLine.text = "0"
        resultLine.text = ""
        this.currentAnswer = ""
    }

    fun stepBack(v: View?){
        if(this.currentAnswer != "") return
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        inputLine.text = inputLine.text.dropLast(1)
        if(inputLine.text.length == 0) {
            inputLine.text = "0"
            if(inputLine.text.last() == '0') {
                val resultLine: TextView = v.findViewById(R.id.result_text)
                resultLine.text = ""
                return
            }
        }
        updateResultLine(v)
    }

    //Reduces inputLine text size and increases resultLine
    private fun maxTextSize(v: View?){
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        val resultLine: TextView = v.findViewById(R.id.result_text)
        if(resultLine.text.isEmpty()) return
        inputLine.textSize = 40.toFloat()
        resultLine.textSize = 70.toFloat()
        //inputLine.setTextColor(resources.getColor(android.R.color.darker_gray))
        //resultLine.setTextColor(resources.getColor(R.color.black))
    }

    //Increases inputLine text size and reduces resultLine
    private fun minTextSize(v: View?){
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        val resultLine: TextView = v.findViewById(R.id.result_text)
        inputLine.textSize = 70.toFloat()
        resultLine.textSize = 50.toFloat()
        //inputLine.setTextColor(resources.getColor(R.color.black))
        //resultLine.setTextColor(resources.getColor(android.R.color.darker_gray))
    }

    fun printResult(v: View?){
        val inputLine: TextView = v!!.findViewById(R.id.math_operation)
        val resultLine: TextView = v.findViewById(R.id.result_text)
        if(inputLine.text.length == 0 && inputLine.text.last() == '0') return
        if(resultLine.text.drop(2).toString() != "Infinit" && resultLine.text.drop(2).toString() != "NaN")
            this.currentAnswer = resultLine.text.drop(2).toString()
        else currentAnswer = "0"
        maxTextSize(v)
    }
}