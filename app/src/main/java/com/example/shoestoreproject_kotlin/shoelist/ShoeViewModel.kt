package com.example.shoestoreproject_kotlin.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreproject_kotlin.models.Shoe
import timber.log.Timber

class ShoeViewModel: ViewModel() {

    // The list of shoes to update
    private lateinit var listOfShoes: MutableList<Shoe>

    // The shoe list live data
    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    // The shoe object live data
    private val _shoe = MutableLiveData<Shoe>()
    val shoe: LiveData<Shoe>
        get() = _shoe

    // Live data tracking if the user has signed into the app before
    private val _visitedBefore = MutableLiveData<Boolean>()
    val visitedBefore: LiveData<Boolean>
        get() = _visitedBefore

    init {
        Timber.i("ShoeViewModel created!")
        createShoeList()
        _shoeList.value = listOfShoes
        _visitedBefore.value = false
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
        // Handles text list of images when implemented. Not mandatory
        val imagesList = shoeImages.split(",")

        // Creates Shoe object and adds to list
        _shoe.value = Shoe(name, size, company, description, imagesList)
        listOfShoes.add(shoe.value!!)
        Timber.i(listOfShoes.toString())
    }

    fun dummyData (){
        listOfShoes.addAll(listOf(
            Shoe("Blazer", 7.0, "Nike", "White"),
            Shoe("Yeezy", 9.5, "Adidas", "Black"),
            Shoe("Nano X1", 5.0, "Reebok", "Grey & Neon green"),
            Shoe("Rider", 11.0, "Dummy Co.", "Cobalt"),
            Shoe("Lightweight", 6.5, "Dummy Co.", "Green"),
            Shoe("Redline", 8.0, "Dummy Co.", "Red"),
            Shoe("Heeler", 7.5, "Dummy Co.", "Black")
        ))
    }

    // Sets that user has passed Welcome and Instruction screens before
    fun hasVisitedBefore(){
        _visitedBefore.value = true
    }

//    fun nextShoe(){
//        //Select Shoe object from list
//        if (shoeList.value?.isNotEmpty() == true) {
////            shoe.value = shoeList.value
//        }
//    }

    /*
    * Removes last added shoe from the list. For future implementation
    */
    fun removeShoe(){
        if (listOfShoes.size >= 1){
            listOfShoes.removeAt(listOfShoes.size - 1)
        }
    }
}