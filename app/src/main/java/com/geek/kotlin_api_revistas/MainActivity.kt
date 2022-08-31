package com.geek.kotlin_api_revistas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.NotificationParams
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    //Controles Sesion
    var txtusuario:  TextInputLayout? = null
    var txtpassword:  TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imagelogo: ImageView = findViewById(R.id.image_login)

        Picasso.get()
            .load("https://www.uteq.edu.ec/images/page/4/l_uteq.png")
            .into(imagelogo);

        var boton: Button = findViewById(R.id.BtnEnviar)

        boton.setOnClickListener {
            /*
            val intent = Intent(this, activity_Revistas::class.java)
            startActivity(intent)


         */
            probandoVolley()
        }


    }

    fun notification(titu: String,descrp:String) {

        val chanelid="chat"
        val chanelname="chat"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val importancia=NotificationManager.IMPORTANCE_HIGH
            val channel=NotificationChannel(channelID, channelName,importancia)

            //Manager de Notificacion
            val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val notification=NotificationCompat.Builder(this, channelID).also {noti->
                noti.setContentTitle(titu)
                noti.setContentText(descrp)
                noti.setSmallIcon(R.drawable.bienvenido)
            }.build()

            val NotificationManager=NotificationManagerCompat.from(applicationContext)
            NotificationManager.notify(1, notification)
        }

    }


    fun probandoVolley() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)


        //Entran Datos
        txtusuario = findViewById(R.id.sesion_txt_usuario)
        txtpassword = findViewById(R.id.sesion_txt_password)
        val usuario :String= txtusuario!!.editText!!.text.toString()
        val password :String=txtpassword!!.editText!!.text.toString()

        val url: String =
            "https://revistas.uteq.edu.ec/ws/loginojs.php?usr=$usuario&correo=$password"

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                var strResp = "[" + response.toString() + "]"
                var str: JSONArray = JSONArray(strResp)
                var elemento: JSONObject = str.getJSONObject(0)
                if (elemento.getString("mensaje").contains("Usuario Correcto")) {
                   // MensajeLargo("Bienvenido")
                    notification("Bienvenido","A esta aplicacion")
                    val intent = Intent(this, activity_Revistas::class.java)
                    startActivity(intent)
                } else {
                    notification("Error","Credenciales incorrectas")
                }

            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }

    fun MensajeLargo(Mensaje: String) {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }


}

