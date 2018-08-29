package project.docs.files.addrequest_kotlin.ui.TicketList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import project.docs.files.addrequest_kotlin.SyncVolley
import project.docs.files.addrequest_kotlin.adapter.TicketAdapter
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket


class TicketListViewModel : ViewModel() {

    private var mLiveDataTicketList: LiveData<List<Ticket>>? = null

    fun setup() {

        Thread({
            AppDatabase.getInstance().ticketDao().deleteAllTickets()
        }).start()

        SyncVolley.apiRestCall()

        mLiveDataTicketList = AppDatabase.getInstance().ticketDao().loadAllTickets()

    }

    fun updateAdapter(ticketAdapter: TicketAdapter) {
        mLiveDataTicketList!!.observeForever({ ticketList -> ticketAdapter.setTicketList(ticketList!!) })
    }


}


