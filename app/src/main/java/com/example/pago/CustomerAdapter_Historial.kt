package com.example.pago

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomerAdapter_Historial constructor(ditemtransactionId: List<String>,
                                            ditememail: List<String>,
                                            ditemphoneNumber: List<String>,
                                            ditem_transactionStatus: List<String>,
                                            ditemamount: List<String>,
                                            ditemdate: List<String>,
                                            ditemreference: List<String>): RecyclerView.Adapter<CustomerAdapter_Historial.ViewHolder>(){

    //Creamos los list con valores por defectos para luego cambiarlos con los datos de la Api
    val datos_itemtransactionId = ditemtransactionId
    val datos_itememail=ditememail
    val datos_itemphoneNumber=ditemphoneNumber
    val datos_item_transactionStatus=ditem_transactionStatus
    val datos_itemamount=ditemamount
    val datos_itemdate=ditemdate
    val datos_itemreference=ditemreference


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CustomerAdapter_Historial.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_viewd_historial, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: CustomerAdapter_Historial.ViewHolder, i: Int) {

        viewHolder.itemtransactionId.text = "Pago Nª"+datos_itemtransactionId.get(i)
        viewHolder.itememail.text="Email: "+datos_itememail.get(i)
        viewHolder.itemphoneNumber.text="Celular: "+datos_itemphoneNumber.get(i)
        viewHolder.itemtransactionStatus.text="Estado: "+datos_item_transactionStatus.get(i)
        viewHolder.itemamount.text="Monto: "+datos_itemamount.get(i)
        viewHolder.itemamount.text="Fecha: "+datos_itemdate.get(i)
        viewHolder.itemreference.text="Referencia: "+datos_itemreference.get(i)
    }

    override fun getItemCount(): Int {
        return datos_itemtransactionId.count()
    }



    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
       var itemtransactionId :TextView
       var itememail : TextView
       var itemphoneNumber : TextView
       var itemtransactionStatus : TextView
       var itemamount : TextView
       var itemdate : TextView
       var itemreference : TextView

        init {
            itemtransactionId=itemView.findViewById(R.id.geek_item_transactionId)
            itememail=itemView.findViewById(R.id.geek_item_email)
            itemphoneNumber=itemView.findViewById(R.id.geek_item_phoneNumber)
            itemtransactionStatus=itemView.findViewById(R.id.geek_item_transactionStatus)
            itemamount=itemView.findViewById(R.id.geek_item_amount)
            itemdate=itemView.findViewById(R.id.geek_item_date)
            itemreference=itemView.findViewById(R.id.geek_item_reference)
        }
    }
}