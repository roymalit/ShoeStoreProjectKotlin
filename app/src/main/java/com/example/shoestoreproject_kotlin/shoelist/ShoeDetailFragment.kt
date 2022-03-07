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
import com.example.shoestoreproject_kotlin.models.Shoe

/**
 * Fragment containing a shoe item's details
 */
class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding

    private val viewModel by activityViewModels<ShoeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail,
            container, false)

        // Set the viewmodel for databinding
        binding.shoeViewModel = viewModel
        binding.shoe = Shoe("",0.0,"","")

        // Button click listeners
        binding.buttonCancel.setOnClickListener { findNavController()
            .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()) }

        // Checks if a message is waiting to be triggered
        viewModel.alertMessage.observe(viewLifecycleOwner){ incomingMessage ->
            when (incomingMessage) {
                // TODO: Replace toasts to solve Android 7.1 (API 25) toast issue
                viewModel.successShoeAdded -> {
                    Toast.makeText(context, viewModel.successShoeAdded, Toast.LENGTH_SHORT).show()
                    viewModel.hasAddedShoeToList()
                    viewModel.hasShownMessage()
                }

                viewModel.errorViewEmpty -> {
                    Toast.makeText(context, "Cannot Save! Only IMAGES field may be empty",
                        Toast.LENGTH_SHORT).show()
                    viewModel.hasShownMessage()
                }

                viewModel.errorTextInvalid -> {
                    Toast.makeText(context, "Entered COMPANY or NAME does not match required " +
                            "format. Please check again", Toast.LENGTH_SHORT).show()
                    viewModel.hasShownMessage()
                }

                viewModel.errorSizeInvalid -> {
                    Toast.makeText(context, "Entered SIZE does not match required format. Please check again",
                        Toast.LENGTH_SHORT).show()
                    viewModel.hasShownMessage()

                }

                viewModel.errorMultilineInvalid -> {
                    Toast.makeText(context, "Entered DESCRIPTION does not match required format. Please check again",
                        Toast.LENGTH_SHORT).show()
                    viewModel.hasShownMessage()
                }
            }
        }


        return binding.root
    }

    // TODO: Resolve clearViews() resulting in blank item adding to list - entered details lost
    private fun clearViews(){
        binding.etCompany.text = null
        binding.etShoeName.text = null
        binding.etSize.text = null
        binding.etmlDescription.text = null
        binding.etmlShoeImages.text = null
    }

}