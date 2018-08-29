package project.docs.files.addrequest_kotlin.utils

import android.arch.persistence.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * DateTime Utils
 *
 * Convert and format Date object to string
 *
 * @author Vijay T. Reddy
 * @version 1.0.0
 */
object DateTimeUtils {


    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }


    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }


    fun stringToDate(dateString: String): Date {

        val sdf = SimpleDateFormat("YYYY-MM-dd")
        var date = Date()
        try {
            date = sdf.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date

    }


    fun dateToString(date: Date): String {

        val sdf = SimpleDateFormat("MM-dd-YYYY  h:mm a")
        val dateString = sdf.format(date)
        return dateString

    }


}