package br.edu.ifpb.pdm.permissioncharger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var imgCharger  :  ImageView
    private var chargerReceiver      :  ChargerReceiver? = null
    private lateinit var intentFilter:  IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.imgCharger = findViewById(R.id.imgCharger)
    }

    override fun onResume() {
        super.onResume()

        if (this.chargerReceiver == null) {
            this.chargerReceiver = ChargerReceiver()
            this.intentFilter    = IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
        }

        registerReceiver(this.chargerReceiver, this.intentFilter)
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(this.chargerReceiver)
    }

    inner class ChargerReceiver: BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)) {
                this@MainActivity.imgCharger.setImageResource(
                    resources.getIdentifier("charger", "drawable", packageName)
                )
            }
            else {
                this@MainActivity.imgCharger.setImageResource(
                    resources.getIdentifier("chargeroff", "drawable", packageName)
                )
            }
        }

    }
}