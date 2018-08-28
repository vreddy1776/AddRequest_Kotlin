package project.docs.files.addrequest_kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.data.Ticket


class TicketAdapter : RecyclerView.Adapter<TicketAdapter.ItemViewHolder>() {


    private var mItemClickListener: ItemClickListener? = null
    private var mTicketList: List<Ticket>? = null
    private var mContext: Context? = null


    /**
     * Constructor for the TicketAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    fun setup(context: Context, listener: ItemClickListener) {
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
    fun setItems(itemList: List<Ticket>) {
        mTicketList = itemList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = mTicketList!![position]

        holder.TicketNameView.setText(item.itemName)
        holder.TicketDescriptionView.setText(item.itemDescription)
        holder.TicketDateView.setText(item.itemDate)

        Glide.with(MyApplication.appContext!!)
                .load(item.itemUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.TicketUrlView)

    }


    override fun getItemCount(): Int {
        return if (mTicketList == null) {
            0
        } else mTicketList!!.size
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
    inner class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        // Class variables for the item description and priority TextViews
        var TicketNameView: TextView
        var TicketDescriptionView: TextView
        var TicketDateView: TextView
        var TicketUrlView: ImageView

        init {

            TicketNameView = itemView.findViewById(R.id.itemName)
            TicketDescriptionView = itemView.findViewById(R.id.itemDescription)
            TicketDateView = itemView.findViewById(R.id.itemDate)
            TicketUrlView = itemView.findViewById(R.id.itemUrl)
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View) {
            val elementId = mTicketList?.get(adapterPosition)?.itemId
            mItemClickListener?.onItemClickListener(elementId!!)
        }

    }


}