package com.syntax_institut.whatssyntax.data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.data.datamodels.Note
import com.syntax_institut.whatssyntax.databinding.ItemNoteBinding


// Adapter class for displaying notes
class NoteAdapter(
    private val dataset: List<Note>,
    private val viewModel: MainViewModel,
    private val context: Context,
    ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    // ViewHolder for notes
    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {

        // Inflate the layout for note item
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }


    // Return the size of the dataset
    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = dataset[position]

        // Set note name and text to respective TextViews
        holder.binding.tvNoteName.text = note.name
        holder.binding.tvNoteText.text = note.text


        // Set click listener for delete button to delete the note
        holder.binding.btnDelete.setOnClickListener {
            viewModel.deleteNote(note)
            Toast.makeText(
                context,
                "Deleted!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}