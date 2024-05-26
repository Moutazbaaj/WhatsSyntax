package com.syntax_institut.whatssyntax.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.R
import com.syntax_institut.whatssyntax.data.datamodels.Profile
import com.syntax_institut.whatssyntax.databinding.FragmentSettingsBinding


// Fragment class for displaying settings
class SettingsFragment : Fragment() {

    // Binding object for the fragment
    private lateinit var binding: FragmentSettingsBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }


    // Initialize views and set click listeners
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Load profile data
        viewModel.loadProfileData()


        // Observe profile data changes and update UI accordingly
        viewModel.profile.observe(viewLifecycleOwner) { profiles ->
            if (profiles.isNotEmpty()) {
                val profile = profiles[0]
                binding.apply {

                    // Populate UI with profile data
                    tietProfileName.setText(profile.name)
                    tietProfileNumber.setText(profile.number)
                    ivProfile.load(profile.image) {
                        transformations(RoundedCornersTransformation(30f))
                        placeholder(android.R.drawable.ic_menu_crop)
                        error(R.drawable.round_broken_image_25)
                    }

                    // Save profile data on button click
                    btProfileSave.setOnClickListener {
                        val updateProfileData = Profile(
                            name = tietProfileName.text.toString(),
                            number = tietProfileNumber.text.toString(),
                            image = profile.image
                        )
                        if (tietProfileName.text?.isNotEmpty() == true && tietProfileNumber.text?.isNotEmpty() == true) {
                            viewModel.setProfileData(updateProfileData)

                            binding.tietProfileName.setText(profile.name)
                            binding.tietProfileNumber.setText(profile.number)

                            viewModel.loadProfileData()

                            Toast.makeText(
                                requireContext(),
                                "Profile updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Name and number cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    // Save profile image link on button click
                    btSaveImg.setOnClickListener {
                        val updateProfileData = Profile(
                            name = profile.name,
                            number = profile.number,
                            image = binding.imgLink.text.toString()
                        )
                        if (imgLink.text?.isNotEmpty() == true) {

                            viewModel.setProfileData(updateProfileData)

                            // Load updated profile image
                            binding.ivProfile.load(updateProfileData.image) {
                                transformations(RoundedCornersTransformation(30f))
                                placeholder(android.R.drawable.ic_menu_crop)
                                error(R.drawable.round_broken_image_25)
                            }

                            viewModel.loadProfileData()

                            Toast.makeText(
                                requireContext(),
                                "Profile Picture updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Image link cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }


            // Navigate back to chat fragment on button click
            binding.btDone.setOnClickListener {
                val action = binding.btDone.findNavController()
                action.navigate(SettingsFragmentDirections.actionSettingsFragmentToChatsFragment())
            }

        }
    }
}



