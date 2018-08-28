package project.docs.files.addrequest_kotlin.ui.TicketDetail


class TicketDetailContract{

    interface View{

        fun updateText(itemName: String,
                       itemDescription: String)

        fun updateContent(itemUrl: String)

    }

}