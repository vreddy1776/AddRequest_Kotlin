package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.arch.lifecycle.LiveData
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket


class TicketDetailPresenter{

    private var mLiveDataItem: LiveData<Ticket>? = null

    fun setupView(view : TicketDetailContract.View, ticketId: Int) {

        mLiveDataItem = AppDatabase.getInstance().ticketDao().loadTicketById(ticketId)
        mLiveDataItem!!.observeForever({ ticket ->

            view.updateText(ticket!!.ticketTitle!!,
                    ticket.ticketDescription!!)

            view.updateContent(ticket.userPhotoUrl!!)

        })
    }

}