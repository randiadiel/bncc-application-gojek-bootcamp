package com.example.bnccapplication.lookup.ui

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnccapplication.R
import com.example.bnccapplication.hotline.HotlineData
import com.example.bnccapplication.lookup.LookupData
import com.example.bnccapplication.lookup.adapter.LookupAdapter
import kotlinx.android.synthetic.main.activity_lookup.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class LookupActivity : AppCompatActivity() {
    private val mockLookupList = mutableListOf<LookupData>(
        LookupData("Loading...",0,0,0)
    )
    private val okHttpClient = OkHttpClient()

    private fun modifyLookupAdapter(lookupAdapter: LookupAdapter, newList: MutableList<LookupData>){
        lookupAdapter.updateData(newList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        val btnLookupBack = findViewById<Button>(R.id.btn_lookup_back)
        val etLookupSearch = findViewById<EditText>(R.id.et_lookup_search)
        val btnLookupCancel = findViewById<Button>(R.id.btn_lookup_cancel)
        val lookupAdapter = LookupAdapter(mockLookupList)
        rv_lookup.layoutManager = LinearLayoutManager(this)
        rv_lookup.adapter = lookupAdapter
        val request : Request = Request.Builder()
            .url("https://api.kawalcorona.com/indonesia/provinsi/")
            .build()
        okHttpClient.newCall(request).enqueue(getCallback(lookupAdapter))

        btnLookupBack.setOnClickListener{
            onBackPressed()
        }
        btnLookupCancel.setOnClickListener {
            etLookupSearch.setText("")
        }
        etLookupSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filterLookupList(p0.toString(),lookupAdapter)
                if(p0?.toString().equals("")){
                    btnLookupCancel.visibility = View.INVISIBLE
                }
                else{
                    btnLookupCancel.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    fun filterLookupList(query : String, lookupAdapter: LookupAdapter){
        val filteredList = mockLookupList.filter { it.provinceName.contains(query.toRegex()) }.toMutableList()
        modifyLookupAdapter(lookupAdapter, filteredList)
    }

    fun getCallback(lookupAdapter: LookupAdapter) : Callback{
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@LookupActivity.runOnUiThread() {
                    Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val lookupListFromNetwork = mutableListOf<LookupData>()

                    for (i in 0 until jsonArray.length()){
                        lookupListFromNetwork.add(i, LookupData(
                            provinceName = jsonArray
                                .getJSONObject(i)
                                .getJSONObject("attributes")
                                .getString("Provinsi"),
                            numberOfConfirmedCases = jsonArray
                                .getJSONObject(i)
                                .getJSONObject("attributes")
                                .getString("Kasus_Posi").toInt(),
                            numberOfRecoveredCases = jsonArray
                                .getJSONObject(i)
                                .getJSONObject("attributes")
                                .getString("Kasus_Semb").toInt(),
                            numberOfDeathCases = jsonArray
                                .getJSONObject(i)
                                .getJSONObject("attributes")
                                .getString("Kasus_Meni").toInt()
                        ))
                    }
                    this@LookupActivity.runOnUiThread {
                        modifyLookupAdapter(lookupAdapter, lookupListFromNetwork)
                    }
                }
                catch (e: Exception){
                    this@LookupActivity.runOnUiThread {
                        Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}