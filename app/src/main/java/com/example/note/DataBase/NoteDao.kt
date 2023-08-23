package com.example.note.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes():LiveData <List<Note>>

    @Query("SELECT * FROM Notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
    fun searchNotes(query:String?):LiveData <List<Note>>
}