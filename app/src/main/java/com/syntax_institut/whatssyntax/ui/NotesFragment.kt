package com.syntax_institut.whatssyntax.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntax_institut.whatssyntax.MainViewModel
import com.syntax_institut.whatssyntax.data.Adapter.NoteAdapter
import com.syntax_institut.whatssyntax.databinding.FragmentNotesBinding


// Fragment for displaying notes
class NotesFragment: Fragment() {

    // Binding object for the fragment
    private lateinit var binding: FragmentNotesBinding

    // Viewmodel for the fragment
    private val viewModel: MainViewModel by activityViewModels()


    // Inflate the layout for the fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }


    // Initialize views and observe LiveData
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe note data changes and update UI accordingly
        viewModel.note.observe(viewLifecycleOwner){
            binding.rvNotes.adapter = NoteAdapter(it,viewModel,requireContext())
        }


        // Observe complete event and unset it
        viewModel.complete.observe(viewLifecycleOwner){
            if (it)
                viewModel.unsetComplete()
        }



        // Delete all notes when the deleted button is clicked
        binding.btnDelete.setOnClickListener {
            // Build an AlertDialog
            AlertDialog.Builder(requireContext())
                .setTitle("Delete All Notes")
                .setMessage("Are you sure you want to delete all notes?")
                .setPositiveButton("Yes") { dialog, _ ->
                    // If user confirms, delete all notes
                    viewModel.deleteAllNotes()
                    Toast.makeText(requireContext(), "All notes are Deleted!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss() // Dismiss the dialog
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    // If a user cancels, do nothing
                    dialog.dismiss() // Dismiss the dialog
                }
                .show() // Show the AlertDialog
        }

    }


}