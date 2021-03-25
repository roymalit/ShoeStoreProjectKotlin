package com.example.shoestoreproject_kotlin.shoelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentShoeDetailBinding

/**
 * Fragment containing a shoe item's details
 */
class ShoeDetailFragment : Fragment() {
    private lateinit var binding: FragmentShoeDetailBinding

    private lateinit var viewModel: ShoeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        // Get viewModel
        viewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        // Set the viewmodel for databinding
//        binding.shoeViewModel = viewModel
        binding.lifecycleOwner = this

        // TODO: Observe shoeList changes, update UI accordingly

        binding.buttonCancel.setOnClickListener { findNavController()
            .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment()) }
        
        binding.buttonSave.setOnClickListener{
            val shoeDetails =  binding.etmlNewShoeDetails.text.toString()
            val shoeImages = binding.etmlNewShoeImages.text.toString()
            if (shoeDetails.isBlank() || shoeImages.isBlank()){
                Toast.makeText(context, "Cannot Save! Fields must not be empty",
                    Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addShoe(shoeDetails, shoeImages)
            }
        }

        return binding.root
    }

}