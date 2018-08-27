package project.docs.files.template_kotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import project.docs.files.template_kotlin.application.MyApplication


private val LOG_TAG = AppDatabase::class.java!!.getSimpleName()
private val LOCK = Any()
private const val DATABASE_NAME = "item_database"
private var sInstance : AppDatabase? = null


@Database(entities = [(Item::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase().also { INSTANCE = it }
                }

        private fun buildDatabase() =
                Room.databaseBuilder(MyApplication.appContext!!,
                        AppDatabase::class.java, "item_database")
                        .build()

    }


    abstract fun itemDao(): ItemDao


}