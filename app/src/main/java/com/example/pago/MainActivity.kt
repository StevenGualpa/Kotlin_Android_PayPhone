package com.example.pago

import android.media.session.MediaSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Header
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    var Token =
        "SV8SkcxKFyBWmsB3ZsN-2dpfNK1WGZSw2Y-2p6QETUIE9B7qnb0oOU_xvhTnO_fxtjxpmpXXNxrGuL5xgdyf3LzcGUd2-O1DCrBn8R-COcFE3gDKu9xPzXDJIk9Y83Q003X-Cf-nnyeHG0gMZK966UMmd_O0qeaE2GvwwSwrt-5OV-OTx0suoZnPQlI9HVGhShW9vxqUx6lrnjN7f0Mo9a-3bqaWj1VWOPhOOrm69wyy8svwLayhsCE_C_i3mJfTtTDCxic1Cm56K5ak0KYBXOoUqgv_GTcMlu_2JThRzkv-NlyDgvI-pYsfgZsNHa1InaM9fulzh-vnR2vAduOcwxcqayc"
    var endPoiniapiRegions="https://pay.payphonetodoesposible.com/api/Regions"
    var endPointapiSale="https://pay.payphonetodoesposible.com/api/Sale"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuario()

    }


    //Metodo para Consumir Api con Regiones
    private fun GetCodeRegions()
    {
        val queue = Volley.newRequestQueue(this)
        val JsonArrayRq = object: JsonArrayRequest(Method.GET,endPoiniapiRegions,null,
            { response->

                for (i in 0  until response.length()) {
                    val Jbject: JSONObject = response.getJSONObject(i)
                    val Id: String = Jbject.getString("prefixNumber")
                    MensajeLargo(Id)

                }
            },
            Response.ErrorListener { error-> MensajeLargo(error.toString())

                Log.d("TAG", "Veamos que paaa ${error.toString()}")
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












    fun usuario() {
        val queue = Volley.newRequestQueue(this)


        var Cadena: String = ""
        val JsonArrayRq = object: JsonArrayRequest(Method.GET,endPoiniapiRegions,null,
            { response->

                for (i in 0  until response.length()) {
                    val Jbject: JSONObject = response.getJSONObject(i)
                    val Id: String = Jbject.getString("prefixNumber")
                    MensajeLargo(Id)

                }
            },
            Response.ErrorListener { error-> MensajeLargo(error.toString())
                Log.d("TAG", "Vemoas que paaa ${error.toString()}")
            var t: TextView=findViewById(R.id.txtaaa)
                t.text = error.message
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



    fun MensajeLargo(Mensaje: String)
    {
        Toast.makeText(this, Mensaje.toString(), Toast.LENGTH_LONG).show()

    }
}