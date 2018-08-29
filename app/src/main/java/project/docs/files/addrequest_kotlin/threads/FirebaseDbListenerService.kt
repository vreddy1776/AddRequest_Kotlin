package project.docs.files.addrequest_kotlin.threads

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.database.*
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket


/**
 * Firebase DB Listenser Service
 *
 * Starts FirebaseDB listener at login and ends at logout.
 *
 * @author Vijay T. Reddy
 * @version 1.0.0
 */
class FirebaseDbListenerService : Service() {

    private var mChildEventListener: ChildEventListener? = null
    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mMessagesDatabaseReference: DatabaseReference? = null


    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    /**
     * Start after login by clearing tickets in local DB starting the read listener.
     */
    override fun onCreate() {

        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mMessagesDatabaseReference = mFirebaseDatabase!!.reference.child("Tickets")

        Thread(Runnable { AppDatabase.getInstance().ticketDao().deleteAllTickets() }).start()

        attachDatabaseReadListener()

    }


    /**
     * End listener after logout.
     */
    override fun onDestroy() {
        detachDatabaseReadListener()
    }


    /**
     * Sync additions, updates, and deletions from Firebase DB to local DB.
     */
    private fun attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = object : ChildEventListener {

                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {

                    val ticket = dataSnapshot.getValue(Ticket::class.java)

                    if (!AppDatabase.getInstance().ticketExists(ticket.ticketId)) {
                        Thread(Runnable { AppDatabase.getInstance().ticketDao().insertTicket(ticket) }).start()
                    }
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

                    val ticket = dataSnapshot.getValue(Ticket::class.java)

                    if (AppDatabase.getInstance().ticketExists(ticket.ticketId) /* && !ticket.userId.equals(UserProfile.getUserID())*/ ) {
                        Thread(Runnable { AppDatabase.getInstance().ticketDao().updateTicket(ticket) }).start()
                    }
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                    val ticket = dataSnapshot.getValue(Ticket::class.java)

                    if ( AppDatabase.getInstance().ticketExists(ticket.ticketId) ) {
                        Thread(Runnable { AppDatabase.getInstance().ticketDao().deleteTicketById(ticket.ticketId) }).start()
                    }
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            }
            mMessagesDatabaseReference!!.addChildEventListener(mChildEventListener)
        }
    }


    /**
     *
     * @see .onDestroy
     */
    private fun detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference!!.removeEventListener(mChildEventListener)
            mChildEventListener = null
        }
    }

    companion object {

        private val TAG = FirebaseDbListenerService::class.java.simpleName
    }


}