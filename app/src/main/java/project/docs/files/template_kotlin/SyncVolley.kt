package project.docs.files.template_kotlin

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import project.docs.files.template_kotlin.application.MyApplication

object SyncVolley {

    private const val MAIN_URL = "https://api.myjson.com/bins/6lm44"

    fun apiRestCall() {

        var jsonArray : JSONArray? = null

        val request = JsonArrayRequest(Request.Method.GET, MAIN_URL, JSONArray(),
                Response.Listener {
                    response -> SyncDatabase.insertItems(response)
                },
                Response.ErrorListener { }
        )
        val requestQueue = Volley.newRequestQueue(MyApplication.appContext)
        requestQueue.add(request)

    }


}