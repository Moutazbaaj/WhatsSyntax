package com.syntax_institut.whatssyntax.data.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.R
import com.syntax_institut.whatssyntax.data.datamodels.Chat
import com.syntax_institut.whatssyntax.data.remote.BASE_URL
import com.syntax_institut.whatssyntax.databinding.ItemChatBinding
import com.syntax_institut.whatssyntax.ui.ChatsFragmentDirections


// Adapter class for Chat logs RecyclerView
class ChatAdapter(
    private val dataset: List<Chat>,
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<ChatAdapter.ItemViewHolder>() {


    // ViewHolder class for ChatAdapter
    inner class ItemViewHolder(val binding: ItemChatBinding) : ViewHolder(binding.root)


    // Inflate the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }


    // Return the size of the dataset
    override fun getItemCount(): Int {
        return dataset.size
    }


    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val chats = dataset[position]


        // Load contact image using a Coil library
        val imgUrl = "${BASE_URL}${chats.contact.image}".toUri()

        holder.binding.ivChatContactImage.load(imgUrl){
            placeholder(android.R.drawable.ic_menu_crop)
            error(R.drawable.round_broken_image_25)
            transformations(RoundedCornersTransformation(30f))
        }

        // Set contact name and last message
        holder.binding.tvChatContactName.text = chats.contact.name

        holder.binding.tvChatLastMess.text = chats.lastMessage.text


        // Set click listener to navigate to chat detail fragment when item is clicked
        holder.binding.cvChat.setOnClickListener{

            viewModel.loadMessage(chats.id)

            val action = holder.binding.cvChat.findNavController()
            action.navigate(ChatsFragmentDirections.actionChatsFragmentToChatDetailFragment(chats.id,chats.contact.name))

        }
    }


}