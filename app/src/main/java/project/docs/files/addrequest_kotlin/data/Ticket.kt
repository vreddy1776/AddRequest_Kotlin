package project.docs.files.addrequest_kotlin.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.parceler.Parcel
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.IdUtils

@Parcel
@Entity(tableName = "Tickets")
class Ticket() : Parcelable{


    @PrimaryKey(autoGenerate = true)
    var ticketId: Int = IdUtils.newID()

    var ticketTitle: String? = C.BLANK_TICKET_TITLE
    var ticketDescription: String? = C.BLANK_DESCRIPTION_TITLE
    var ticketDate: String? = C.DEFAULT_TICKET_DATE
    var ticketVideoPostId: String? = C.DEFAULT_TICKET_VIDEO_POST_ID
    var ticketVideoLocalUri: String? = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
    var ticketVideoInternetUrl: String? = C.DEFAULT_TICKET_VIDEO_INTERNET_URL
    var userId: String? = C.DEFAULT_USER_ID
    var userName: String? = C.DEFAULT_USER_NAME
    var userPhotoUrl: String? = C.DEFAULT_USER_PHOTO_URL

    constructor(parcel: android.os.Parcel) : this() {
        ticketId = parcel.readInt()
        ticketTitle = parcel.readString()
        ticketDescription = parcel.readString()
        ticketDate = parcel.readString()
        ticketVideoPostId = parcel.readString()
        ticketVideoLocalUri = parcel.readString()
        ticketVideoInternetUrl = parcel.readString()
        userId = parcel.readString()
        userName = parcel.readString()
        userPhotoUrl = parcel.readString()
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

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(ticketId)
        parcel.writeString(ticketTitle)
        parcel.writeString(ticketDescription)
        parcel.writeString(ticketDate)
        parcel.writeString(ticketVideoPostId)
        parcel.writeString(ticketVideoLocalUri)
        parcel.writeString(ticketVideoInternetUrl)
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(userPhotoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: android.os.Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }


}