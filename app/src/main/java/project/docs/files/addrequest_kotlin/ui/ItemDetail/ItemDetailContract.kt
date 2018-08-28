package project.docs.files.addrequest_kotlin.ui.ItemDetail


class ItemDetailContract{

    interface View{

        fun updateText(itemName: String,
                       itemDescription: String)

        fun updateContent(itemUrl: String)

    }

}