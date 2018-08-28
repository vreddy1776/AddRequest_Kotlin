package project.docs.files.addrequest_kotlin.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import project.docs.files.addrequest_kotlin.utils.IdUtils


@Entity(tableName = "Items")
class Item {


    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0

    var itemName: String? = null
    var itemDescription: String? = null
    var itemDate: String? = null
    var itemUrl: String? = null


    @Ignore
    constructor() {
        this.itemId = IdUtils.newID()
        this.itemName = ""
        this.itemDescription = ""
        this.itemDate = ""
        this.itemUrl = ""
    }


    @Ignore
    constructor(item: Item) {
        this.itemId = IdUtils.newID()
        this.itemName = item.itemName
        this.itemDescription = item.itemDescription
        this.itemDate = item.itemDate
        this.itemUrl = item.itemUrl
    }


    @Ignore
    constructor(
            itemName: String,
            itemDescription: String,
            itemDate: String,
            itemUrl: String) {
        this.itemId = IdUtils.newID()
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.itemDate = itemDate
        this.itemUrl = itemUrl
    }


    constructor(
            itemId: Int,
            itemName: String,
            itemDescription: String,
            itemDate: String,
            itemUrl: String) {
        this.itemId = itemId
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.itemDate = itemDate
        this.itemUrl = itemUrl
    }


}