package com.syntax_institut.whatssyntax.data.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.RoundedCornersTransformation
import com.syntax_institut.whatssyntax.R
import com.syntax_institut.whatssyntax.data.datamodels.Contact
import com.syntax_institut.whatssyntax.data.remote.BASE_URL
import com.syntax_institut.whatssyntax.databinding.ItemStatusBinding
import com.syntax_institut.whatssyntax.ui.StatusFragmentDirections


// Adapter class for the RecyclerView in the StatusFragment
class StatusAdapter(
    private var dataset: List<Contact>
) : RecyclerView.Adapter<StatusAdapter.ItemViewHolder>() {


    // ViewHolder class for the RecyclerView items
    inner class ItemViewHolder(val binding: ItemStatusBinding) : ViewHolder(binding.root)


    // Inflate the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // Return the size of the dataset
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val contactStatus = dataset[position]

        // Load contact image using a Coil library
        val imageUrl = "${BASE_URL}${contactStatus.image}".toUri()

        holder.binding.ivContact.load(imageUrl) {
            placeholder(android.R.drawable.ic_menu_crop)
            error(R.drawable.round_broken_image_25)
            transformations(RoundedCornersTransformation(30f))
        }

        // Set contact name
        holder.binding.tvContactName.text = contactStatus.name


        // Check if the status is available
        if (contactStatus.status != null) {

            // If status is available, make item clickable and set text color to black
            holder.binding.statusCard.setOnClickListener {
                val action = holder.binding.statusCard.findNavController()
                action.navigate(
                    StatusFragmentDirections.actionStatusFragmentToStatusDetailFragment(
                        contactStatus.id
                    )
                )
            }
            // Set text color to black and make the image fully visible
            holder.binding.tvContactName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
            // Make image fully visible
            holder.binding.ivContact.alpha = 1.0f
        } else {
            // If status is not available, make item not clickable, set text color to gray,
            // and reduce image visibility
            holder.itemView.isClickable = false
            holder.binding.tvContactName.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.grau
                )
            )
            // Reduce image visibility
            holder.binding.ivContact.alpha = 0.4f
        }
    }

}