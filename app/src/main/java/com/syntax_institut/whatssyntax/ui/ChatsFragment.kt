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
import com.syntax_institut.whatssyntax.data.Adapter.ChatAdapter
import com.syntax_institut.whatssyntax.databinding.FragmentChatBinding


// Fragment class for displaying chats
class ChatsFragment : Fragment() {

    // Binding object for the fragment
    private lateinit var binding: FragmentChatBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()




    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }


    // Initialize views and observe LiveData
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Load chat data
        viewModel.loadChatData()


        // Observe chat data changes and update UI accordingly
        viewModel.chat.observe(viewLifecycleOwner) { chats ->
            binding.rvChats.adapter = ChatAdapter(chats, viewModel)

        }

        // Observe loading status and update UI accordingly
        viewModel.loading.observe(viewLifecycleOwner) { apiStatus ->
            when (apiStatus) {
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvChats.visibility = View.GONE
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
                    binding.rvChats.visibility = View.VISIBLE
                }
            }
        }
    }
}