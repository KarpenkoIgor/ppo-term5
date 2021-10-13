package com.example.myphonecalculator

import android.view.View
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import androidx.core.text.isDigitsOnly


object Handler {
    private var currentAnswer = ""
    var angleConf = "deg"
    var trigonometryConf = ""

    fun handler(v: View?, str:String, inputLine:TextView, resultLine: TextView){
        minTextSize(v,inputLine, resultLine)
        if(this.currentAnswer != "") {
            if(str.last().isDigit()) inputLine.text = "0"
            else inputLine.text = this.currentAnswer
            this.currentAnswer = ""
        }
        if(inputLine.text.last() == '.' && !str.isDigitsOnly()){
            inputLine.append("0")
        }
        if(str.isDigitsOnly()) {
            if(getFirstDigit(inputLine.text.toString()) == "0") inputLine.text = inputLine.text.dropLast(1)

            inputLine.append(str)
        }
        else if (str == "."){
            if(inputLine.text.last().isDigit() && getFirstDigit(inputLine.text.toString()) != ".")
                inputLine.append(str)
        }
        else if (str == "e" || str == "π"){
            if(getFirstDigit(inputLine.text.toString()) == "0"){
                inputLine.text = inputLine.text.dropLast(1)
                inputLine.append(str)
            }
            else if(inputLine.text.last() in arrayOf(')','π','e') || inputLine.text.last().isDigit())
                inputLine.append("×" + str)
            else inputLine.append(str)
        }
        else if (str in arrayOf("+","-","×","÷")){
            if (inputLine.text.last() in arrayOf('+','-','×','÷')){
                inputLine.text = inputLine.text.dropLast(1)
                inputLine.append(str)
            }
            else inputLine.append(str)
        }
        else if (str in arrayOf("(",")")){
            if(str == "("){
                if(getFirstDigit(inputLine.text.toString()) == "0"){
                    inputLine.text = inputLine.text.dropLast(1)
                }
                else if (inputLine.text.last() in arrayOf('π','e','!',')') || inputLine.text.last().isDigit())
                    inputLine.append("×")
                inputLine.append(str)
            }
            else{
                if(inputLine.text.last() in "+-×÷"){

                }
                else if(getBracketsBalans(inputLine.text.toString())> 0){
                    if(inputLine.text.last() == '(')
                        inputLine.append("0)")
                    else
                        inputLine.append(")")
                }
            }
        }
        else if (str in arrayOf("sin", "cos", "tan")){
            if(getFirstDigit(inputLine.text.toString()) == "0"){
                inputLine.text = inputLine.text.dropLast(1)
            }
            else if (inputLine.text.last() in arrayOf('π','e','!',')') || inputLine.text.last().isDigit())
                inputLine.append("×")
            inputLine.append(str + "(")
        }
        updateResultLine(v,inputLine, resultLine)
    }

    fun percentButtonHandler(v: View?,inputLine:TextView, resultLine: TextView){
        updateResultLine(v,inputLine, resultLine)
    }

    private fun getTempNum(sym:String):String{
        if(sym in arrayOf("+", "-")) return "0"
        else if(sym in arrayOf("×","÷")) return "1"
        return "0"
    }

    private fun updateResultLine(v: View?, inputLine:TextView, resultLine: TextView){
        var tempLine:String = inputLine.text.toString()
        if (tempLine.last() in arrayOf('+', '-', '.')) tempLine += "0"
        if (tempLine.last() in arrayOf('×','÷')) tempLine += "1"
        if (tempLine.last() == '('){
            if(tempLine.length > 1 && tempLine.reversed()[1] in arrayOf('×','÷','+','-')){
                tempLine += getTempNum(tempLine.reversed()[1].toString())
            }
            else {
                tempLine += 0
            }
        }
        if (getBracketsBalans(tempLine) != 0){
            tempLine += ")".repeat(getBracketsBalans(tempLine))
        }
        tempLine = tempLine.replace('×', '*')
        tempLine = tempLine.replace('÷', '/')

        val expression = ExpressionBuilder(tempLine).build()
        val numResult = expression.evaluate()

        //Leave only 6 digits after dot
        var finalAnswer = String.format("%.6f", numResult).toDouble()
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
        resultLine.text = tempLine
    }

    fun clean(v: View?, inputLine:TextView, resultLine: TextView){
        minTextSize(v,inputLine, resultLine)
        inputLine.text = "0"
        resultLine.text = ""
        this.currentAnswer = ""
    }

    fun stepBack(v: View?, inputLine:TextView, resultLine: TextView){
        if(this.currentAnswer != "") return
        for (literal in arrayOf("sin(", "cos(", "tan(","asin(", "acos(", "atan(","lg(", "ln(")) {
            if (inputLine.text.endsWith(literal)) {
                inputLine.text = inputLine.text.dropLast(literal.length)
                updateResultLine(v,inputLine, resultLine)
                return
            }
        }
        inputLine.text = inputLine.text.dropLast(1)
        if(inputLine.text.length == 0) {
            inputLine.text = "0"
            if(inputLine.text.last() == '0') {
                resultLine.text = ""
                return
            }
        }
        updateResultLine(v,inputLine, resultLine)
    }

    //Reduces inputLine text size and increases resultLine
    private fun maxTextSize(v: View?, inputLine:TextView, resultLine: TextView){
        if(resultLine.text.isEmpty()) return
        inputLine.textSize = 40.toFloat()
        resultLine.textSize = 70.toFloat()
        //inputLine.setTextColor(resources.getColor(android.R.color.darker_gray))
        //resultLine.setTextColor(resources.getColor(R.color.black))
    }

    //Increases inputLine text size and reduces resultLine
    private fun minTextSize(v: View?, inputLine:TextView, resultLine: TextView){
        inputLine.textSize = 70.toFloat()
        resultLine.textSize = 50.toFloat()
        //inputLine.setTextColor(resources.getColor(R.color.black))
        //resultLine.setTextColor(resources.getColor(android.R.color.darker_gray))
    }

    fun printResult(v: View? ,inputLine:TextView, resultLine: TextView){
        if(inputLine.text.length == 0 && inputLine.text.last() == '0') return
        if(resultLine.text.drop(2).toString() != "Infinit" && resultLine.text.drop(2).toString() != "NaN")
            this.currentAnswer = resultLine.text.drop(2).toString()
        else currentAnswer = "0"
        maxTextSize(v,inputLine, resultLine)
    }

    private fun getFirstDigit(str:String):String{
        var last = '+'
        for(dig in str.reversed()){
            if(!dig.isDigit()){
                if(dig == '.') last = dig
                break
            }
            last = dig
        }
        return last.toString()
    }

    private fun getBracketsBalans(str:String):Int{
        var ans = 0
        for(s in str){
            if(s == '(') ans++
            if(s == ')') ans--
        }
        return ans
    }

    fun isRadAvailable():Boolean{
        if (trigonometryConf == "") return true
        return false
    }
    fun isArcAvailable():Boolean{
        if (angleConf == "deg") return true
        return false
    }

}