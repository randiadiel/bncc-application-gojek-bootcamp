package com.example.bnccapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bnccapplication.hotline.HotlineData
import com.example.bnccapplication.hotline.adapter.HotlineAdapter
import com.example.bnccapplication.hotline.ui.HotlineActivity
import com.example.bnccapplication.lookup.ui.LookupActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_hotline.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        const val extra = "Extra"
    }
    private val okHttpClient = OkHttpClient()

    private val mockHotlineList = mutableListOf<HotlineData>(
        HotlineData("","","Loading...")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnLookup = findViewById<Button>(R.id.btn_main_lookup)
        val btnHotline = findViewById<Button>(R.id.btn_main_hotline)
        val tvTotalCases = findViewById<TextView>(R.id.tv_main_total_cases)
        val tvTotalConfirmed = findViewById<TextView>(R.id.tv_main_confirmed_cases)
        val tvTotalRecovered = findViewById<TextView>(R.id.tv_main_recovered_cases)
        val tvTotalDeath = findViewById<TextView>(R.id.tv_main_death_cases)
        val btnHotlineCancel = findViewById<Button>(R.id.btn_main_hotline_cancel)
        val hotlineAdapter = HotlineAdapter(mockHotlineList)
        rv_main_hotline.layoutManager = LinearLayoutManager(this)
        rv_main_hotline.adapter = hotlineAdapter

        val request : Request = Request.Builder()
            .url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()
        val mainRequest : Request = Request.Builder()
            .url("https://api.kawalcorona.com/indonesia")
            .build()

        okHttpClient.newCall(mainRequest).enqueue(getMainCallback(tvTotalCases, tvTotalConfirmed, tvTotalRecovered, tvTotalDeath))
        okHttpClient.newCall(request).enqueue(getCallback(hotlineAdapter))
        btnLookup.setOnClickListener {
            openLookupActivity()
        }
        btnHotline.setOnClickListener {
            openHotlineActivity()
        }
        btnHotlineCancel.setOnClickListener() {
            closeBottomSheet()
        }
    }
    private fun openLookupActivity() {
        val intent = Intent(this, LookupActivity::class.java).apply {
            putExtra(extra, "Message From MainActivity")
        }
        startActivity(intent)
    }
    private fun openHotlineActivity() {
//        val intent = Intent(this, HotlineActivity::class.java).apply {
//            putExtra(extra, "Message From MainActivity")
//        }
//        startActivity(intent)
        val bottomSheet = findViewById<View>(R.id.cl_bottom_sheet)
        val bottomSheetBehavior : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val viewBg = findViewById<View>(R.id.bg)
        bottomSheetBehavior.addBottomSheetCallback(getBottomSheetCallback())
        if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED;
            viewBg.visibility = View.VISIBLE
        }else{
            closeBottomSheet()
        }
        viewBg.setOnClickListener() {
            closeBottomSheet()
        }
    }
    private fun actionDial(phoneNumber : String = "911") {
        val intent = Intent().apply{
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
    private fun closeBottomSheet(){
        val bottomSheet = findViewById<View>(R.id.cl_bottom_sheet)
        val bottomSheetBehavior : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val viewBg = findViewById<View>(R.id.bg)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;
        viewBg.visibility = View.INVISIBLE
    }
    private fun getBottomSheetCallback() : BottomSheetBehavior.BottomSheetCallback {
        val bottomSheet = findViewById<View>(R.id.cl_bottom_sheet)
        return object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset == 0f) closeBottomSheet()
                else if (slideOffset == 1f) bottomSheet.background = ContextCompat.getDrawable(applicationContext, R.drawable.rectangle)
                else bottomSheet.background = ContextCompat.getDrawable(applicationContext, R.drawable.layout_bg)
            }
        }
    }
    private fun getMainCallback(tvTotalCases: TextView, tvTotalConfirmed: TextView, tvTotalRecovered: TextView, tvTotalDeath: TextView) : Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@MainActivity.runOnUiThread {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val totalCases = jsonArray.getJSONObject(0).getString("positif")
                    val confirmedCases = jsonArray.getJSONObject(0).getString("dirawat")
                    val recoveredCases = jsonArray.getJSONObject(0).getString("sembuh")
                    val deathCases = jsonArray.getJSONObject(0).getString("meninggal")
                    this@MainActivity.runOnUiThread() {
                        tvTotalCases.text = totalCases
                        tvTotalConfirmed.text = confirmedCases
                        tvTotalRecovered.text = recoveredCases
                        tvTotalDeath.text = deathCases
                    }
                } catch (e: Exception){
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }
    private fun getCallback(hotlineAdapter:HotlineAdapter) : Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@MainActivity.runOnUiThread {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    this@MainActivity.runOnUiThread() {
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }
                } catch (e: Exception){
                    this@MainActivity.runOnUiThread {
                        Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }
}