package com.example.shoestoreproject_kotlin.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentLoginBinding

/**
 * Fragment containing the Shoe Store login screen
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // Button click listeners
        binding.registerButton.setOnClickListener { findNavController()
            .navigate(LoginFragmentDirections.actionLoginDestinationToWelcomeFragment())
        }

        binding.loginButton.setOnClickListener { findNavController()
            .navigate(LoginFragmentDirections.actionLoginDestinationToWelcomeFragment())
        }

        return binding.root
    }

}