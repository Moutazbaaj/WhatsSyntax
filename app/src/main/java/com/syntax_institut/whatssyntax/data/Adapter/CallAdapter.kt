package com.syntax_institut.whatssyntax.data.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax_institut.whatssyntax.R
import com.syntax_institut.whatssyntax.data.datamodels.Calls
import com.syntax_institut.whatssyntax.data.datamodels.Chat
import com.syntax_institut.whatssyntax.data.remote.BASE_URL
import com.syntax_institut.whatssyntax.databinding.ItemCallBinding


// Adapter class for Call logs RecyclerView
class CallAdapter(
    private val dataset: List<Calls>
) : RecyclerView.Adapter<CallAdapter.ItemViewHolder>() {


    // ViewHolder class for CallAdapter
    inner class ItemViewHolder(val binding: ItemCallBinding) : ViewHolder(binding.root)


    // Inflate the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return dataset.size
    }


    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val call = dataset[position]


        // Load contact image using a Coil library
        val imageUrl = "${BASE_URL}${call.contact.image}".toUri()

        holder.binding.tvCallContactImage.load(imageUrl) {
            placeholder(android.R.drawable.ic_menu_crop)
            error(R.drawable.round_broken_image_25)
            transformations(RoundedCornersTransformation(30f))
        }

        // Set contact name and call time
        holder.binding.tvCallName.text = call.contact.name
        holder.binding.tvCallTime.text = call.time


        // Determine rotation and image resource based on a call type
        val rotation: Float
        val imageResource: Int

        when {
            call.incoming && call.accepted -> {
                rotation = 180f
                imageResource = R.drawable.icon_call_accepted
            }

            !call.incoming && call.accepted -> {
                rotation = 0f
                imageResource = R.drawable.icon_call_accepted
            }

            call.incoming && !call.accepted -> {
                rotation = 180f
                imageResource = R.drawable.icon_call_missed
            }

            else -> {
                rotation = 0f
                imageResource = R.drawable.icon_call_missed
            }
        }

        // Set rotation and image resource for call icon
        holder.binding.ivCallStatus.rotation = rotation
        holder.binding.ivCallStatus.setImageResource(imageResource)


        // Set click listener to initiate a phone call
        holder.binding.cvCall.setOnClickListener {
            val phoneNumber = call.contact.number
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }
}