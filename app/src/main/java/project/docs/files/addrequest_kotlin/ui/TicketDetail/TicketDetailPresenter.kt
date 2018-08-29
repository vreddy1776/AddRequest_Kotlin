package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.arch.lifecycle.LiveData
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket
import project.docs.files.addrequest_kotlin.utils.C


class TicketDetailPresenter{

    private var mLiveDataItem: LiveData<Ticket>? = null
    var tempTicket = Ticket()

    fun setupView(view : TicketDetailContract.View, ticketType: Int , ticketId: Int) {

        mLiveDataItem = AppDatabase.getInstance().ticketDao().loadTicketById(ticketId)
        mLiveDataItem!!.observeForever({ ticket ->

            if(ticketType != C.ADD_TICKET_TYPE){
                tempTicket.setTicket(ticket!!)
                view.updateText(ticket.ticketTitle!!, ticket.ticketDescription!!)
                view.updateContent(ticket.userPhotoUrl!!)
            }

        })
    }

}