package com.example.note.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note.DataBase.Note
import com.example.note.Fragment.HomeFragmentDirections
import com.example.note.databinding.NoteLayoutBinding
import java.util.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteBody == newItem.noteBody &&
                    oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.tvNotetitle.text = currentNote.noteTitle
        holder.itemBinding.tvNotebody.text = currentNote.noteBody

        val random = Random()
        val color = android.graphics.Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)

        )

        if (position % 3 == 0) {
            holder.itemBinding.colorsize.setBackgroundColor(Color.parseColor("#dfca4d")) // Pink
        } else if (position % 3 == 1) {
            holder.itemBinding.colorsize.setBackgroundColor(Color.parseColor("#00B894")) // Light Green
        } else {
            holder.itemBinding.colorsize.setBackgroundColor(Color.parseColor("#00FFFF")) // Blue
        }
        holder.itemBinding.ibColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            it.findNavController().navigate(direction)

        }


    }
}