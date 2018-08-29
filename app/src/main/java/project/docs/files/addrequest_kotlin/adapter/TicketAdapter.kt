package project.docs.files.addrequest_kotlin.adapter

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


class TicketAdapter : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {


    private var mTicketClickListener: TicketClickListener? = null
    private var mTicketList: List<Ticket>? = null


    /**
     * Constructor for the TicketAdapter that initializes the Context.
     *
     * @param listener the TicketClickListener
     */
    fun setup(listener: TicketClickListener) {
        mTicketClickListener = listener
    }


    /**
     * Set up TicketClickListener.
     */
    interface TicketClickListener {
        fun onItemClickListener(ticketId: Int, userId: String)
    }


    /**
     * When data changes, this method updates the list of items
     * and notifies the adapter to use the new values on it
     */
    fun setTicketList(ticketList: List<Ticket>) {
        mTicketList = ticketList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {

        val ticket = mTicketList!![position]

        holder.ticketNameView.text = ticket.ticketTitle
        holder.ticketDescriptionView.text = ticket.ticketDescription
        holder.ticketDateView.text = ticket.ticketDate

        Glide.with(MyApplication.appContext!!)
                .load(ticket.userPhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ticketUrlView)

    }


    override fun getItemCount(): Int {
        return if (mTicketList == null) {
            0
        } else mTicketList!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {

        // Inflate the item to a view
        val view = LayoutInflater.from(MyApplication.appContext)
                .inflate(R.layout.item_layout, parent, false)

        return TicketViewHolder(view)

    }


    /**
     * Inner class for creating ViewHolders.
     */
    inner class TicketViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        // Class variables for the item description and priority TextViews

        var ticketNameView: TextView = itemView.findViewById(R.id.ticketTitle)
        var ticketDescriptionView: TextView = itemView.findViewById(R.id.ticketDescription)
        var ticketDateView: TextView = itemView.findViewById(R.id.ticketDate)
        var ticketUrlView: ImageView = itemView.findViewById(R.id.ticketUserProfilePic)

        init { itemView.setOnClickListener(this) }

        override fun onClick(v: View) {
            val ticketId = mTicketList?.get(adapterPosition)?.ticketId
            val userId = mTicketList?.get(adapterPosition)?.userId
            mTicketClickListener?.onItemClickListener(ticketId!!, userId!!)
        }

    }


}