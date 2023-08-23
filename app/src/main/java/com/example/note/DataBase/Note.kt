package com.example.note.DataBase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "Notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo
    val noteTitle:String,
    val noteBody:String



):Parcelable