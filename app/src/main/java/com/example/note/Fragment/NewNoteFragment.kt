package com.example.note.Fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.note.DataBase.Note
import com.example.note.MainActivity
import com.example.note.R
import com.example.note.adapter.NoteAdapter
import com.example.note.databinding.FragmentNewNoteBinding
import com.example.note.veiwmodel.NoteViewModel
import java.util.Calendar
import java.util.Date
import android.text.format.DateFormat
import androidx.core.content.ContextCompat.getSystemService


class NewNoteFragment : Fragment(R.layout.fragment_new_note), MenuProvider {
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var mView: View
    private var ff: MainActivity? = null
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotification()
//     setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotification()

        binding.donenew.setOnClickListener {
        ff?.scheduleNotification()

        }
        activity?.addMenuProvider(this, viewLifecycleOwner)

        notesViewModel = (activity as MainActivity).noteViewModel

        mView = view

    }



//     fun scheduleNotification() {
//
//        val intent = Intent(requireContext().applicationContext, Notification::class.java)
//        val title = binding.etNotetitle.text.toString()
//        val message = binding.etnotebody.text.toString()
//
//
//        val pendingIntent = PendingIntent.getBroadcast(
//            requireContext().applicationContext,
//         123,
//            intent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val alarmManger = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val time = getTime()
//        alarmManger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
//        showAlter(time, title, message)
//
//    }

     fun showAlter(time: Long, title: String, message: String) {


        val data = Date(time)
        val dataformat = DateFormat.getLongDateFormat(requireContext().applicationContext)

        val timeformat = DateFormat.getTimeFormat(requireContext().applicationContext)

        AlertDialog.Builder(requireContext())
            .setTitle("Notification ")
            .setMessage(

                "Title: " + title + "\nMessage: " + message +
                        "\nAt:" + dataformat.format(data) + "   " + timeformat.format(data)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()


    }

     fun getTime(): Long {

        val minute = binding.timepickernew.minute
        val hour = binding.timepickernew.hour
        val day = binding.datepickernew.dayOfMonth
        val month = binding.datepickernew.month
        val years = binding.datepickernew.year
        val calender = Calendar.getInstance()
        calender.set(years, month, day, hour, minute)
        return calender.timeInMillis

    }


     fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name:CharSequence = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("feaxandroid", name, importance)
            channel.description = desc
//line below error
            val notificationManager = getSystemService(requireContext(), NotificationManager::class.java)
//----------------
            notificationManager?.createNotificationChannel(channel)
        }

    }


    private fun savenote(view: View) {
        val noteTitle = binding.etNotetitle.text.toString().trim()
        val noteBody = binding.etnotebody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)
            notesViewModel.addNote(note)
            Toast.makeText(mView.context, "Note Saved Successfully", Toast.LENGTH_SHORT).show()

            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(mView.context, "Please enter note Title", Toast.LENGTH_SHORT).show()


        }


    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        menu.clear()
        menuInflater.inflate(R.menu.menu_newnote, menu)


    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_save -> {
                savenote(mView)
//                scheduleNotification()
            }

            R.id.menu_notification -> {

                visible()

//                createNotification()
            }

        }

        return true
    }

    private fun visible() {

        if (binding.datepickernew.visibility == View.VISIBLE) {
            createNotification()
            binding.datepickernew.visibility = View.GONE
            binding.timepickernew.visibility = View.GONE
            binding.donenew.visibility = View.GONE

        } else {

            binding.datepickernew.visibility = View.VISIBLE
            binding.timepickernew.visibility = View.VISIBLE
            binding.donenew.visibility = View.VISIBLE


        }
    }

    //

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
//
//        inflater.inflate(R.menu.menu_newnote,menu)
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.menu_save->{
//                savenote(mView)
//            }
//
//        }
//
//        return super.onOptionsItemSelected(item)
//    }