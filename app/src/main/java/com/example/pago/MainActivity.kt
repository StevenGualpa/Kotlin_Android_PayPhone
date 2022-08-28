package com.example.pago

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set

class MainActivity : AppCompatActivity(),AdapterView.OnItemClickListener {


    var Token =
        "SV8SkcxKFyBWmsB3ZsN-2dpfNK1WGZSw2Y-2p6QETUIE9B7qnb0oOU_xvhTnO_fxtjxpmpXXNxrGuL5xgdyf3LzcGUd2-O1DCrBn8R-COcFE3gDKu9xPzXDJIk9Y83Q003X-Cf-nnyeHG0gMZK966UMmd_O0qeaE2GvwwSwrt-5OV-OTx0suoZnPQlI9HVGhShW9vxqUx6lrnjN7f0Mo9a-3bqaWj1VWOPhOOrm69wyy8svwLayhsCE_C_i3mJfTtTDCxic1Cm56K5ak0KYBXOoUqgv_GTcMlu_2JThRzkv-NlyDgvI-pYsfgZsNHa1InaM9fulzh-vnR2vAduOcwxcqayc"
    var endPoiniapiRegions="https://pay.payphonetodoesposible.com/api/Regions"
    var endPointapiSale="https://pay.payphonetodoesposible.com/api/Sale"
    var endPoiniapiUsers="https://pay.payphonetodoesposible.com/api/Users/0993002048/region/593"

    //Codigos Regiones
    var itemscodigo: ArrayList<String> = ArrayList()
    lateinit var Txt_codigo :AutoCompleteTextView
    var adapterItem: ArrayAdapter<String>? = null

    lateinit var txtReferencia: TextInputEditText
    lateinit var txttelefono: TextInputEditText
    lateinit var txtMonto:TextInputEditText
    var selectcodigo:String? = null

    //Historial de Pagos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CargaRecyclerView()
        txtReferencia = findViewById<TextInputEditText>(R.id.inputReferencia)
        txttelefono=findViewById<TextInputEditText>(R.id.inputNumeroCelular)
        txtMonto=findViewById<TextInputEditText>(R.id.inputMonto)
        //Cargamos Regiones
        getRegions()
        adapterItem=ArrayAdapter(this,R.layout.list_item,itemscodigo)
        Txt_codigo.setAdapter(adapterItem)
        Txt_codigo.setOnItemClickListener(this)
        //Cargamos el Historial
CargaRecyclerView()
    }


    //Metodo para Consumir Api con Regiones
    fun getRegions() {
        Txt_codigo =findViewById<AutoCompleteTextView>(R.id.item_txt_codigos)
        val queue = Volley.newRequestQueue(this)
        val JsonArrayRq = object: JsonArrayRequest(Method.GET,endPoiniapiRegions,null,
            { response->
                for (i in 0  until response.length()) {
                    val Jbject: JSONObject = response.getJSONObject(i)
                    val Id: String = Jbject.getString("prefixNumber")
                    itemscodigo.add(Id)
                }
            },
            Response.ErrorListener { error-> MensajeLargo(error.toString())
                Log.d("TAG", "Vemoas que paaa ${error.toString()}")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer $Token")
                return headers
            }
        }
        queue.add(JsonArrayRq)
    }

    fun MensajeLargo(Mensaje: String) {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()
    }

    fun pagar(view: View?) {

        //Valores para Envio
        val phoneNumber: String? = txttelefono.text.toString()
        val countryCode: String? = selectcodigo
        val reference: String? = txtReferencia.text.toString()
        val clientTransactionId: String? = UUID.randomUUID().toString()
        val currency: String? = "USD"
        val amount = txtMonto.text.toString().toFloat()
        val valor = amount * 100
        val valorf = valor.toInt()


            val queue = Volley.newRequestQueue(this)

            var JsonPost = JSONArray(
                """[{"amount":$valorf,
        "amountWithoutTax":$valorf,
        "clientTransactionId":"$clientTransactionId",
        "countryCode":"$countryCode",
        "currency":"$currency",
        "phoneNumber":"$phoneNumber",
        "reference":"$reference"}]""".trimMargin()
            )
            val JsonArrayRq: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                endPointapiSale,
                JsonPost.getJSONObject(0),  // info del pago
                Response.Listener { response ->
                    try {
                        MensajeLargo("Pago Solicitado")
                        CargaRecyclerView()
                    } catch (e: JSONException) {
                        println(e.toString())
                    }
                },
                Response.ErrorListener { error -> println(error.toString()) }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = java.util.HashMap()
                    params["Authorization"] = "Bearer $Token"
                    return params
                }
            }
            queue.add<JSONObject>(JsonArrayRq)
    }
    //Salva el Codigo de la Region
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: String = p0?.getItemAtPosition(p2).toString()
        selectcodigo=item
    }

    fun CargaRecyclerView()
    {
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://my-json-server.typicode.com/StevenGualpa/Api_Historial/Historial"

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
                var ditemtransactionId= arrayListOf<String>()
                var ditememail= arrayListOf<String>()
                var ditemphoneNumber= arrayListOf<String>()
                var ditem_transactionStatus= arrayListOf<String>()
                var ditemamount = arrayListOf<String>()
                var ditemdate = arrayListOf<String>()
                var ditemreference = arrayListOf<String>()

                //Extraemos Elementos de eiquetas
                while (index<n)
                {
                    var elemento: JSONObject =str.getJSONObject(index)

                    ditemtransactionId.add(elemento.getString("transactionId"))
                    ditememail.add(elemento.getString("email"))
                    ditemphoneNumber.add(elemento.getString("phoneNumber"))
                    ditem_transactionStatus.add(elemento.getString("transactionStatus"))
                    ditemamount.add(elemento.getString("amount"))
                    ditemdate.add(elemento.getString("date"))
                    ditemreference.add(elemento.getString("reference"))
                    index++

                }
                //MensajeLargo(list_volumenes_id.toString())
                val recyclerView_ : RecyclerView =findViewById(R.id.RcviewHistorial)
                val adapter_=CustomerAdapter_Historial(ditemtransactionId,ditememail, ditemphoneNumber, ditem_transactionStatus, ditemamount, ditemdate, ditemreference)

                recyclerView_.layoutManager= LinearLayoutManager(this)
                recyclerView_.adapter=adapter_
            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }
}