package com.example.bnccapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bnccapplication.hotline.ui.HotlineActivity
import com.example.bnccapplication.lookup.ui.LookupActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val extra = "Extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLookup = findViewById<Button>(R.id.btn_main_lookup)
        val btnHotline = findViewById<Button>(R.id.btn_main_hotline)
        btnLookup.setOnClickListener {
            openLookupActivity()
        }
        btnHotline.setOnClickListener {
            openHotlineActivity()
        }
    }
    private fun openLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(extra, "Message From MainActivity")
        }
        startActivity(intent)
    }
    private fun openHotlineActivity() {
        val intent = Intent(this, HotlineActivity::class.java).apply {
            putExtra(extra, "Message From MainActivity")
        }
        startActivity(intent)
    }
    private fun actionDial() {
        val phoneNumber = "404558"
        val intent = Intent().apply{
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
}