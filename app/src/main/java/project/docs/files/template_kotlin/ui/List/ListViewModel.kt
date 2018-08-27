package project.docs.files.template_kotlin.ui.List

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import project.docs.files.template_kotlin.SyncVolley
import project.docs.files.template_kotlin.adapter.ItemAdapter
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item
import android.arch.persistence.room.RoomMasterTable.TABLE_NAME
import android.util.Log


class ListViewModel : ViewModel() {

    private var mLiveDataItemList: LiveData<List<Item>>? = null
    private lateinit var mItemsObserver: Observer<List<Item>>

    private var mDb: AppDatabase? = null


    fun setup() {

        mDb = AppDatabase.getInstance()

        //AppExecuters.getInstance().diskIO().execute(Runnable { AppDatabase.getInstance().itemDao().deleteAllItems() })
        //AppDatabase.getDataBase().itemDao().deleteAllItems()

        var res = mDb?.query("select * from Items", null)
        Log.d("Cursor", "Cursor; Before Delete:  " + res!!.count)


        Thread({
            mDb?.itemDao()?.deleteAllItems()
        }).start()

        res = mDb?.query("select * from Items", null)
        Log.d("Cursor", "Cursor; After Delete:  " + res!!.count)


        /*
        Thread({
        }).start()
        */
        SyncVolley.apiRestCall()


        //mLiveDataItemList = AppDatabase.getInstance().itemDao().loadAllItems()

        mLiveDataItemList = mDb?.itemDao()?.loadAllItems()
        Log.d("Test", "mLiveDataItemList:  ${mLiveDataItemList!!.value.toString()}")


    }

    fun updateAdapter(itemAdapter: ItemAdapter) {
        mLiveDataItemList!!.observeForever({ itemList -> itemAdapter.setItems(itemList!!) })
        /*
        mLiveDataItemList?.observeForever({
            fun onChanged(itemList : List<Item>) {
                itemAdapter.setItems(itemList)
            }
        })
        */
    }


}


