package com.example.bnccapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LookupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        val btnLookupBack = findViewById<Button>(R.id.btn_lookup_back)
        val tvLookupContent = findViewById<TextView>(R.id.tv_lookup_content)
        btnLookupBack.setOnClickListener{
            openMainActivity()
        }
        tvLookupContent.setText(intent.getStringExtra("extra"))
    }

    private fun openMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}