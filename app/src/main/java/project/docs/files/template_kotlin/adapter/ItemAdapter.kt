package project.docs.files.template_kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import project.docs.files.template_kotlin.R
import project.docs.files.template_kotlin.application.MyApplication
import project.docs.files.template_kotlin.data.Item


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    private var mItemClickListener: ItemClickListener? = null
    private var mItemList: List<Item>? = null
    private var mContext: Context? = null


    /**
     * Constructor for the ItemAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    fun ItemAdapter(context: Context, listener: ItemClickListener) {
        mContext = context
        mItemClickListener = listener
    }


    /**
     * Set up ItemClickListener.
     */
    interface ItemClickListener {
        fun onItemClickListener(itemId: Int)
    }


    /**
     * When data changes, this method updates the list of items
     * and notifies the adapter to use the new values on it
     */
    fun setItems(itemList: List<Item>) {
        mItemList = itemList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = mItemList!![position]

        holder.itemNameView.setText(item.itemName)
        holder.itemDescriptionView.setText(item.itemDescription)
        holder.itemDateView.setText(item.itemDate)

        Glide.with(MyApplication.appContext!!)
                .load(item.itemUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.itemUrlView)

    }

    override fun getItemCount(): Int {
        return if (mItemList == null) {
            0
        } else mItemList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        // Inflate the item to a view
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_layout, parent, false)

        return ItemViewHolder(view)

    }


    /**
     * Inner class for creating ViewHolders.
     */
    class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        // Class variables for the item description and priority TextViews
        var itemNameView: TextView
        var itemDescriptionView: TextView
        var itemDateView: TextView
        var itemUrlView: ImageView

        init {

            itemNameView = itemView.findViewById(R.id.itemName)
            itemDescriptionView = itemView.findViewById(R.id.itemDescription)
            itemDateView = itemView.findViewById(R.id.itemDate)
            itemUrlView = itemView.findViewById(R.id.itemUrl)
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            //val elementId = mItemList.get(adapterPosition).getItemId()
            //mItemClickListener.onItemClickListener(elementId)
        }

    }


}