package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.arch.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase
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


    /**
     * Calls two threads - one for adding ticket to local DB other to remote DB.
     *
     * @param ticket The ticket to be added.
     * @param ticketType View, Add, or Update ticket type for AddTicketActivity session.
     */
    fun addTicketToDb(ticketType: Int) {

        addTicketToLocalDb(ticketType)
        addTicketToFirebaseDb()
    }


    /**
     * Adds ticket to local (SQLlite) DB.
     *
     * @see .addTicketToDb
     * @param ticket The ticket to be added.
     * @param ticketType View, Add, or Update ticket type for AddTicketActivity session.
     */
    fun addTicketToLocalDb(ticketType: Int) {

        if (ticketType == C.ADD_TICKET_TYPE) {
            Thread(Runnable { AppDatabase.getInstance().ticketDao().insertTicket(tempTicket) }).start()
        } else {
            Thread(Runnable { AppDatabase.getInstance().ticketDao().updateTicket(tempTicket) }).start()
        }
    }


    /**
     * Adds ticket to remote (Firebase) DB.
     *
     * @see .addTicketToDb
     * @param ticket The ticket to be added.
     */
    private fun addTicketToFirebaseDb() {

        val fBdatabase = FirebaseDatabase.getInstance()
        val myRef = fBdatabase.getReference("Tickets")
        Thread(Runnable { myRef.child(tempTicket.ticketId.toString()).setValue(tempTicket) }).start()
    }

}