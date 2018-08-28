package project.docs.files.template_kotlin.ui.ItemDetail


class ItemDetailContract{

    interface View{

        fun updateText(itemName: String,
                       itemDescription: String)

        fun updateContent(itemUrl: String)

    }

}