package project.docs.files.template_kotlin.ui.List

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import project.docs.files.template_kotlin.SyncVolley
import project.docs.files.template_kotlin.adapter.ItemAdapter
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item


class ListViewModel : ViewModel() {

    private var mLiveDataItemList: LiveData<List<Item>>? = null

    private var mDb: AppDatabase? = null


    fun setup() {

        mDb = AppDatabase.getInstance()

        Thread({
            mDb?.itemDao()?.deleteAllItems()
        }).start()

        SyncVolley.apiRestCall()

        mLiveDataItemList = mDb?.itemDao()?.loadAllItems()

    }

    fun updateAdapter(itemAdapter: ItemAdapter) {
        mLiveDataItemList!!.observeForever({ itemList -> itemAdapter.setItems(itemList!!) })
    }


}


