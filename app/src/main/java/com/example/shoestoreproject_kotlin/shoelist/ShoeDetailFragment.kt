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
        ^(\w+(?: \w+)*) *, *\d+(?:\.5)* *, *(\w+(?: \w+)*) *, *(\w+(?: \w+)*) *$

        Readable explanation:
        words+([space]words+)*[space]*,[space]*digit+(.5)*[space]*,
        [space]*words+([space]words+)*[space]*,[space]*words+([space]words+)*[space]*
     */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail,
            container, false)

            // Button click listeners
        binding.buttonCancel.setOnClickListener { findNavController()
            .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()) }
        
        binding.buttonSave.setOnClickListener{
            val shoeDetails =  binding.etmlNewShoeDetails.text.toString()
            val shoeImages = binding.etmlNewShoeImages.text.toString()

            // Checks if either text field is empty before attempting save
            // Add || shoeImages.isBlank() when using images editText
            if (shoeDetails.isBlank()){
                Toast.makeText(context, "Cannot Save! Field must not be empty",
                    Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addShoe(shoeDetails, shoeImages)
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                binding.etmlNewShoeDetails.text = null
//                binding.etmlNewShoeImages.text = null
            }
        }

        binding.buttonDummy.setOnClickListener{
            viewModel.dummyData()
            Toast.makeText(context, "Dummy data added!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    // Checks if new shoe details are written in correct format
    private fun newDetailsValidation(){

    }

}