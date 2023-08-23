package com.example.note.DataBase


class NoteRepository(private val db: NoteDatabase) {
    suspend fun insertNote(Note: Note) = db.getNoteDao().insertNote(Note)
    suspend fun deleteNote(Note: Note) = db.getNoteDao().deleteNote(Note)
    suspend fun updateNote(Note: Note) = db.getNoteDao().updateNote(Note)

    fun getALLNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String) = db.getNoteDao().searchNotes(query)
}