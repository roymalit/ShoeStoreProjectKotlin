package com.example.shoestoreproject_kotlin.data

import androidx.databinding.InverseMethod

object Converter {
    // Converts a String to a Double
    @JvmStatic fun stringToDouble(value: String): Double {
        try {
            return if (value.isNotEmpty()){
                value.toDouble()
            } else {
                return 0.0
            }
        } catch (e: NumberFormatException){
            return -1.0
        }

    }

    @InverseMethod("stringToDouble")
    @JvmStatic fun doubleToString(value: Double): String {
        return if (value != 0.0){
            value.toString()
        } else {
            return ""
        }
    }

    // Converts a String to a List<String>
    @JvmStatic fun stringToStringList(value: String): List<String> {
         return if (value.isNotEmpty()){
            value.trim().split(",")
        } else {
            return emptyList()
        }
    }

    @InverseMethod ("stringToStringList")
    @JvmStatic fun stringListToString(value: List<String>?): String {
        return if (value?.isNotEmpty() == true){
            value.joinToString()
        } else {
            return ""
        }
    }
}