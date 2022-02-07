package com.example.shoestoreproject_kotlin.shoelist

import android.view.View
import android.widget.Toast
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

//    @Bindable
    // val editTextContent = MutableLiveData<String>()

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

    // Validation for single line text
    private val textPattern ="(?<firstWord>\\w+\\.*)(?<additionalWords> ?\\w+\\.*)* ?".toRegex()
    // Validation for shoe size
    private val sizePattern ="^(?<size>2[0-2]|1[0-9]|[1-9])(?<halfSize>\\.5)?\$".toRegex()
    // Validation for multiline text
    private val multiLineTextPattern = "^(?<firstWord>\\w+\\.*)(?<additionalWords>\\s?\\w+\\.*)* ?$"
        .toRegex(RegexOption.MULTILINE)

    init {
        Timber.i("ShoeViewModel created!")
        createShoeList()
        _shoeList.value = listOfShoes
        _visitedBefore.value = false
        _addedToList.value = false
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
        // TODO: Update to work with changed details structure
        // Trim unnecessary whitespace
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
            Timber.i(shoe.toString())
            listOfShoes.add(shoe)
            // For testing
            Timber.i(listOfShoes.toString())
        }
    }

    fun callWorking(view: View){
        Timber.i("Call worked!!")
        Toast.makeText(view.context, "Call Worked!!", Toast.LENGTH_SHORT).show()
    }

    // TODO: validation function for two-way binding implementation
    private fun validate (shoe: Shoe): Boolean{
        // Checks if any fields except Images have been left blank
        val isViewEmpty = shoe.company.isBlank() || shoe.name.isBlank() || shoe.size.equals(0.0)
                || shoe.description.isBlank()

        when {
//            // Checks if either text field is empty before attempting save
//            // Add || shoeImages.isBlank() when using images editText
//            isViewEmpty -> Toast.makeText(context,
//                "Cannot Save! Only IMAGES field may be empty", Toast.LENGTH_SHORT).show()
//
//            // Check if entered details matches the RegEx pattern
//            !textPattern.matches(shoe.company) || !textPattern.matches(shoe.name) -> Toast.makeText(context,
//                "Entered COMPANY or NAME does not match required format. Please check again",
//                Toast.LENGTH_SHORT).show()
//
//            !sizePattern.matches(shoe.size.toString()) -> Toast.makeText(context,
//                "Entered SIZE does not match required format. Please check again",
//                Toast.LENGTH_SHORT).show()
//
//            !multiLineTextPattern.matches(shoe.description) -> Toast.makeText(context,
//                "Entered DESCRIPTION does not match required format. Please check again",
//                Toast.LENGTH_SHORT).show()

            // Adds to viewModel if validation is met
            else -> {
                return true
            }
        }
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

    // Sets that user has passed Welcome and Instruction screens before
    fun hasVisitedBefore(){
        _visitedBefore.value = true
    }
    // Sets that a shoe has been added to the list
    fun addedToList(){
        _addedToList.value = true
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