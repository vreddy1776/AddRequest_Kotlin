package project.docs.files.addrequest_kotlin.threads

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.parceler.Parcels
import project.docs.files.addrequest_kotlin.data.AppDatabase
import project.docs.files.addrequest_kotlin.data.Ticket
import project.docs.files.addrequest_kotlin.utils.C


/**
 * Video Upload Service
 *
 * Service to run if user uploads video.
 *
 * @author Vijay T. Reddy
 * @version 1.0.0
 */
class VideoUploadService : Service() {


    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        Log.d("videoTest","onStartCommand")
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show()

        //val bundle = intent.getBundleExtra("bundle")
        //var person  = bundle.getParcelable<Person>("selected_person") as Person

        //val ticket = intent.getBundleExtra(C.KEY_TICKET).getParcelable(C.KEY_TICKET) as Ticket
        //val ticket = bundle.getParcelable(C.KEY_TICKET) as Ticket
        //val ticketType = bundle.getInt(C.KEY_TICKET_TYPE)

        val ticket = Parcels.unwrap<Any>(intent.getParcelableExtra(C.KEY_TICKET)) as Ticket
        val ticketType = intent.extras!!.get(C.KEY_TICKET_TYPE) as Int

        uploadVideo(ticket, ticketType)

        return super.onStartCommand(intent, flags, startId)
    }


    private fun uploadVideo(tempTicket: Ticket, ticketType: Int) {

        Log.d("videoTest","uploadvideo")
        Toast.makeText(this, "uploadVideo", Toast.LENGTH_SHORT).show()

        val firebaseStorage = FirebaseStorage.getInstance()
        val firebaseVideoRef = firebaseStorage.reference.child("Videos")

        Toast.makeText(this, tempTicket.ticketVideoLocalUri, Toast.LENGTH_SHORT).show()

        val capturedVideoUri = Uri.parse(tempTicket.ticketVideoLocalUri)
        val localVideoRef = firebaseVideoRef.child(capturedVideoUri.lastPathSegment)
        val uploadTask = localVideoRef.putFile(capturedVideoUri)

        uploadTask.addOnFailureListener {
            Toast.makeText(this, "video upload failed", Toast.LENGTH_SHORT).show()
            stopSelf()
        }.addOnSuccessListener { taskSnapshot ->

            Toast.makeText(this, "video upload success", Toast.LENGTH_SHORT).show()

            tempTicket.ticketVideoPostId = C.VIDEO_EXISTS_TICKET_VIDEO_POST_ID
            tempTicket.ticketVideoInternetUrl = taskSnapshot.downloadUrl.toString()
            //tempTicket.ticketVideoInternetUrl = taskSnapshot.toString()

            addTicketToDb(tempTicket, ticketType)
            //Notifications.ticketPostedNotification(tempTicket.ticketId)
            stopSelf()
        }

    }


    /**
     * Calls two threads - one for adding ticket to local DB other to remote DB.
     *
     * @param ticket The ticket to be added.
     * @param ticketType View, Add, or Update ticket type for AddTicketActivity session.
     */
    fun addTicketToDb(tempTicket: Ticket, ticketType: Int) {

        addTicketToLocalDb(tempTicket, ticketType)
        addTicketToFirebaseDb(tempTicket)
    }


    /**
     * Adds ticket to local (SQLlite) DB.
     *
     * @see .addTicketToDb
     * @param ticket The ticket to be added.
     * @param ticketType View, Add, or Update ticket type for AddTicketActivity session.
     */
    fun addTicketToLocalDb(tempTicket: Ticket, ticketType: Int) {

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
    private fun addTicketToFirebaseDb(tempTicket: Ticket) {

        val fBdatabase = FirebaseDatabase.getInstance()
        val myRef = fBdatabase.getReference("Tickets")
        Thread(Runnable { myRef.child(tempTicket.ticketId.toString()).setValue(tempTicket) }).start()
    }

    companion object {


        private val TAG = VideoUploadService::class.java.simpleName
    }


}