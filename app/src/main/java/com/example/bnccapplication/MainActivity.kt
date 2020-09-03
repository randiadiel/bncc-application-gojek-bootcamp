package com.example.bnccapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLookup = findViewById<Button>(R.id.btn_lookup)
        btnLookup.setOnClickListener {
            openLookupActivity()
        }
    }
    private fun openSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("Extras", "Hello SecondActivity from MainActivity")
        }
    }
    private fun openLookupActivity() {
        val intent = Intent(this,LookupActivity::class.java)
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