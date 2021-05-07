package com.example.shoestoreproject_kotlin.shoelist

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.shoestoreproject_kotlin.R
import com.example.shoestoreproject_kotlin.databinding.FragmentShoeListBinding
import com.example.shoestoreproject_kotlin.models.Shoe

// Extensions
// dp to px conversion
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


class ShoeListFragment : Fragment() {
    private lateinit var binding: FragmentShoeListBinding

    private val viewModel by activityViewModels<ShoeViewModel>()

    // Used for view child position
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return NavigationUI.onNavDestinationSelected(item, findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list,
            container, false)

        // Button click listeners
        binding.fabShoeDetail.setOnClickListener{findNavController()
            .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())}

        // Set the viewmodel for databinding
        binding.shoeViewModel = viewModel
        binding.lifecycleOwner = this

        // Reusable margin parameters. Use dp converted to px
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(3.px, 8.px, 3.px, 0)

        // Observes shoeList changes, updates UI accordingly
        viewModel.shoeList.observe(viewLifecycleOwner, { list ->
            list.forEach { shoe ->
                newTextView(shoe, params)
                position++
            }
        })

        return binding.root
    }

    // Creates a new text view for a shoe object
    private fun newTextView(shoe: Shoe, params: LinearLayout.LayoutParams) {
        val textView = TextView(context)
        textView.setTextAppearance(R.style.BodyTextStyle)
        textView.setBackgroundResource(R.drawable.list_item_border)
        textView.text = getString(
            R.string.list_item_format,
            shoe.company,
            shoe.name,
            shoe.size
        )
        binding.llShoeList.addView(textView, position, params)
    }
}