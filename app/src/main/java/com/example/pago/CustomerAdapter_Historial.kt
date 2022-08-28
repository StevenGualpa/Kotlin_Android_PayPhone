package com.example.pago

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter_Historial constructor(did: List<String>,
                                            dsection: List<String>) : RecyclerView.Adapter<CustomerAdapter_Historial.ViewHolder>(){

    //Creamos los list con valores por defectos para luego cambiarlos con los datos de la Api
    val datos_id = did
    val datos_section=dsection

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Historial.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_viewd_historial, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Historial.ViewHolder, i: Int) {

        viewHolder.itemid.text = datos_id.get(i)
        viewHolder.itemsection.text=datos_section.get(i)
    }

    override fun getItemCount(): Int {
        return datos_id.count()
    }



    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        var itemid: TextView
        var itemsection: TextView

        init {
            itemid=itemView.findViewById(R.id.geek_item_articulo_titulo)
            itemsection=itemView.findViewById(R.id.geek_item_articulo_subtitulo)

        }
    }
}