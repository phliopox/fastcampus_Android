package com.ian.fastcam.chap9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.ian.fastcam.TAG

class LowBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "LowBatteryReceiver - onReceive: ${intent.action}");
        when(intent.action){
            Intent.ACTION_BATTERY_LOW ->{
                Toast.makeText(context,"배터리가 부족합니다",Toast.LENGTH_SHORT)
            }
        }
    }
}