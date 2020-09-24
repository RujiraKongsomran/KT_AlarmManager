package com.rujirakongsomran.kt_alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rujirakongsomran.kt_alarmmanager.databinding.ActivityMainBinding
import de.mateware.snacky.Snacky
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setContentView(binding.root)

        init();

    }

    private fun init() {
        binding.btnTimer.setOnClickListener {
            setAlarm(5);
        }
    }

    private fun setAlarm(number: Int) {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val now = Calendar.getInstance()
        val calendarList = ArrayList<Calendar>()
        for (i in 1..number) {
            calendarList.add(now) // Add times
        }

        val textTimer = StringBuilder()
        for (calendar in calendarList) {
            // Each calendar, we will set alarm
            calendar.add(Calendar.SECOND, 10) // Next 10 sec
            val requestCode = Random().nextInt()
            val intent = Intent(this, MyAlarmReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            val pi = PendingIntent.getBroadcast(this, requestCode, intent, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
            else
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)

            textTimer.append(simpleDateFormat.format(calendar.timeInMillis)).append("\n")
        }

        binding.tvTimer.text = textTimer

        Snacky.builder()
            .setView(binding.root)
            .setText("Alarm has been set")
            .centerText()
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackgroundSnack))
            .setDuration(Snacky.LENGTH_LONG)
            .info()
            .show();

        //Toast.makeText(this, "Alarm has been set", Toast.LENGTH_SHORT).show()

    }
}