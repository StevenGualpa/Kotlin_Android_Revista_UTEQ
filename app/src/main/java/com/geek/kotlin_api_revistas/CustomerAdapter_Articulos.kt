package com.geek.kotlin_api_revistas

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomerAdapter_Articulos constructor(activity_: Activity,
                                            context_: Context,
                                            dmanager: DownloadManager,
                                            var userList: ArrayList<Cs_Articulo>,
                                            var notification: Notification) : RecyclerView.Adapter<CustomerAdapter_Articulos.ViewHolder>() {


    val context: Context = context_
    val acti: Activity=activity_
    val REQUESTED_PERMISSION_CODE=100
    val manager: DownloadManager=dmanager
    var downloadid: Long = 0


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Articulos.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_view_articulos, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Articulos.ViewHolder, i: Int) {

        viewHolder.itemid.text = userList[i].nombres
       // viewHolder.itemsection.text=userList[i].section
        viewHolder.itemtitle.text=userList[i].linkPD
      //  viewHolder.itemdoi.text=userList[i].doi
        viewHolder.itempublished.text=userList[i].publicacion
        viewHolder.itemdUrlViewGalley.text=userList[i].link

        Picasso.get().load(R.drawable.pdfimg).into(viewHolder.imageView2);
        Picasso.get().load(R.drawable.navgimg).into(viewHolder.imageView3);

        viewHolder.imageView3.setOnClickListener{v: View ->
            var i= Intent(context,activity_webview::class.java)
            i.putExtra("link", viewHolder.itemdUrlViewGalley.text)
            ContextCompat.startActivity(context, i, null)
        }
        viewHolder.imageView2.setOnClickListener{v: View ->
            BajarDoc(viewHolder.itemtitle.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return userList.count()
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemid: TextView
        //var itemsection: TextView
        var itemtitle: TextView
        //var itemdoi: TextView
        var itempublished: TextView
    //    var btndescarga: Button
     //   var btnview: Button

        var itemdUrlViewGalley: TextView
        var imageView2:ImageView
        var imageView3:ImageView
        init {
            itemid=itemView.findViewById(R.id.geek_item_articulo_id)
           // itemsection=itemView.findViewById(R.id.geek_item_articulo_section)
            itemtitle=itemView.findViewById(R.id.geek_item_articulo_title)
            //itemdoi=itemView.findViewById(R.id.geek_item_articulo_doi)
            itempublished=itemView.findViewById(R.id.geek_item_articulo_date_published)
           itemdUrlViewGalley=itemView.findViewById(R.id.geek_item_articulo_link)
            //btndescarga=itemView.findViewById(R.id.geek_item_articulo_BtnDescarga)
            //btnview=itemView.findViewById(R.id.geek_item_articulo_BtnView)
            imageView2=itemView.findViewById(R.id.imageView2)
            imageView3=itemView.findViewById(R.id.imageViewweb)



            //Enviar Datos

/*
            btndescarga.setOnClickListener{ v: View ->

                BajarDoc(itemtitle.text.toString())
            }
            btnview.setOnClickListener{ v: View ->
                var i= Intent(context,activity_webview::class.java)
                i.putExtra("link", itemdUrlViewGalley.text)
                ContextCompat.startActivity(context, i, null)
            }
 */
        }
    }

    fun BajarDoc(enlace: String) {


        val request =
//            DownloadManager.Request(Uri.parse("https://revistas.uteq.edu.ec/index.php/ingenio/article/download/7/5/15.pdf"))
            DownloadManager.Request(Uri.parse(enlace))
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

            val NotificationManager=NotificationManagerCompat.from(context.applicationContext)
            NotificationManager.notify(1, notification)

        } catch (e: Exception) {
            Toast.makeText(context.applicationContext, "Error: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(acti, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }

}
