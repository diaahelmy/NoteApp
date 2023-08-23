package com.example.note.Fragment
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.note.DataBase.Note
import com.example.note.MainActivity
import com.example.note.R
import com.example.note.databinding.FragmentUpdateNoteBinding
import com.example.note.veiwmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note), MenuProvider {

    private lateinit var currentNote: Note
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!


    private lateinit var notesViewModel: NoteViewModel
    private val args: UpdateNoteFragmentArgs by navArgs()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
      _binding= FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this,viewLifecycleOwner)

        notesViewModel=(activity as MainActivity).noteViewModel
        currentNote=args.note!!

        binding.etNotetitleUpdate.setText(currentNote.noteTitle)
        binding.etnotebodyupdate.setText(currentNote.noteBody)

        //if user update thr note

        binding.fabDone.setOnClickListener {
            val title =binding.etNotetitleUpdate.text.toString().trim()
            val body= binding.etnotebodyupdate.text.toString().trim()

            if (title.isNotEmpty()){

                val note =Note(currentNote.id,title,body)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)

            }else{

                Toast.makeText(context,"please enter note title",Toast.LENGTH_SHORT).show()


            }


        }

    }
    private fun deleteNote(){

        AlertDialog.Builder(activity).apply {

            setTitle("Delete Note")
            setMessage("You want to delete this Note? ")
            setPositiveButton("Delete"){_,_->
                notesViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)



            }
            setNegativeButton("Cancel",null)

        }.create().show()

    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_update_note, menu)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_delete->{
//                deleteNote()
//            }
//
//
//        }
//
//
//        return super.onOptionsItemSelected(item)
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification() {
        val name = "Notif Channel"
        val desc ="A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("feaxandroid",name, importance)
        channel.description=desc
        val notificationManager=
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_update_note, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.menu_delete->{
                deleteNote()
            }


        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}