package com.gocheck.com.noteapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation

import com.gocheck.com.noteapp.R
import com.gocheck.com.noteapp.database.Note
import com.gocheck.com.noteapp.database.NoteDb
import com.gocheck.com.noteapp.util.snackBar
import com.gocheck.com.noteapp.util.toast
import kotlinx.android.synthetic.main.fragment_new_note.*
import kotlinx.coroutines.launch

class NewNoteFragment : BaseFragment() {
    private var note : Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_new_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//      receiving the note arg from list
        arguments?.let {
            note = NewNoteFragmentArgs.fromBundle(it).note
            title.setText(note?.title)
            noteText.setText(note?.note)
        }

        saveButton.setOnClickListener {view ->

            val noteTitle = title.text.toString()
            val noteBody = noteText.text.toString()

            if (noteTitle.isEmpty()){
                title.error = "Title required"
                title.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()){
                noteText.error = "Note required"
                noteText.requestFocus()
                return@setOnClickListener
            }

            launch {
                val mNotes = Note(noteTitle, noteBody)
                context?.let {
                    if(note == null){
                        NoteDb.invoke(it).getNoteDao().addNewNote(mNotes)
                        it.snackBar(view, "Note Saved")
                        val action = NewNoteFragmentDirections.toNoteListFragment()
                        Navigation.findNavController(view).navigate(action)
                    }else {
                        mNotes.id = note!!.id
                        NoteDb.invoke(it).getNoteDao().updateNote(mNotes)
                        it.snackBar(view, "Note Updated")
                    }

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share_note ->
                if (note != null)
                    shareNote()
            R.id.delete ->
                if (note != null)
                    delete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete(){
        AlertDialog.Builder(context!!).apply {
            setTitle("Delete Note")
            setMessage("Are you sure?")
            setPositiveButton("Yes"){_, _ ->
                launch {
                    NoteDb.invoke(context).getNoteDao().deleteNote(note!!)
                    val action = NewNoteFragmentDirections.toNoteListFragment()
                    Navigation.findNavController(view!!).navigate(action)
                    context.toast("Note deleted")
                }
            }
            setNegativeButton("No"){_, _ ->

            }

        }.create().show()
    }

//    share note
     private fun shareNote(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, note?.note)
        startActivity(intent)
    }
}
