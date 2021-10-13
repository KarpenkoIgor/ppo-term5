package com.example.myphonecalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.isDigitsOnly


class SimpleCalculator : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCM: TextView = view.findViewById(R.id.btn_change)
        btnCM.setOnClickListener {changeModToScience()}
        val btn0:TextView = view.findViewById(R.id.btn_0)
        btn0.setOnClickListener {Handler.handler(view,"0")}
        val btnDot:TextView = view.findViewById(R.id.btn_dot)
        btnDot.setOnClickListener {Handler.handler(view,".")}
        val btnEqual:TextView = view.findViewById(R.id.btn_eq)
        btnEqual.setOnClickListener {Handler.printResult(view,)}
        val btn1:TextView = view.findViewById(R.id.btn_1)
        btn1.setOnClickListener {Handler.handler(view,"1")}
        val btn2:TextView = view.findViewById(R.id.btn_2)
        btn2.setOnClickListener {Handler.handler(view,"2")}
        val btn3:TextView = view.findViewById(R.id.btn_3)
        btn3.setOnClickListener {Handler.handler(view,"3")}
        val btnAdd:TextView = view.findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {Handler.handler(view,"+")}
        val btn4:TextView = view.findViewById(R.id.btn_4)
        btn4.setOnClickListener {Handler.handler(view,"4")}
        val btn5:TextView = view.findViewById(R.id.btn_5)
        btn5.setOnClickListener {Handler.handler(view,"5")}
        val btn6:TextView = view.findViewById(R.id.btn_6)
        btn6.setOnClickListener {Handler.handler(view,"6")}
        val btnSub:TextView = view.findViewById(R.id.btn_sub)
        btnSub.setOnClickListener {Handler.handler(view,"-")}
        val btn7:TextView = view.findViewById(R.id.btn_7)
        btn7.setOnClickListener {Handler.handler(view,"7")}
        val btn8:TextView = view.findViewById(R.id.btn_8)
        btn8.setOnClickListener {Handler.handler(view,"8")}
        val btn9:TextView = view.findViewById(R.id.btn_9)
        btn9.setOnClickListener {Handler.handler(view,"9")}
        val btnMul:TextView = view.findViewById(R.id.btn_mul)
        btnMul.setOnClickListener {Handler.handler(view,"×")}
        val btnAC:TextView = view.findViewById(R.id.btn_AC)
        btnAC.setOnClickListener {Handler.clean(view,)}
        val btnBack:TextView = view.findViewById(R.id.btn_back)
        btnBack.setOnClickListener {Handler.stepBack(view,)}
        val btnProc:TextView = view.findViewById(R.id.btn_proc)
        btnProc.setOnClickListener {Handler.percentButtonHandler(view)}
        val btnDiv:TextView = view.findViewById(R.id.btn_div)
        btnDiv.setOnClickListener {Handler.handler(view,"÷")}
    }

    companion object {
        @JvmStatic
        fun newInstance() = SimpleCalculator()
    }

    private fun changeModToScience(){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, ScientificCalculator())
            commit()
        }
    }

    private fun workingWithLines(){

    }


}