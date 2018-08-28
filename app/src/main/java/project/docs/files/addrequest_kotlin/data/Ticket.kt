package project.docs.files.addrequest_kotlin.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.IdUtils


@Entity(tableName = "Tickets")
class Ticket {


    @PrimaryKey(autoGenerate = true)
    var ticketId: Int = C.DEFAULT_TICKET_ID

    var ticketTitle: String? = C.BLANK_TICKET_TITLE
    var ticketDescription: String? = C.BLANK_DESCRIPTION_TITLE
    var ticketDate: String? = C.DEFAULT_TICKET_DATE
    var ticketVideoPostId: String? = C.DEFAULT_TICKET_VIDEO_POST_ID
    var ticketVideoLocalUri: String? = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
    var ticketVideoInternetUrl: String? = C.DEFAULT_TICKET_VIDEO_INTERNET_URL
    var userId: String? = C.DEFAULT_USER_ID
    var userName: String? = C.DEFAULT_USER_NAME
    var userPhotoUrl: String? = C.DEFAULT_USER_PHOTO_URL


    @Ignore
    fun newTicket() {
        ticketId = C.DEFAULT_TICKET_ID
        ticketTitle = C.BLANK_TICKET_TITLE
        ticketDescription = C.BLANK_DESCRIPTION_TITLE
        ticketDate = C.DEFAULT_TICKET_DATE
        ticketVideoPostId = C.DEFAULT_TICKET_VIDEO_POST_ID
        ticketVideoLocalUri = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
        ticketVideoInternetUrl = C.DEFAULT_TICKET_VIDEO_INTERNET_URL
        userId = C.DEFAULT_USER_ID
        userName = C.DEFAULT_USER_NAME
        userPhotoUrl = C.DEFAULT_USER_PHOTO_URL

    }


    @Ignore
    fun newTicket(
            ticketTitle: String,
            ticketDescription: String,
            ticketDate: String,
            ticketVideoPostId: String,
            ticketVideoLocalUri: String,
            ticketVideoInternetUrl: String,
            userId: String,
            userName: String,
            userPhotoUrl: String) {
        this.ticketTitle = ticketTitle
        this.ticketDescription = ticketDescription
        this.ticketDate = ticketDate
        this.ticketVideoPostId = ticketVideoPostId
        this.ticketVideoLocalUri = ticketVideoLocalUri
        this.ticketVideoInternetUrl = ticketVideoInternetUrl
        this.userId = userId
        this.userName = userName
        this.userPhotoUrl = userPhotoUrl

    }


    fun newTicket(
            ticketId: Int,
            ticketTitle: String,
            ticketDescription: String,
            ticketDate: String,
            ticketVideoPostId: String,
            ticketVideoLocalUri: String,
            ticketVideoInternetUrl: String,
            userId: String,
            userName: String,
            userPhotoUrl: String) {
        this.ticketId = ticketId
        this.ticketTitle = ticketTitle
        this.ticketDescription = ticketDescription
        this.ticketDate = ticketDate
        this.ticketVideoPostId = ticketVideoPostId
        this.ticketVideoLocalUri = ticketVideoLocalUri
        this.ticketVideoInternetUrl = ticketVideoInternetUrl
        this.userId = userId
        this.userName = userName
        this.userPhotoUrl = userPhotoUrl

    }


    fun setTicket(ticket: Ticket) {
        ticketId = ticket.ticketId
        ticketTitle = ticket.ticketTitle
        ticketDescription = ticket.ticketDescription
        ticketDate = ticket.ticketDate
        ticketVideoPostId = ticket.ticketVideoPostId
        ticketVideoLocalUri = ticket.ticketVideoLocalUri
        ticketVideoInternetUrl = ticket.ticketVideoInternetUrl
        userId = ticket.userId
        userName = ticket.userName
        userPhotoUrl = ticket.userPhotoUrl

    }


}