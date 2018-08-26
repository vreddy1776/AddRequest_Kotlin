package project.docs.files.template_kotlin

import android.content.Context
import org.json.JSONArray
import org.json.JSONException
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item

object SyncDatabase {

    private var mDb: AppDatabase? = null

    fun insertItems(jsonArray: JSONArray) {

        for (i in 0 until jsonArray.length()) {

            try {

                val jsonObject = jsonArray.getJSONObject(i)

                val item = Item(jsonObject.get("itemId") as Int,
                        jsonObject.get("itemName").toString(),
                        jsonObject.get("itemDescription").toString(),
                        jsonObject.get("itemDate").toString(),
                        jsonObject.get("itemUrl").toString())

                mDb?.getInstance()?.itemDao()?.insertItem(item)


                /*
                Thread({
                    AppDatabase.getInstance()?.itemDao()?.insertItem(item)
                }).start()
                */

                //AppDatabase.getInstance()?.itemDao()?.insertItem(item)


            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

    }

}