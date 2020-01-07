package com.gocheck.com.noteapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gocheck.com.noteapp.R
import com.gocheck.com.noteapp.database.Note
import com.gocheck.com.noteapp.ui.NewNoteFragment
import com.gocheck.com.noteapp.ui.NoteListFragment
import com.gocheck.com.noteapp.ui.NoteListFragmentDirections
import kotlinx.android.synthetic.main.note_list.view.*

class NotesAdapter(private val notes : List<Note>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var  mActionMode : ActionMode? = null

    class NotesViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]
        holder.view.note_title.text = note.title
        holder.view.note_desc.text = note.note

        holder.view.setOnClickListener {
            val action = NoteListFragmentDirections.toNewNoteFragment()
            action.note = note
            Navigation.findNavController(it).navigate(action)
            mActionMode = null
        }

        holder.view.setOnLongClickListener{
            note.setSelected(!note.isSelected())
            holder.view.setBackgroundColor(
                if (note.isSelected())
                    Color.GRAY
                else
                    Color.WHITE
            )

            mActionMode = it.startActionMode(mAction)
            true

        }

    }

    //  contextual menu
    private val mAction: ActionMode.Callback =
        object : ActionMode.Callback {
            override fun onCreateActionMode(
                mode: ActionMode,
                menu: Menu
            ): Boolean {
                val inflater = mode.menuInflater
                inflater?.inflate(R.menu.context_menu, menu)
                return true
            }

            override fun onPrepareActionMode(
                mode: ActionMode,
                menu: Menu
            ): Boolean {
                menu.findItem(R.id.delete_selected)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                return true
            }

            override fun onActionItemClicked(
                mode: ActionMode,
                item: MenuItem
            ): Boolean {
                when(item.itemId){
                    R.id.delete_selected -> {
//                        toast("Deleted")
                    }
                }
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                mActionMode = null
            }
        }


}