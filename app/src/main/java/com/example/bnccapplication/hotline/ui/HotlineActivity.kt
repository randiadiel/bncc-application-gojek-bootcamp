package com.example.bnccapplication.hotline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnccapplication.R
import com.example.bnccapplication.hotline.HotlineData
import com.example.bnccapplication.hotline.adapter.HotlineAdapter
import kotlinx.android.synthetic.main.activity_hotline.*
import kotlinx.android.synthetic.main.activity_lookup.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class HotlineActivity : AppCompatActivity() {
    private val okHttpClient = OkHttpClient()

    private val mockHotlineList = mutableListOf<HotlineData>(
        HotlineData("","","Loading...")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotline)
        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rv_hotline.layoutManager = LinearLayoutManager(this)
        rv_hotline.adapter = hotlineAdapter

        val request : Request = Request.Builder()
            .url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()

        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
    }

    private fun getCallback(hotlineAdapter:HotlineAdapter) : Callback{
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@HotlineActivity.runOnUiThread {
                    Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork = mutableListOf<HotlineData>()

                    for(i in 0 until jsonArray.length()) {
                        hotlineListFromNetwork.add(
                            HotlineData(
                                imgIcon = jsonArray.getJSONObject(i).getString("img_icon"),
                                name = jsonArray.getJSONObject(i).getString("name"),
                                phone = jsonArray.getJSONObject(i).getString("phone")
                            ))
                    }
                    this@HotlineActivity.runOnUiThread() {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }
                } catch (e: Exception){
                    this@HotlineActivity.runOnUiThread {
                        Toast.makeText(this@HotlineActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }
}