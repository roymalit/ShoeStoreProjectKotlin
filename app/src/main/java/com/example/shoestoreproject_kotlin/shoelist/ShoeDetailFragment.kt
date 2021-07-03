package com.example.shoestoreproject_kotlin.shoelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentShoeDetailBinding

/**
 * Fragment containing a shoe item's details
 */
class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding

    private val viewModel by activityViewModels<ShoeViewModel>()

    /*
        shoe detail validation regular expression
        ^(\w+(?: \w+)*) *,\s*([1-9]|1[0-9]|2[0-2])(\.5)? *,\s*(\w+(?: \w+)*) *,\s*(\w+(?: \w+)*) *$

        TODO: Edit readable explanation
        Readable explanation:
        word+([space]word+)*[space]*,[whitespace]*(1...22)(.5)?[space]*,
        [whitespace]*words+([space]words+)*[space]*,[space]*words+([space]words+)*[space]*
     */
    // Regular expression for checking entered details match required format
    private val pattern = "^(\\w+(?: \\w+)*) *,\\s*([1-9]|1[0-9]|2[0-2])(\\.5)? *,\\s*(\\w+(?: \\w+)*) *,\\s*(\\w+(?: \\w+)*) *\$"
        .toRegex(RegexOption.MULTILINE)

    // TODO: Create regular expressions
    // Validation for single line text
    private val textPattern ="(\\w+(?: \\w+)*) *".toRegex()
    // Validation for shoe size
    private val sizePattern ="^(?<size>2[0-2]|1[0-9]|[1-9])(?<halfSize>\\.5)?\$".toRegex()
    // Validation for multiline text
    private val multiLineTextPattern = "^(?<firstWord>\\w+\\.*)(?<additionalWords>\\s\\w+\\.*)*\$"
        .toRegex(RegexOption.UNIX_LINES)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail,
            container, false)

            // Button click listeners
        binding.buttonCancel.setOnClickListener { findNavController()
            .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()) }
        
        binding.buttonSave.setOnClickListener{
            // Grabs user inputted text
            val company = binding.etCompany.text.toString()
            val name = binding.etShoeName.text.toString()
            val size = binding.etSize.text.toString()
            val description = binding.etmlDescription.text.toString()
            val shoeImages = binding.etmlNewShoeImages.text.toString()
            // Create string array of grabbed values
            val shoeDetails =  arrayOf(company, name, size, description)

            // Checks if any fields except Images have been left blank
            val isViewEmpty = company.isBlank() || name.isBlank() || size.isBlank()
                    || description.isBlank()

            when {
                // Checks if either text field is empty before attempting save
                // Add || shoeImages.isBlank() when using images editText
                isViewEmpty -> Toast.makeText(context,
                    "Cannot Save! Only IMAGES field may be empty", Toast.LENGTH_SHORT).show()

                // Check if entered details matches the RegEx pattern
                !textPattern.matches(company) || !textPattern.matches(name) -> Toast.makeText(context,
                    "Entered details don't match required format. Please check again",
                    Toast.LENGTH_SHORT).show()
                // TODO: Add other validation checks

                // Adds to viewModel if validation is met
                else -> {
                    viewModel.addShoe(shoeDetails, shoeImages)
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                    binding.etCompany.text = null
                    // TODO: Clear other views
//                    binding.etmlNewShoeImages.text = null
                }
            }
        }

        binding.buttonDummy.setOnClickListener{
            viewModel.dummyData()
            Toast.makeText(context, "Dummy data added!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}