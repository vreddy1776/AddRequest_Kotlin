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

    @Query("SELECT * FROM Tickets WHERE itemId = :itemId")
    fun loadTicketById(itemId: Int): LiveData<Ticket>


    /**
     * Insert Items
     */

    @Insert
    fun insertTicket(item: Ticket)


    /**
     * Update Items
     */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTicket(item: Ticket)


    /**
     * Delete Items
     */

    @Query("DELETE FROM Tickets")
    fun deleteAllTickets()


}
