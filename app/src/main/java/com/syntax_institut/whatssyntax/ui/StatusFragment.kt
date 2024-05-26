package com.syntax_institut.whatssyntax.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntax_institut.whatssyntax.ApiStatus
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.data.Adapter.StatusAdapter
import com.syntax_institut.whatssyntax.data.datamodels.Contact
import com.syntax_institut.whatssyntax.databinding.FragmentStatusBinding


// Fragment for displaying status
class StatusFragment : Fragment() {


    // Binding object for the fragment
    private lateinit var binding: FragmentStatusBinding

    // ViewModel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusBinding.inflate(layoutInflater)
        return binding.root
    }

    // Initialize views and observe data changes
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load contact data from ViewModel
        viewModel.loadContactData()


        // Observe changes in contact data
        viewModel.contact.observe(viewLifecycleOwner) { contacts ->

            // Sort contacts based on status availability and name
            val sortedContacts =
                contacts.sortedWith(compareByDescending<Contact> { it.status != null }.thenBy { it.name })

            // Update the adapter with sorted contacts
            binding.rvContacts.adapter = StatusAdapter(sortedContacts)
        }

        // Observe loading status and update UI accordingly
        viewModel.loading.observe(viewLifecycleOwner) { apiStatus ->
            when (apiStatus) {
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvContacts.visibility = View.GONE
                }

                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorImage.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Loading error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorImage.visibility = View.GONE
                    binding.rvContacts.visibility = View.VISIBLE
                }
            }
        }

    }

}