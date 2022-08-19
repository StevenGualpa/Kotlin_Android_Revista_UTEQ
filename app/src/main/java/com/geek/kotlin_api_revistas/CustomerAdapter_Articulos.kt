package com.geek.kotlin_api_revistas

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomerAdapter_Articulos constructor(activity_: Activity,
                                            context_: Context,
                                            did: List<String>,
                                            dsection: List<String>,
                                            dtitle: List<String>,
                                            ddoi: List<String>,
                                            ddate_published: List<String>,
                                            dUrlViewGalley : List<String>,
                                            dmanager: DownloadManager) : RecyclerView.Adapter<CustomerAdapter_Articulos.ViewHolder>() {


    val context: Context = context_
    val acti: Activity=activity_
    val REQUESTED_PERMISSION_CODE=100
    val manager: DownloadManager=dmanager
    var downloadid: Long = 0

    //Creamos los list con valores por defectos para luego cambiarlos con los datos de la Api
    val datos_id = did
    val datos_section=dsection
    val datos_title=dtitle
    val datos_doi = ddoi
    val datos_published=ddate_published
    val datos_UrlViewGalley= dUrlViewGalley


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Articulos.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_view_articulos, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Articulos.ViewHolder, i: Int) {

        viewHolder.itemid.text = datos_id.get(i)
        viewHolder.itemsection.text=datos_section.get(i)
        viewHolder.itemtitle.text=datos_title.get(i)
        viewHolder.itemdoi.text=datos_doi.get(i)
        viewHolder.itempublished.text=datos_published.get(i)
        viewHolder.itemdUrlViewGalley.text=datos_UrlViewGalley.get(i)
    }

    override fun getItemCount(): Int {
        return datos_id.count()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemid: TextView
        var itemsection: TextView
        var itemtitle: TextView
        var itemdoi: TextView
        var itempublished: TextView
        var btndescarga: Button
        var itemdUrlViewGalley: TextView

        init {
            itemid=itemView.findViewById(R.id.geek_item_articulo_id)
            itemsection=itemView.findViewById(R.id.geek_item_articulo_section)
            itemtitle=itemView.findViewById(R.id.geek_item_articulo_title)
            itemdoi=itemView.findViewById(R.id.geek_item_articulo_doi)
            itempublished=itemView.findViewById(R.id.geek_item_articulo_date_published)
            itemdUrlViewGalley=itemView.findViewById(R.id.geek_item_articulo_link)
            btndescarga=itemView.findViewById(R.id.geek_item_articulo_BtnDescarga)

            //Enviar Datos

            btndescarga.setOnClickListener{ v: View ->

                //    Snackbar.make(v,"Descargas el Archivo",Snackbar.LENGTH_LONG).setAction("Accion",null).show()
                //               Snackbar.make(v, "Item Selecccionado $position    ${itemid.text}" ,  Snackbar.LENGTH_LONG).setAction("Actción", null).show()
               // solicittarPermiso()
               // MensajeLargo(itemdUrlViewGalley.text.toString())
                BajarDoc(itemdUrlViewGalley.text.toString())
            }
        }
    }
    //Permisos

    fun BajarDoc(enlace: String) {


        val request =  DownloadManager.Request(Uri.parse("https://revistas.uteq.edu.ec/index.php/ingenio/article/view/7/5.pdf"))
            .setDescription("Download PDF")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle("Download Pdf")
            .setAllowedOverMetered(true)
            .setVisibleInDownloadsUi(true)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalFilesDir(context.applicationContext, Environment.DIRECTORY_DOWNLOADS,"downloadfile.pdf")



        try {
            downloadid = manager.enqueue(request)
            context.registerReceiver(MyBroadcastReceiver(downloadid), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        } catch (e: Exception) {
            Toast.makeText(context.applicationContext, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }


    fun solicittarPermiso()
    {
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Permiso Concedido", Toast.LENGTH_LONG).show()
        }
        else{
            ActivityCompat.requestPermissions( acti,arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUESTED_PERMISSION_CODE)
        }
    }


    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(acti, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }







}
class MyBroadcastReceiver(var downloadid: Long) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (id == downloadid)
            Toast.makeText(context,  "Download Done!! "+downloadid.toString() ,Toast.LENGTH_LONG).show()
    }
}

class AdministradorPermisos(var context: Context) {

    fun getPermisosNoAprobados(listaPermisos: ArrayList<String?>): ArrayList<String?> {
        val list = ArrayList<String?>()
        for (permiso in listaPermisos) {
            if (Build.VERSION.SDK_INT >= 23)
                if (context.checkSelfPermission(permiso!!) != PackageManager.PERMISSION_GRANTED)
                    list.add(permiso)
        }
        return list
    }

    fun getPermisosAprobados(listaPermisos: ArrayList<String?>): ArrayList<String?> {
        val list = ArrayList<String?>()
        for (permiso in listaPermisos) {
            if (Build.VERSION.SDK_INT >= 23)
                if (context.checkSelfPermission(permiso!!) == PackageManager.PERMISSION_GRANTED)
                    list.add(permiso)
        }
        return list
    }

    fun getPermission(permisosSolicitados: ArrayList<String?>) {
        if(permisosSolicitados.size>0)
            if (Build.VERSION.SDK_INT >= 23)
                ActivityCompat.requestPermissions(context as Activity, permisosSolicitados.toArray(arrayOfNulls(permisosSolicitados.size)),1)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,grantResults: IntArray):String  {
        var s = ""
        if (requestCode == 1) {
            for (i in permissions.indices) {
                s+= if(grantResults[i] == PackageManager.PERMISSION_GRANTED)"Permitido: " else "Denegado: "
                s+=" " + permissions[i] + "\n"
            }
        }
        return s
    }

}