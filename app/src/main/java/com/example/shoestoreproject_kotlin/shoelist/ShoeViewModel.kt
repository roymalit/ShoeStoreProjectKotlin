package com.example.shoestoreproject_kotlin.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreproject_kotlin.models.Shoe
import timber.log.Timber

class ShoeViewModel: ViewModel() {

    private lateinit var listOfShoes: MutableList<Shoe>

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _shoe = MutableLiveData<Shoe>()
    val shoe: LiveData<Shoe>
        get() = _shoe

    init {
        Timber.i("ShoeViewModel created!")
        createShoeList()
        _shoeList.value = listOfShoes
//        _shoe.value = listOfShoes.first()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("ShoeViewModel destroyed!")
    }

    /*
    * Initialize list of shoes if it isn't already
    */
    private fun createShoeList() {
        if (!this::listOfShoes.isInitialized){
            listOfShoes = mutableListOf()
        }
    }

    /*
    * Adds new shoe to the list
    */
    fun addShoe (shoeDetails: String, shoeImages: String){
        val split = shoeDetails.split(",")
        val name = split[0]
        val size = split[1].toDouble()
        val company = split[2]
        val description = split[3]
        val imagesList = shoeImages.split(",")

        // Create Shoe object
        val shoe = Shoe(name, size, company, description, imagesList)
        listOfShoes.add(shoe)
        Timber.i(listOfShoes.toString())
    }

    fun nextShoe(){
        //Select Shoe object from list
        if (shoeList.value?.isNotEmpty() == true) {
//            shoe.value = shoeList.value
        }
    }

    /*
    * Removes last added shoe from the list
    */
    fun removeShoe(){
        if (listOfShoes.size >= 1){
            listOfShoes.removeAt(listOfShoes.size - 1)
        }
    }
}