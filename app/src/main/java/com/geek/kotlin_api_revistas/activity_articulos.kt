package com.geek.kotlin_api_revistas

import android.app.DownloadManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class activity_articulos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articulos)

        val bundle=intent.extras
        probandoVolley(bundle?.getString("dato_volumen").toString())
    }


    fun probandoVolley(parm: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        //val url: String = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=1"
        val url: String = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id="+parm

        val txtresul = findViewById<TextView>(R.id.lbl_titulo_articulos)
        //txtresul.text=parm
        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var strResp = response.toString()
                var str: JSONArray = JSONArray(strResp)

                //Contador
                var index=0
                //Cantidad de Elementos
                var n=str.length()
                //Listas que usaremos
                var list_articulo_id = arrayListOf<String>()
                var list_articulo_section = arrayListOf<String>()
                var list_articulo_title = arrayListOf<String>()
                var list_articulo_doi = arrayListOf<String>()
                var list_articulo_date_published = arrayListOf<String>()
                var list_articulo_dUrlViewGalley = arrayListOf<String>()


                //Extraemos Elementos de eiquetas
                //var elemento: JSONObject =str.getJSONObject(index)

                while (index<n)
                {
                    var elemento: JSONObject =str.getJSONObject(index)

                    list_articulo_id.add(elemento.getString("publication_id"))
                    list_articulo_section.add(elemento.getString("section"))
                    list_articulo_title.add(elemento.getString("title"))
                    list_articulo_doi.add(elemento.getString("doi"))
                    list_articulo_date_published.add(elemento.getString("date_published"))
                    var str2: JSONArray = JSONArray(elemento.getString("galeys"))
                    var elemento2: JSONObject =str2.getJSONObject(0)
                    list_articulo_dUrlViewGalley.add(elemento2.getString("UrlViewGalley"))
                    //MensajeLargo(elemento2.getString("UrlViewGalley"))
                    index++
                }
                VisualizaCardview_(list_articulo_id,list_articulo_section,list_articulo_title,list_articulo_doi,list_articulo_date_published,list_articulo_dUrlViewGalley)

            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }




    private fun VisualizaCardview_(list1: List<String>,
                                   list2: List<String>,
                                   list3: List<String>,
                                   list4: List<String>,
                                   list5: List<String>,
                                   list6: List<String>)
    {
        val recyclerView_ : RecyclerView =findViewById(R.id.recycler_articulos)
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager


        val adapter_=CustomerAdapter_Articulos(this,this, list1,list2,list3,list4,list5,list6,manager)

        recyclerView_.layoutManager= LinearLayoutManager(this)
        recyclerView_.adapter=adapter_

    }

    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }
}