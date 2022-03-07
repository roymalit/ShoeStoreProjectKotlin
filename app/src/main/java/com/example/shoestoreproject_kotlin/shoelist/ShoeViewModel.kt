package com.example.shoestoreproject_kotlin.shoelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestoreproject_kotlin.models.Shoe
import timber.log.Timber

/**
 * ViewModel for the Shoe Store activity
 */
class ShoeViewModel: ViewModel() {

    // The list of shoes to update
    private lateinit var listOfShoes: MutableList<Shoe>

    /**
     * Variables for storing and sending messages, errors & toasts
     */
    val errorViewEmpty = "viewEmpty"
    val errorTextInvalid = "textInvalid"
    val errorSizeInvalid = "sizeInvalid"
    val errorMultilineInvalid = "multilineInvalid"
    val successShoeAdded = "Shoe Saved!"

    // Live data for storing current message
    private val _alertMessage = MutableLiveData<String>()
    val alertMessage: LiveData<String>
        get() = _alertMessage

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

    // Tracks if a shoe was added to the list
    private val _addedToList = MutableLiveData<Boolean>()
    val addedToList: LiveData<Boolean>
        get() = _addedToList


    init {
        Timber.i("ShoeViewModel created!")
        createShoeList()
        _shoeList.value = listOfShoes
        _visitedBefore.value = false
        _addedToList.value = false
        _alertMessage.value = ""
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
    fun addShoe (shoeDetails: Array<String>, shoeImages: String){
        val company = shoeDetails[0].trim()
        val name = shoeDetails[1].trim()
        val size = shoeDetails[2].trim().toDouble()
        val description = shoeDetails[3].trim()
        // Handles text list of images when implemented. Not mandatory
        val imagesList = shoeImages.trim().split(",")

        // Creates Shoe object and adds to list
        _shoe.value = Shoe(name, size, company, description, imagesList)
        listOfShoes.add(shoe.value!!)
        // For testing
        Timber.i(listOfShoes.toString())
    }

    // TODO: addShoe function for two-way binding implementation
    fun addShoe (shoe: Shoe) {
        if (validate(shoe)){
            // Creates Shoe object and adds to list
            _shoe.value = shoe
            listOfShoes.add(shoe)
            // TODO: Create and call a 'message' LiveData for confirming shoe has been added
            _alertMessage.value = successShoeAdded
            _addedToList.value = true
            // For testing
            Timber.i(listOfShoes.toString())
        }
    }


    /*
    *   VALIDATION
    */
    // Validation for single line text
    private val textPattern ="(?<firstWord>\\w+\\.*)(?<additionalWords> ?\\w+\\.*)* ?".toRegex()
    // Validation for shoe size
    private val sizePattern ="^(?<size>2[0-2]|1[0-9]|[1-9])(?<halfSizeOrDouble>\\.5|\\.0)?\$".toRegex()
    // Validation for multiline text
    private val multiLineTextPattern = "^(?<firstWord>\\w+\\.*)(?<additionalWords>\\s?\\w+\\.*)* ?$"
        .toRegex(RegexOption.MULTILINE)

    // TODO: validation function for two-way binding implementation
    private fun validate (shoe: Shoe): Boolean{
        val isFormF = isFormFilled(shoe)
        val isTextPV = isTextPatternValid(shoe)
        val isSizePV = isSizePatternValid(shoe)
        val isMlinePV = isMultilinePatternValid(shoe)

        return when {
            !isFormF -> {
                _alertMessage.value = errorViewEmpty
                false
            }

            !isTextPV -> {
                _alertMessage.value = errorTextInvalid
                false
            }

            !isSizePV -> {
                _alertMessage.value = errorSizeInvalid
                false
            }

            !isMlinePV -> {
                _alertMessage.value = errorMultilineInvalid
                false
            }
            // Adds to viewModel if validation is met
            else -> {
                true
            }
        }
    }

    // Checks if any fields except Images have been left blank
    private fun isFormFilled(shoe: Shoe): Boolean{
        return !(shoe.company.isBlank() || shoe.name.isBlank() || shoe.size.equals(0.0)
                || shoe.description.isBlank())
    }

    // Check if entered details matches the RegEx pattern
    private fun isTextPatternValid(shoe: Shoe): Boolean{
        return textPattern.matches(shoe.company) && textPattern.matches(shoe.name)
    }

    private fun isSizePatternValid(shoe: Shoe): Boolean{
        return sizePattern.matches(shoe.size.toString())
    }

    private fun isMultilinePatternValid(shoe: Shoe): Boolean{
        return multiLineTextPattern.matches(shoe.description)
    }

    // Fills the viewModel with dummy data for testing convenience
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
        Timber.i("Dummy data added!")
    }

    // Checks if an error message has been shown
    fun hasShownMessage(){
        if (alertMessage.value != ""){
            _alertMessage.value = ""
        }
    }

    // Sets that user has passed Welcome and Instruction screens before
    fun hasVisitedBefore(){
        _visitedBefore.value = true
    }
    // Checks if a shoe has been added to the list
    fun hasAddedShoeToList(){
        if (addedToList.value != false){
            _addedToList.value = false
        }
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