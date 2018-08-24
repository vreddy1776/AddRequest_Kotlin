package project.docs.files.template_kotlin.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context




private val LOG_TAG = AppDatabase::class.java!!.getSimpleName()
private val DATABASE_NAME = "item_database"




@Database(entities = [(Item::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
            }
            return INSTANCE as AppDatabase
        }
    }


    abstract fun ItemDao(): ItemDao


}