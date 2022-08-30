package com.geek.kotlin_api_revistas

import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Cs_Articulo(a: JSONObject) {
    var nombres: String
    var publicacion: String
    var id: String
    var img: Int
    var link:String
    var linkhtml:String
    var linkPD:String
    var section: String
    var doi:String

    companion object {
        @Throws(JSONException::class)
        fun JsonObjectsBuild(datos: JSONArray): ArrayList<Cs_Articulo> {
            val publicacion: ArrayList<Cs_Articulo> = ArrayList<Cs_Articulo>()
            var i = 0
            while (i < datos.length()) {
                publicacion.add(Cs_Articulo(datos.getJSONObject(i)))
                i++
            }
            return publicacion
        }
    }

    init {
        nombres = a.getString("title").toString()
        publicacion = a.getString("date_published").toString()
        id = a.getString("publication_id").toString()
        img=R.drawable.icono_download
        var asd=a.getJSONArray("galeys")
        link=asd.getJSONObject(0).getString("UrlViewGalley")
        linkhtml=asd.getJSONObject(0).getString("UrlViewGalley")
        section=a.getString("section").toString()
        linkPD=""
        doi=a.getString("doi").toString()

        //Armar URL PDF
        var revista1="csye"
        var revista2="cyt"
        var revista3="ingenio"
        var submission_id=a.getString("submission_id").toString()
        var galley_id=asd.getJSONObject(0).getString("galley_id")
        var file_id=asd.getJSONObject(0).getString("file_id")
        var label=asd.getJSONObject(0).getString("label")
        var preUrl="https://revistas.uteq.edu.ec/index.php/"

        if(link.contains(revista1)){
            preUrl=preUrl+ revista1+"/article/download/$submission_id/$galley_id/$file_id"+".pdf"
        }
        else if(link.contains(revista2)){
            preUrl=preUrl+revista2+"/article/download/$submission_id/$galley_id/$file_id"+".pdf"
        }
        else if(link.contains(revista3)){
            preUrl=preUrl+revista3+"/article/download/$submission_id/$galley_id/$file_id"+".pdf"
        }
        else{preUrl="que paso"}
        linkPD=preUrl

    }

}