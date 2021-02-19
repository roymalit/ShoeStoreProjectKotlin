package com.example.shoestoreproject_kotlin.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentInstructionBinding

/**
 * Fragment detailing app instructions
 */
class InstructionFragment : Fragment() {
    private lateinit var binding: FragmentInstructionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.id.instruction_layout, container, false)

        return binding.root
    }

}