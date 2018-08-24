package project.docs.files.template_kotlin.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface ItemDao {


    /**
     * Load Items
     */

    @Query("SELECT * FROM Items")
    fun loadAllItems(): LiveData<List<Item>>

    @Query("SELECT * FROM Items WHERE itemId = :itemId")
    fun loadItemById(itemId: Int): LiveData<Item>


    /**
     * Insert Items
     */

    @Insert
    fun insertItem(item: Item)


    /**
     * Update Items
     */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(item: Item)


    /**
     * Delete Items
     */

    @Query("DELETE FROM Items")
    fun deleteAllItems()


}
