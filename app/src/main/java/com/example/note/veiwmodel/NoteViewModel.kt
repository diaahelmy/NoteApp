package com.example.note.veiwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.note.DataBase.Note
import com.example.note.DataBase.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository,
) : AndroidViewModel(app) {


    private val _minute = MutableLiveData<Long>()
    var minute: LiveData<Long> = _minute

    private val _hour = MutableLiveData<Long>()
    var hour: LiveData<Long> = _hour

    private val _day = MutableLiveData<Long>()
    var day: LiveData<Long> = _day

    private val _month = MutableLiveData<Long>()
    var month: LiveData<Long> = _month

    private val _years = MutableLiveData<Long>()
    var years: LiveData<Long> = _years

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)

    }


    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)

    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)

    }

    fun getAllNotes() = noteRepository.getALLNotes()
    fun searchNote(query: String) = noteRepository.searchNote(query)
}