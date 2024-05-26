package com.syntax_institut.whatssyntax.data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.databinding.ItemChatInBinding
import com.syntax_institut.whatssyntax.databinding.ItemChatOutBinding
import java.lang.IllegalArgumentException
import androidx.appcompat.app.AlertDialog


// Adapter for handling chat messages in a RecyclerView
class ChatDetailAdapter(

    // Dataset containing chat messages
    private val dataset: MutableList<Messages>,

    // View model for handling logic
    private val viewModel: MainViewModel,

    // Name of the note associated with the chat
    private val contactName: String,

    // Context reference for displaying dialogs and toasts
    private val context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // View type for sent messages
    private val sentMessage = 1

    // View type for received messages
    private val receivedMessage = 2


    // ViewHolder for sent messages
    inner class SendMessageViewHolder(val binding: ItemChatOutBinding) :
        RecyclerView.ViewHolder(binding.root)


    // ViewHolder for received messages
    inner class ReceiveMessageViewHolder(val binding: ItemChatInBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Inflate the appropriate layout based on the view type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            sentMessage -> {
                val binding = ItemChatOutBinding.inflate(inflater, parent, false)
                SendMessageViewHolder(binding)
            }

            receivedMessage -> {
                val binding = ItemChatInBinding.inflate(inflater, parent, false)
                ReceiveMessageViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view..")
        }
    }


    // Return the size of the dataset
    override fun getItemCount(): Int {
        return dataset.size
    }


    // Determine the view type of the item at the given position
    override fun getItemViewType(position: Int): Int {
        return if (dataset[position].incoming) receivedMessage else sentMessage
    }


    // Bind data to the views within the ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message = dataset[position]

        when (holder.itemViewType) {
            sentMessage -> {
                val sendHolder = holder as SendMessageViewHolder
                sendHolder.binding.tvMessageOut.text = message.text

            }

            receivedMessage -> {
                val receiveHolder = holder as ReceiveMessageViewHolder
                receiveHolder.binding.tvMessageIn.text = message.text
            }
        }

        // Set a click listener to show a confirmation dialog when a message is clicked
        holder.itemView.setOnClickListener {
            showConfirmationDialog(context, contactName, message.text)
        }
    }

    // Show a confirmation dialog for saving the message as a note
    private fun showConfirmationDialog(context: Context, name: String, message: String) {
        AlertDialog.Builder(context).setTitle("Save Note")
            .setMessage("Do you want to save this message as a note?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.insertNote(name, message)
                Toast.makeText(
                    context, "Saved!", Toast.LENGTH_SHORT
                ).show()
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }


}

