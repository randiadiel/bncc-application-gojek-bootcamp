package com.example.bnccapplication.lookup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnccapplication.R
import com.example.bnccapplication.lookup.LookupData
import com.example.bnccapplication.lookup.adapter.LookupAdapter
import kotlinx.android.synthetic.main.activity_lookup.*

class LookupActivity : AppCompatActivity() {
    private val mockLookupList = mutableListOf<LookupData>(
        LookupData("DKI Jakarta",4566,4333,233),
        LookupData("Papua",4532,232,23),
        LookupData("Medan",333,33,2),
        LookupData("Jawa Barat",3234,32,1),
        LookupData("Jawa Tengah",1222,322,34),
        LookupData("Jawa Timur",1222,322,34),
        LookupData("Gorontalo",1222,322,34)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        val btnLookupBack = findViewById<Button>(R.id.btn_lookup_back)
        val etLookupSearch = findViewById<EditText>(R.id.et_lookup_search)
        btnLookupBack.setOnClickListener{
            onBackPressed()
        }
        etLookupSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterLookupList(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
        rv_lookup.layoutManager = LinearLayoutManager(this)
        modifyLookupAdapter(mockLookupList)
    }

    fun filterLookupList(query : String){
        val filteredList = mockLookupList.filter { it.provinceName.contains(query.toRegex()) }.toMutableList()
        modifyLookupAdapter(filteredList)
    }

    private fun modifyLookupAdapter(list : MutableList<LookupData>){
        val lookupAdapter = LookupAdapter(list)
        rv_lookup.adapter = lookupAdapter
    }
}