package com.example.bindimaps.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bindimaps.R
import com.example.bindimaps.adapter.SessionAdapter
import com.example.bindimaps.model.MainUserSessionsItem
import com.example.bindimaps.service.ServiceBuilder
import com.example.bindimaps.service.UserService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionListActivity : AppCompatActivity() {

    var adapter: SessionAdapter? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)

        initAdapter()
        loadSessions()

    }

    private fun initAdapter() {
        val recyclerview = findViewById<RecyclerView>(R.id.rvSession)
        recyclerview.layoutManager = LinearLayoutManager(this)
            progressBar = findViewById(R.id.pBar)
            progressBar?.visibility = View.VISIBLE

        adapter = SessionAdapter(SessionAdapter.OnClickListener {

            val intent = Intent(this@SessionListActivity, VenuesActivity::class.java)
            intent.putExtra("mUserPath", Gson().toJson(it.path).toString())
            startActivity(intent)

        })

        recyclerview.adapter = adapter
    }

    private fun loadSessions() {
        val destinationService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = destinationService.getSessions()
        requestCall.enqueue(object : Callback<List<MainUserSessionsItem>> {
            override fun onResponse(
                call: Call<List<MainUserSessionsItem>>,
                response: Response<List<MainUserSessionsItem>>
            ) {
                progressBar?.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        adapter?.setData(list)
                    }
                } else {
                    Toast.makeText(
                        this@SessionListActivity,
                        response.errorBody().toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<MainUserSessionsItem>>, t: Throwable) {
                Log.e("!_@_Error_", t.toString())
                progressBar?.visibility = View.INVISIBLE
                Toast.makeText(
                    this@SessionListActivity,
                    "Something went wrong", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}