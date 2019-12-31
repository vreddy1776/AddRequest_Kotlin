package project.docs.files.addrequest_kotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import project.docs.files.addrequest_kotlin.di.App


@Database(entities = [(Ticket::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase().also { INSTANCE = it }
                }

        private fun buildDatabase() =
                Room.databaseBuilder(App.applicationContext(),
                        AppDatabase::class.java, "ticket_database")
                        .build()

    }


    abstract fun ticketDao(): TicketDao


    fun ticketExists(ticketId: Int): Boolean {

        val cursor = INSTANCE?.query("SELECT * FROM Tickets WHERE ticketId = $ticketId", null)
        val cursorCount = cursor?.count

        return cursorCount != 0

    }

}