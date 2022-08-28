package com.example.pago

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(),AdapterView.OnItemClickListener {


    var Token =
        "SV8SkcxKFyBWmsB3ZsN-2dpfNK1WGZSw2Y-2p6QETUIE9B7qnb0oOU_xvhTnO_fxtjxpmpXXNxrGuL5xgdyf3LzcGUd2-O1DCrBn8R-COcFE3gDKu9xPzXDJIk9Y83Q003X-Cf-nnyeHG0gMZK966UMmd_O0qeaE2GvwwSwrt-5OV-OTx0suoZnPQlI9HVGhShW9vxqUx6lrnjN7f0Mo9a-3bqaWj1VWOPhOOrm69wyy8svwLayhsCE_C_i3mJfTtTDCxic1Cm56K5ak0KYBXOoUqgv_GTcMlu_2JThRzkv-NlyDgvI-pYsfgZsNHa1InaM9fulzh-vnR2vAduOcwxcqayc"
    var endPoiniapiRegions="https://pay.payphonetodoesposible.com/api/Regions"
    var endPointapiSale="https://pay.payphonetodoesposible.com/api/Sale"
    var endPoiniapiUsers="https://pay.payphonetodoesposible.com/api/Users/0993002048/region/593"

    //Codigos Regiones
    var itemscodigo: ArrayList<String> = ArrayList()
    lateinit var txtreg :AutoCompleteTextView
    var adapterItem: ArrayAdapter<String>? = null

    lateinit var inputReferencia: TextInputEditText
    lateinit var inputtelefono: TextInputEditText
    lateinit var inputMonto:TextInputEditText
    var itemcodigo:String? = null

    //Historial de Pagos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputReferencia = findViewById<TextInputEditText>(R.id.inputReferencia)
        inputtelefono=findViewById<TextInputEditText>(R.id.inputNumeroCelular)
        inputMonto=findViewById<TextInputEditText>(R.id.inputMonto)
        //Cargamos Regiones
        getRegions()
        adapterItem=ArrayAdapter(this,R.layout.list_item,itemscodigo)
        txtreg.setAdapter(adapterItem)
        txtreg.setOnItemClickListener(this)
        //Cargamos el Historial

    }


    //Metodo para Consumir Api con Regiones
    fun getRegions() {
        txtreg =findViewById<AutoCompleteTextView>(R.id.item_list_code)
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
    fun validanumero(): Boolean{

        return false
    }

    fun pagar(view: View?) {

        val phoneNumber: String? = inputtelefono.text.toString()
        val countryCode: String? = itemcodigo
        val reference: String? = inputReferencia.text.toString()
        val amount = 0
        val amountWithoutTax = 0
        val clientTransactionId: String? = null
        val currency: String? = "USD"

        val queue = Volley.newRequestQueue(this)
        var credenciales = JSONArray(
            """[{"amount":1000,
        "amountWithoutTax":1000,
        "clientTransactionId":"""+UUID.randomUUID().toString()+"""",
        "countryCode":"""+countryCode+""",
        "currency":"""+currency+""",
        "phoneNumber":"""+phoneNumber+""",
        "reference":"""+reference+"""}]""".trimMargin()
        )
        val pagarPayPhone: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            "https://pay.payphonetodoesposible.com/api/Sale",
            credenciales.getJSONObject(0),  // info del pago
            Response.Listener { response ->
                try {
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
        queue.add<JSONObject>(pagarPayPhone)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: String = p0?.getItemAtPosition(p2).toString()
        itemcodigo=item
        //MensajeLargo(item)
    }
}