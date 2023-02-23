package com.example.bindimaps.activity

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bindimaps.R
import com.example.bindimaps.model.MainVenuesItemItem
import com.example.bindimaps.model.Path
import com.example.bindimaps.service.ServiceBuilder
import com.example.bindimaps.service.UserService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_venues.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*
import kotlin.random.Random


class VenuesActivity : AppCompatActivity() {

    private var surfaceView: SurfaceView? = null
    private var surfaceHolder: SurfaceHolder? = null
    var mListUserPath: String? = null
    var sessionList: List<Path>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues)

        surfaceView = findViewById(R.id.surfaceView)
        surfaceHolder = surfaceView?.holder

        mListUserPath = (intent.getStringExtra("mUserPath") ?: "")
        val listType: Type = object : TypeToken<List<Path>>() {}.type
        sessionList = Gson().fromJson<List<Path>>(mListUserPath, listType)

        loadVenues()
    }

    fun drawVenusAndSession(mListVenus: List<MainVenuesItemItem>?) {
        val x = ArrayList<Float>()
        val y = ArrayList<Float>()
        val clr = ArrayList<Int>()
        val tempClr = intArrayOf(
            Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED,
            Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN
        )
        mListVenus?.forEachIndexed { index, mainVenuesItemItem ->
            x.add(mainVenuesItemItem.position.x.toFloat())
            y.add(mainVenuesItemItem.position.y.toFloat())
            clr.add(tempClr[Random.nextInt(0, 11)])
        }

        val paint = Paint()
        paint.color = Color.GREEN
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        val canvas = surfaceHolder?.lockCanvas()
        canvas?.drawColor(Color.GRAY)
        // for dots
        for (i in x.indices) {
            paint.color = Color.RED
            canvas?.drawCircle(x[i], y[i], 5f, paint)
        }

        // for lines
        if (sessionList?.isNotEmpty() == true) {
            sessionList?.let {
                val path = Path()
                path.moveTo(it[0].position.x.toFloat(), it[0].position.x.toFloat())
                for (i in 1 until it.size) {
                    paint.color = Color.GREEN
                    path.lineTo(it[i].position.x.toFloat() * 2, it[i].position.y.toFloat() * 2)
                }
                canvas?.drawPath(path, paint)
            }
        }
        surfaceHolder?.unlockCanvasAndPost(canvas)
    }

    private fun loadVenues() {
        val destinationService = ServiceBuilder.buildService(UserService::class.java)
        val requestCall = destinationService.getVenues()
        requestCall.enqueue(object : Callback<List<MainVenuesItemItem>> {
            override fun onResponse(
                call: Call<List<MainVenuesItemItem>>,
                response: Response<List<MainVenuesItemItem>>
            ) {
                if (response.isSuccessful) {
                    drawVenusAndSession(response.body())
                } else {
                    Toast.makeText(
                        this@VenuesActivity,
                        response.errorBody().toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<MainVenuesItemItem>>, t: Throwable) {
                Log.e("!_@_Error_", t.toString())
                Toast.makeText(
                    this@VenuesActivity,
                    "Something went wrong", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}