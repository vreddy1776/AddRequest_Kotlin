package project.docs.files.addrequest_kotlin.threads

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.ui.TicketDetail.TicketDetailActivity
import project.docs.files.addrequest_kotlin.utils.C


/**
 * Notifications
 *
 * Class with all Notification references.
 *
 * @author Vijay T. Reddy
 * @version 1.0.0
 */
object Notifications {

    /*
     * This notification IdUtils can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
    private val NOTIFICATION_ID = 1138
    private val MAIN_NOTIFICATION_CHANNEL_ID = "main_notification_channel"
    private val OPEN_REQUEST = 0
    private var mTicketID = C.DEFAULT_TICKET_ID


    fun ticketPostedNotification(ticketID: Int) {

        val context = MyApplication.appContext
        mTicketID = ticketID

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                    MAIN_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, MAIN_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_body)))
                .setDefaults(android.app.Notification.DEFAULT_VIBRATE)
                .addAction(openTicketAction(context))
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

    }


    private fun openTicketAction(context: Context): NotificationCompat.Action {

        val intent = Intent(context, TicketDetailActivity::class.java)
        intent.putExtra(C.KEY_TICKET_ID, mTicketID)
        intent.putExtra(C.KEY_TICKET_TYPE, C.UPDATE_TICKET_TYPE)

        val pendingIntent = PendingIntent.getActivity(
                context,
                OPEN_REQUEST,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val action = NotificationCompat.Action(R.drawable.ic_launcher_foreground,
                "View Your Request",
                pendingIntent)

        return action

    }


    fun clearAllNotifications(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }


    private fun largeIcon(context: Context): Bitmap {
        val res = context.resources
        val largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground)
        return largeIcon
    }
}