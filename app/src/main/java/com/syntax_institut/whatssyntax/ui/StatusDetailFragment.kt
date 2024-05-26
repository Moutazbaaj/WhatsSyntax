package com.syntax_institut.whatssyntax.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.R
import com.syntax_institut.whatssyntax.data.remote.BASE_URL
import com.syntax_institut.whatssyntax.databinding.FragmentStatusDetailBinding


// Fragment class for displaying status details
class StatusDetailFragment : Fragment() {

    // Binding object for the fragment
    private lateinit var binding: FragmentStatusDetailBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Initialize views and set click listeners
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Retrieve status ID from arguments
        val status = arguments?.getInt("status") ?: 0
        var index = 0


        // Retrieve contact with status from ViewModel
        val contact = viewModel.contact.value?.find { it.id == status }
        contact?.status?.images?.let { images ->

            // Display the status owner's name
            binding.tvStatus.text = contact.name+ "'s Status :"


            // Load the first image of the status
            if (index < images.size) {
                binding.statusImagesContainer.load("$BASE_URL${images[index]}"){
                    placeholder(android.R.drawable.ic_menu_crop)
                    error(R.drawable.round_broken_image_25)
                    transformations(RoundedCornersTransformation(100f))
                }
            } else {

                // Navigate to status fragment if no images available
                navigateToStatusFragment()
            }
        }

        // Set click listener for navigating to the next image or back
        binding.cvStatusDetail.setOnClickListener {
            contact?.status?.images?.let { images ->
                if (index +1 < images.size) {
                    index ++

                    // Load the next image
                    binding.statusImagesContainer.load("$BASE_URL${images[index]}"){
                        placeholder(android.R.drawable.ic_menu_crop)
                        error(R.drawable.round_broken_image_25)
                        transformations(RoundedCornersTransformation(100f))
                    }
                } else {

                    // Navigate to status fragment if no more images available
                    navigateToStatusFragment()
                }
            }
        }
    }


    // Navigate to status fragment
    private fun navigateToStatusFragment() {
        val action = binding.cvStatusDetail.findNavController()
        action.navigate(StatusDetailFragmentDirections.actionStatusDetailFragmentToStatusFragment())
    }
}
