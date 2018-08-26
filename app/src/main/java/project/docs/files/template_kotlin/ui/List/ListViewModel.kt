package project.docs.files.template_kotlin.ui.List

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.content.Context
import project.docs.files.template_kotlin.SyncVolley
import project.docs.files.template_kotlin.adapter.ItemAdapter
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item


class ListViewModel : ViewModel() {

    private val mLiveDataItemList: LiveData<List<Item>>? = null
    private lateinit var mItemsObserver: Observer<List<Item>>

    fun setup() {

        //AppExecuters.getInstance().diskIO().execute(Runnable { AppDatabase.getInstance().itemDao().deleteAllItems() })
        //AppDatabase.getDataBase().itemDao().deleteAllItems()
        /*
        Thread({
        }).start()
        */
        SyncVolley.apiRestCall()
        //mLiveDataItemList = AppDatabase.getInstance().itemDao().loadAllItems()

    }

    fun updateAdapter(itemAdapter: ItemAdapter) {
        mLiveDataItemList!!.observeForever({ itemList -> itemAdapter.setItems(itemList!!) })
    }


}


