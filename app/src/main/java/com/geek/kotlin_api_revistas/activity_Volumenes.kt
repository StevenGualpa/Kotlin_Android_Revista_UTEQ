package com.geek.kotlin_api_revistas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class activity_Volumenes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volumenes)

        val bundle=intent.extras
        probandoVolley(bundle?.getString("dato_revista").toString())
        var num=bundle?.getString("dato_revista").toString()
        var toolbar : Toolbar?= findViewById(R.id.toolbar3);
        if(num=="1"){
            toolbar!!.title=("  Revista InGenio")
            }
        else if(num=="2"){
            toolbar!!.title=("  Ciencia y Tecnología")
            }
        else if(num=="3"){
            toolbar!!.title=("  Ciencias Sociales y Económicas")
           }
        toolbar?.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar);
    }

    fun probandoVolley(parm: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        //val url: String = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=1"
        val url: String = "https://revistas.uteq.edu.ec/ws/issues.php?j_id="+parm

        //txtresul.text=parm
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var lsyVolumen = ArrayList<Cs_Volumen>()
                var strResp = response.toString()
                var str: JSONArray = JSONArray(strResp)
                lsyVolumen = Cs_Volumen.JsonObjectsBuild(str)

                VisualizaCardview_(lsyVolumen)

            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }

    private fun VisualizaCardview_(userList: ArrayList<Cs_Volumen>)
    {
        val recyclerView_ : RecyclerView =findViewById(R.id.recycler_volumenes)
        val adapter_=CustomerAdapter_Volumenes(this, userList)

        recyclerView_.layoutManager= LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView_.adapter=adapter_

        val resId = R.anim.layout_animation_down_to_up
        val animation = AnimationUtils.loadLayoutAnimation(
            applicationContext,
            resId
        )
        recyclerView_.layoutAnimation = animation
    }
}