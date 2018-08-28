package project.docs.files.addrequest_kotlin.ui.TicketList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import project.docs.files.addrequest_kotlin.SyncVolley
import project.docs.files.addrequest_kotlin.adapter.TicketAdapter
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket


class TicketListViewModel : ViewModel() {

    private var mLiveDataItemList: LiveData<List<Ticket>>? = null
    private var mDb: AppDatabase? = null


    fun setup() {

        mDb = AppDatabase.getInstance()

        Thread({
            mDb?.ticketDao()?.deleteAllTickets()
        }).start()

        SyncVolley.apiRestCall()

        mLiveDataItemList = mDb?.ticketDao()?.loadAllTickets()

    }

    fun updateAdapter(ticketAdapter: TicketAdapter) {
        mLiveDataItemList!!.observeForever({ ticketList -> ticketAdapter.setTicketList(ticketList!!) })
    }


}


