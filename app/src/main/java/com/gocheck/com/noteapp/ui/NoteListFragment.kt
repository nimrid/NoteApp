package com.gocheck.com.noteapp.ui

import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.gocheck.com.noteapp.R
import com.gocheck.com.noteapp.adapter.NotesAdapter
import com.gocheck.com.noteapp.database.NoteDb
import kotlinx.android.synthetic.main.fragment_note_list.*
import kotlinx.coroutines.launch

class NoteListFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyler_view.setHasFixedSize(true)
        recyler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        addNewNote.setOnClickListener {
            val action = NoteListFragmentDirections.toNewNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }

        launch {
            context?.let {
                val notes = NoteDb.invoke(it).getNoteDao().getAllNotes()
                recyler_view.adapter =
                    NotesAdapter(notes)

            }
        }
    }

}
