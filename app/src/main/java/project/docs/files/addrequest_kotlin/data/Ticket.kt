package project.docs.files.addrequest_kotlin.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.IdUtils


@Entity(tableName = "Tickets")
class Ticket {


    @PrimaryKey(autoGenerate = true)
    private var ticketId: Int = 0

    private var ticketTitle: String? = C.BLANK_TICKET_TITLE
    private var ticketDescription: String? = C.BLANK_DESCRIPTION_TITLE
    private var ticketDate: String? = C.DEFAULT_TICKET_DATE
    private var ticketVideoPostId: String? = C.DEFAULT_TICKET_VIDEO_POST_ID
    private var ticketVideoLocalUri: String? = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
    private var ticketVideoInternetUrl: String? = C.DEFAULT_TICKET_VIDEO_INTERNET_URL
    private var userId: String? = C.DEFAULT_USER_ID
    private var userName: String? = C.DEFAULT_USER_NAME
    private var userPhotoUrl: String? = C.DEFAULT_USER_PHOTO_URL


    @Ignore
    fun newTicket() {
        this.ticketId = C.DEFAULT_TICKET_ID
        this.ticketTitle = C.BLANK_TICKET_TITLE
        this.ticketDescription = C.BLANK_DESCRIPTION_TITLE
        this.ticketDate = C.DEFAULT_TICKET_DATE
        this.ticketVideoPostId = C.DEFAULT_TICKET_VIDEO_POST_ID
        this.ticketVideoLocalUri = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
        this.ticketVideoInternetUrl = C.DEFAULT_TICKET_VIDEO_INTERNET_URL
        this.userId = C.DEFAULT_USER_ID
        this.userName = C.DEFAULT_USER_NAME
        this.userPhotoUrl = C.DEFAULT_USER_PHOTO_URL

    }


    @Ignore
    fun Ticket(ticket: Ticket) {
        this.ticketId = ticket.getTicketId()
        this.ticketTitle = ticket.getTicketTitle()
        this.ticketDescription = ticket.getTicketDescription()
        this.ticketDate = ticket.getTicketDate()
        this.ticketVideoPostId = ticket.getTicketVideoPostId()
        this.ticketVideoLocalUri = ticket.getTicketVideoLocalUri()
        this.ticketVideoInternetUrl = ticket.getTicketVideoInternetUrl()
        this.userId = ticket.getUserId()
        this.userName = ticket.getUserName()
        this.userPhotoUrl = ticket.getUserPhotoUrl()

    }


    @Ignore
    fun Ticket(
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


    fun Ticket(
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


    fun getTicketId(): Int {
        return ticketId
    }

    fun setTicketId(ticketId: Int) {
        this.ticketId = ticketId
    }

    fun getTicketTitle(): String? {
        return ticketTitle
    }

    fun setTicketTitle(ticketTitle: String) {
        this.ticketTitle = ticketTitle
    }

    fun getTicketDescription(): String? {
        return ticketDescription
    }

    fun setTicketDescription(ticketDescription: String) {
        this.ticketDescription = ticketDescription
    }

    fun getTicketDate(): String? {
        return ticketDate
    }

    fun setTicketDate(ticketDate: String) {
        this.ticketDate = ticketDate
    }

    fun getTicketVideoPostId(): String? {
        return ticketVideoPostId
    }

    fun setTicketVideoPostId(ticketVideoPostId: String) {
        this.ticketVideoPostId = ticketVideoPostId
    }

    fun getTicketVideoLocalUri(): String? {
        return ticketVideoLocalUri
    }

    fun setTicketVideoLocalUri(ticketVideoLocalUri: String) {
        this.ticketVideoLocalUri = ticketVideoLocalUri
    }

    fun getTicketVideoInternetUrl(): String? {
        return ticketVideoInternetUrl
    }

    fun setTicketVideoInternetUrl(ticketVideoInternetUrl: String) {
        this.ticketVideoInternetUrl = ticketVideoInternetUrl
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

    fun getUserPhotoUrl(): String? {
        return userPhotoUrl
    }

    fun setUserPhotoUrl(userPhotoUrl: String) {
        this.userPhotoUrl = userPhotoUrl
    }

    fun setTicket(ticket: Ticket) {
        this.ticketId = ticket.getTicketId()
        this.ticketTitle = ticket.getTicketTitle()
        this.ticketDescription = ticket.getTicketDescription()
        this.ticketDate = ticket.getTicketDate()
        this.ticketVideoPostId = ticket.getTicketVideoPostId()
        this.ticketVideoLocalUri = ticket.getTicketVideoLocalUri()
        this.ticketVideoInternetUrl = ticket.getTicketVideoInternetUrl()
        this.userId = ticket.getUserId()
        this.userName = ticket.getUserName()
        this.userPhotoUrl = ticket.getUserPhotoUrl()

    }


}