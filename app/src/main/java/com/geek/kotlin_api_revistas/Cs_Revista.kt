package com.geek.kotlin_api_revistas

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Cs_Revista(a: JSONObject) {
    var nombres: String
    var abreviacion: String
    var id: String
    var urlavatar: String

    companion object {
        @Throws(JSONException::class)
        fun JsonObjectsBuild(datos: JSONArray): ArrayList<Cs_Revista> {
            val revistas: ArrayList<Cs_Revista> = ArrayList<Cs_Revista>()
            var i = 0
            while (i < datos.length()) {
                revistas.add(Cs_Revista(datos.getJSONObject(i)))
                i++
            }
            return revistas
        }
    }

    init {
        nombres = a.getString("name").toString()
        abreviacion = a.getString("abbreviation").toString()
        id = a.getString("journal_id").toString()
        urlavatar = a.getString("portada").toString()
    }
}