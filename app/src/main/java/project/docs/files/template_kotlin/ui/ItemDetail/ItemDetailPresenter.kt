package project.docs.files.template_kotlin.ui.ItemDetail

import android.arch.lifecycle.LiveData
import project.docs.files.template_kotlin.data.AppDatabase
import project.docs.files.template_kotlin.data.Item


class ItemDetailPresenter{

    private var mLiveDataItem: LiveData<Item>? = null

    fun setupView(view : ItemDetailContract.View, itemId: Int) {

        mLiveDataItem = AppDatabase.getInstance().itemDao().loadItemById(itemId)
        mLiveDataItem!!.observeForever({ item ->

            view.updateText(item!!.itemName!!,
                    item.itemDescription!!)

            view.updateContent(item.itemUrl!!)

        })
    }

}