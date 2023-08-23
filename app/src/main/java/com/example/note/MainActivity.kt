package com.example.note

import android.R
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.note.DataBase.NoteDatabase
import com.example.note.DataBase.NoteRepository
import com.example.note.Fragment.NewNoteFragment
import com.example.note.databinding.ActivityMainBinding
import com.example.note.veiwmodel.NoteViewModel
import com.example.note.veiwmodel.NoteViewModelFactor
import java.util.Calendar
import java.util.Date


class MainActivity : AppCompatActivity() {

    private lateinit var dd:NewNoteFragment
    lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        createNotification()


    }

    fun scheduleNotification() {

        val intent = Intent(applicationContext, Notification::class.java)
//        val title = binding.etNotetitle.text.toString()
//        val message = binding.etnotebody.text.toString()


        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            123,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManger = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = dd.getTime()
        alarmManger.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        showAlter(time, "sadf", "dede")

    }
    fun showAlter(time: Long, title: String, message: String) {


        val data = Date(time)
        val dataformat = DateFormat.getLongDateFormat(applicationContext)

        val timeformat = DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification ")
            .setMessage(

                "Title: " + title + "\nMessage: " + message +
                        "\nAt:" + dataformat.format(data) + "   " + timeformat.format(data)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()


    }




    fun createNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notif Channel"
            val desc = "A Description of the Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("feaxandroid", name, importance)
            channel.description = desc
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))

        val viewModelProviderFactory = NoteViewModelFactor(application, noteRepository)
        noteViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            )
                .get(NoteViewModel::class.java)

    }


}