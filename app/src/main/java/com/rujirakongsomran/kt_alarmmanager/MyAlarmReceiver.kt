package com.rujirakongsomran.kt_alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val requestCode = p1!!.getIntExtra("REQUEST_CODE", -1)
        Toast.makeText(p0!!, "Alarm fired with request code$requestCode", Toast.LENGTH_SHORT)
            .show()
    }

}
