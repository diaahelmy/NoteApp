package com.example.note.veiwmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.note.DataBase.NoteRepository

class NoteViewModelFactor(val app:Application,private val noterepository: NoteRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {


            return NoteViewModel(app,noterepository) as T

    }

}