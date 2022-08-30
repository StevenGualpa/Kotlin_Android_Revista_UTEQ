package com.geek.kotlin_api_revistas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CustomerAdapter_Revistas constructor(context_: Context,
                                           dato: Intent,
                                           val userList: ArrayList<Cs_Revista>) : RecyclerView.Adapter<CustomerAdapter_Revistas.ViewHolder>()
{
    val context: Context = context_

    //Creamos los list con valores por defectos para luego cambiarlos con los datos de la Api
    var datos = dato

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Revistas.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_view_revistas, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Revistas.ViewHolder, i: Int) {

        viewHolder.itemid.text = userList[i].id
        viewHolder.itemname.text = userList[i].nombres
        viewHolder.itemabreviatura.text=userList[i].abreviacion
        Picasso.get().load(userList[i].urlavatar).into(viewHolder.itemportada);
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemid: TextView
        var itemname: TextView
        var itemabreviatura: TextView
        var itemportada: ImageView

        init {
            itemid=itemView.findViewById(R.id.geek_item_revista_id)
            itemname=itemView.findViewById(R.id.geek_item_revista_name)
            itemabreviatura=itemView.findViewById(R.id.geek_item_revista_abreviatura)
            itemportada=itemView.findViewById(R.id.geek_item_revista_portada)

            //Enviar Datos
            itemView.setOnClickListener{ v: View ->
                var position: Int = getAdapterPosition()
                //Snackbar.make(v, "Item Selecccionado $position    ${itemid.text}" , Snackbar.LENGTH_LONG).setAction("Actción", null).show()
                var i=Intent(context,activity_Volumenes::class.java)
                i.putExtra("dato_revista", itemid.text)
                    startActivity(context,i, null)
            }
        }

    }



                                           }