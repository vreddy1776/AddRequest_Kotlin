package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.arch.lifecycle.LiveData
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket


class TicketDetailPresenter{

    private var mLiveDataItem: LiveData<Ticket>? = null

    fun setupView(view : TicketDetailContract.View, itemId: Int) {

        mLiveDataItem = AppDatabase.getInstance().ticketDao().loadTicketById(itemId)
        mLiveDataItem!!.observeForever({ item ->

            view.updateText(item!!.itemName!!,
                    item.itemDescription!!)

            view.updateContent(item.itemUrl!!)

        })
    }

}