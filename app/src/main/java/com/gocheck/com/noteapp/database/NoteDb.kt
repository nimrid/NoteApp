package com.gocheck.com.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDb : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object {
//      volatile means its immediately ready for usage in other classes
        @Volatile
        private var instance : NoteDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDb(context).also {
                instance = it
            }
        }

        private fun buildDb(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDb::class.java,
            "noteDatabase"
        ).build()

    }
}