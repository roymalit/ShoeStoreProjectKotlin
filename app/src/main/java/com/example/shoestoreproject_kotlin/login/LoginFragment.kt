package com.example.shoestoreproject_kotlin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentLoginBinding
import com.example.shoestoreproject_kotlin.shoelist.ShoeViewModel

/**
 * Fragment containing the Shoe Store login screen
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel by activityViewModels<ShoeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // Button click listeners
        binding.registerButton.setOnClickListener {
            login()
        }

        binding.loginButton.setOnClickListener {
            login()
        }

        return binding.root
    }

    // Navigates past the intro fragments depending on if the user has visited before
    private fun login (){
        if (viewModel.visitedBefore.value == true){
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToShoeListFragment())
        } else {
            findNavController()
                .navigate(LoginFragmentDirections.actionLoginDestinationToWelcomeFragment())
            // Set user has now visited before
            viewModel.hasVisitedBefore()
        }
    }
}