package project.docs.files.template_kotlin

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item

object SyncDatabase {

    private var mDb: AppDatabase? = null

    fun insertItems(jsonArray: JSONArray) {

        mDb = AppDatabase.getInstance()

        for (i in 0 until jsonArray.length()) try {

            val jsonObject = jsonArray.getJSONObject(i)

            val item = Item(jsonObject.get("itemId") as Int,
                    jsonObject.get("itemName").toString(),
                    jsonObject.get("itemDescription").toString(),
                    jsonObject.get("itemDate").toString(),
                    jsonObject.get("itemUrl").toString())

            Log.d("Test", "Inserted Item:  ${item.toString()}")



            Thread({
                mDb?.itemDao()?.insertItem(item)
            }).start()

            /*
            Thread({
                AppDatabase.getInstance()?.itemDao()?.insertItem(item)
            }).start()
            */

            //AppDatabase.getInstance()?.itemDao()?.insertItem(item)


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var res = mDb?.query("select * from Items", null)
        Log.d("Cursor", "Cursor; After Insert:  " + res!!.count)

    }

}