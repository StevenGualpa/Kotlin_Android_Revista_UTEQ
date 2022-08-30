package com.geek.kotlin_api_revistas

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CustomerAdapter_Volumenes constructor(context_: Context,
                                            val userList: ArrayList<Cs_Volumen>) : RecyclerView.Adapter<CustomerAdapter_Volumenes.ViewHolder>() {
    val context: Context = context_

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Volumenes.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_view_volumenes, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Volumenes.ViewHolder, i: Int) {

        viewHolder.itemid.text = userList[i].id
        viewHolder.itemdoi.text=userList[i].doi
        viewHolder.itemdate.text=userList[i].publicacion
        viewHolder.itemvolumen.text=userList[i].nombres
        Picasso.get().load(userList[i].urlavatar).into(viewHolder.itemportada);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemid: TextView
        var itemdoi: TextView
        var itemdate: TextView
        var itemportada: ImageView
        var itemvolumen: TextView
        init {
            itemid=itemView.findViewById(R.id.geek_item_volumen_id)
            itemdoi=itemView.findViewById(R.id.geek_item_volumen_doi)
            itemdate=itemView.findViewById(R.id.geek_item_volumen_date)
            itemvolumen=itemView.findViewById(R.id.geek_item_volumen_numero)
            itemportada=itemView.findViewById(R.id.geek_item_volumen_portada)

            //Enviar Datos
            itemView.setOnClickListener{ v: View ->
                var position: Int = getAdapterPosition()
 //               Snackbar.make(v, "Item Selecccionado $position    ${itemid.text}" ,  Snackbar.LENGTH_LONG).setAction("Actción", null).show()
                var i= Intent(context,activity_articulos::class.java)
                i.putExtra("dato_volumen", itemid.text)
                ContextCompat.startActivity(context, i, null)
            }
        }
    }



}