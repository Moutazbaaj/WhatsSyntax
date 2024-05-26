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
import com.syntax_institut.whatssyntax.data.Adapter.CallAdapter
import com.syntax_institut.whatssyntax.databinding.FragmentCallsBinding


// Fragment class for displaying call logs
class CallsFragment : Fragment() {


    // Binding object for the fragment
    private lateinit var binding: FragmentCallsBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallsBinding.inflate(layoutInflater)
        return binding.root
    }

    // Initialize views and observe LiveData
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load call data
        viewModel.loadCallData()


        // Observe call data changes and update UI accordingly
        viewModel.call.observe(viewLifecycleOwner) { call ->
            binding.rvCalls.adapter = CallAdapter(call)
        }

        // Observe loading status and update UI accordingly
        viewModel.loading.observe(viewLifecycleOwner) { apiStatus ->
            when (apiStatus) {
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvCalls.visibility = View.GONE
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
                    binding.rvCalls.visibility = View.VISIBLE
                }
            }
        }

    }
}