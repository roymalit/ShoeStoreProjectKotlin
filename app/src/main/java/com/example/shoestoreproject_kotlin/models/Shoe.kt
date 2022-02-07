package com.example.shoestoreproject_kotlin.models

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.example.shoestoreproject_kotlin.BR

data class Shoe(var name: String, var size: Double, var company: String, var description: String,
                var images: List<String> = mutableListOf()) : Observable {

    private val propertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

    @JvmName("getSize1")
    @Bindable
    fun getSize(): Double {
        return size
    }

    @JvmName("setSize1")
    fun setSize(newSize: Double) {
        if(newSize != size) {
            size = newSize
            propertyChangeRegistry.notifyChange(this, BR.shoe)
        }
    }
}