package com.geek.kotlin_api_revistas

import android.app.DownloadManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class activity_articulos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articulos)

        val bundle=intent.extras
        probandoVolley(bundle?.getString("dato_volumen").toString())
        val txtresul = findViewById<TextView>(R.id.lbl_titulo_articulos)
        txtresul.text = bundle?.getString("name")

    }


    fun probandoVolley(parm: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        //val url: String = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=1"
        val url: String = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id="+parm

        //txtresul.text=parm
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var lsyAriculos = ArrayList<Cs_Articulo>()
                var strResp = response.toString()
                var str: JSONArray = JSONArray(strResp)

                lsyAriculos = Cs_Articulo.JsonObjectsBuild(str)
                VisualizaCardview_(lsyAriculos)

            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }




    private fun VisualizaCardview_(userList: ArrayList<Cs_Articulo>)
    {
        val recyclerView_ : RecyclerView =findViewById(R.id.recycler_articulos)
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


        val adapter_=CustomerAdapter_Articulos(this,this,manager,userList)

        recyclerView_.layoutManager= LinearLayoutManager(this)
        recyclerView_.adapter=adapter_

        val resId = R.anim.layout_animation_down_to_up
        val animation = AnimationUtils.loadLayoutAnimation(
            applicationContext,
            resId
        )
        recyclerView_.layoutAnimation = animation

    }

    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }
}