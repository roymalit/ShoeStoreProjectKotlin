package com.example.shoestoreproject_kotlin.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreproject_kotlin.models.Shoe
import timber.log.Timber

class ShoeModelView: ViewModel() {

    private lateinit var listOfShoes: MutableList<Shoe>

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    init {
        Timber.i("ShoeViewModel created!")
        createShoeList()
        _shoeList.value = listOfShoes
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("ShoeViewModel destroyed!")
    }

    private fun createShoeList() {
        if (listOfShoes.isNullOrEmpty()){
            listOfShoes = mutableListOf()
        }
    }

    private fun addShoe (shoe: Shoe){
        listOfShoes.add(shoe)
    }

    private fun removeShoe(){

    }
}