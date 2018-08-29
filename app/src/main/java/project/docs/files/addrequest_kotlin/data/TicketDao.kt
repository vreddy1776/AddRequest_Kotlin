package project.docs.files.addrequest_kotlin.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface TicketDao {


    /**
     * Load Items
     */

    @Query("SELECT * FROM Tickets")
    fun loadAllTickets(): LiveData<List<Ticket>>

    @Query("SELECT * FROM Tickets WHERE ticketId = :ticketId")
    fun loadTicketById(ticketId: Int): LiveData<Ticket>


    /**
     * Insert Items
     */

    @Insert
    fun insertTicket(ticket: Ticket)


    /**
     * Update Items
     */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTicket(ticket: Ticket)


    /**
     * Delete Items
     */

    @Query("DELETE FROM Tickets WHERE ticketId = :ticketId")
    fun deleteTicketById(ticketId: Int)

    @Query("DELETE FROM Tickets")
    fun deleteAllTickets()


}
