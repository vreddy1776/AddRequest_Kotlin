package project.docs.files.addrequest_kotlin.ui.TicketDetail


class TicketDetailContract{

    interface View{

        fun updateText(ticketTitle: String,
                       ticketDescription: String)

        fun updateVideoView()

    }

}