package project.docs.files.addrequest_kotlin

import org.json.JSONArray
import org.json.JSONException
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket

object SyncDatabase {

    private var mDb: AppDatabase? = null

    fun insertItems(jsonArray: JSONArray) {

        mDb = AppDatabase.getInstance()

        for (i in 0 until jsonArray.length()) try {

            val jsonObject = jsonArray.getJSONObject(i)

            val item = Ticket(jsonObject.get("itemId") as Int,
                    jsonObject.get("itemName").toString(),
                    jsonObject.get("itemDescription").toString(),
                    jsonObject.get("itemDate").toString(),
                    jsonObject.get("itemUrl").toString())

            Thread({
                mDb?.ticketDao()?.insertTicket(item)
            }).start()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}