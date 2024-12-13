package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var tvInput:TextView? = null
    private var lastNumaric : Boolean = false
    private var lastDot : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view : View){
        tvInput?.append((view as Button).text)
        lastNumaric = true

    }
    fun onClaer(view : View){
        tvInput?.text = ""
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumaric && !lastDot){
            tvInput?.append(".")
            lastDot = true
            lastNumaric = false
        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return  if(value.startsWith("-") )  {
            false
        }else{
            value.contains("-") ||
            value.contains("+") ||
            value.contains("X") ||
            value.contains("/")
        }
    }

    fun onOperator(view: View){
        tvInput?.text.let {
            if(lastNumaric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumaric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if (lastNumaric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            var result = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")
                    var one = splitValue[0].toDouble()
                    var two = splitValue[1].toDouble()
                    result = (one - two).toInt().toString()
                }
                else if(tvValue.contains("+")){
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0].toDouble()
                    var two = splitValue[1].toDouble()
                    result = (one + two).toInt().toString()
                }
                else if(tvValue.contains("X")){
                    var splitValue = tvValue.split("X")
                    var one = splitValue[0].toDouble()
                    var two = splitValue[1].toDouble()
                    result = (one * two).toInt().toString()
                }
                else if(tvValue.contains("/")){
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0].toDouble()
                    var two = splitValue[1].toDouble()
                    result = (one / two).toString()
                }
                if (prefix.isNotEmpty()){
                    result = "$prefix$result"
                }
                if(result.contains(".0")){
                    result = result.substring(0 , result.length - 2)
                }
                tvInput?.text = result


            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

}