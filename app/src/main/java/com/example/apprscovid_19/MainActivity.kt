package com.example.apprscovid_19

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.apprscovid_19.hospital.Hospital
import com.example.apprscovid_19.hospital.HospitalAdapter
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hospitalAdapter: HospitalAdapter
    private val hospitals: MutableList<Hospital> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        hospitalAdapter = HospitalAdapter(hospitals) { hospital ->
            openMap(hospital.province)
        }
        recyclerView.adapter = hospitalAdapter

        fetchData()
    }

    private fun fetchData() {
        val url = "https://dekontaminasi.com/api/id/covid19/hospitals"

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                parseData(response)
            },
            { error ->
                error.printStackTrace()
            })

        Volley.newRequestQueue(this).add(request)
    }

    private fun parseData(response: JSONArray) {
        for (i in 0 until response.length()) {
            try {
                val jsonObject = response.getJSONObject(i)
                val hospital = Hospital(
                    jsonObject.getString("name"),
                    jsonObject.getString("address"),
                    jsonObject.getString("region"),
                    jsonObject.getString("phone"),
                    jsonObject.getString("province")
                )
                hospitals.add(hospital)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        hospitalAdapter.notifyDataSetChanged()
    }

    private fun openMap(province: String) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("province", province)
        startActivity(intent)
    }
}