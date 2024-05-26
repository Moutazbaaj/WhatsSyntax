package com.syntax_institut.whatssyntax.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.syntax_institut.whatssyntax.ApiStatus
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.data.Adapter.ChatDetailAdapter
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.data.local.WhatsSyntaxDatabaseDao
import com.syntax_institut.whatssyntax.databinding.FragmentChatDetailBinding
import kotlin.math.abs


// Fragment class for displaying chat details
class ChatDetailFragment : Fragment() {

    // Binding object for the fragment
    private lateinit var binding: FragmentChatDetailBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Adapter for the RecyclerView
    private lateinit var adapter: ChatDetailAdapter



    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailBinding.inflate(layoutInflater)
        return binding.root
    }


    // Initialize views and set click listeners
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Retrieve chat ID from arguments
        val chatId = arguments?.getInt("chatId")
        val name = arguments?.getString("name")

        // Initialize the adapter
        adapter = ChatDetailAdapter(mutableListOf(),viewModel,name.toString(), requireContext())
        binding.rvMessages.adapter = adapter

        binding.toolBar.title = name

        // Observe message data
        viewModel.message.observe(viewLifecycleOwner) { messageList ->
            adapter = ChatDetailAdapter(messageList.toMutableList(),viewModel,name.toString(),requireContext())
            binding.rvMessages.adapter = adapter

            // set the List scroll Position
            binding.rvMessages.layoutManager?.scrollToPosition(messageList.size - 1)
        }


        // Send a message to API when btSend is clicked
        binding.btSend.setOnClickListener {
            val newMessage = binding.tietMessage.text.toString()
            if (chatId != null && newMessage.isNotEmpty()) {
                val messages = Messages(text = newMessage, incoming = false)
                viewModel.sendMessage(chatId, messages)
                viewModel.loadMessage(chatId)
            }
            binding.tietMessage.text?.clear()
        }


        // Navigate back to chat fragment on button click
        binding.btnDone.setOnClickListener {
            val action = binding.btnDone.findNavController()
            action.navigate(ChatDetailFragmentDirections.actionChatDetailFragmentToChatsFragment())
        }

        // Observe loading status and update UI accordingly
        viewModel.loading.observe(viewLifecycleOwner) { apiStatus ->
            when (apiStatus) {
                ApiStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    Toast.makeText(
                        requireContext(),
                        "Loading error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }




    }
}