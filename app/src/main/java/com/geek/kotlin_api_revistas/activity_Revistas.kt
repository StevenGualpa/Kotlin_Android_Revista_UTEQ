package com.geek.kotlin_api_revistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class activity_Revistas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revistas)
            probandoVolley()
    }


    fun probandoVolley() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://revistas.uteq.edu.ec/ws/journals.php"
        val txtresul = findViewById<TextView>(R.id.lbl_titulo_revistas)
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var lsyRevista = ArrayList<Cs_Revista>()

                var strResp = response.toString()
                var str: JSONArray = JSONArray(strResp)
                lsyRevista = Cs_Revista.JsonObjectsBuild(str)

                VisualizaCardview_(lsyRevista)
            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }


    private fun VisualizaCardview_(userList: ArrayList<Cs_Revista>)
    {
        val a= Intent(this, activity_Volumenes::class.java)
        val recyclerView_ : RecyclerView =findViewById(R.id.recycler_revista)
        val adapter_=CustomerAdapter_Revistas(this,a,userList)

        recyclerView_.layoutManager= LinearLayoutManager(this)
        recyclerView_.adapter=adapter_
        val resId = R.anim.layout_animation_down_to_up
        val animation = AnimationUtils.loadLayoutAnimation(
            applicationContext,
            resId
        )
        recyclerView_.layoutAnimation = animation
    }


}