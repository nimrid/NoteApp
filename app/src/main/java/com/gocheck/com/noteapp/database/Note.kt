package com.gocheck.com.noteapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    val title: String,
    val note: String
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    private var isSelected = false

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

}