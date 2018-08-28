package project.docs.files.addrequest_kotlin.ui.TicketList

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.adapter.TicketAdapter
import project.docs.files.addrequest_kotlin.ui.TicketDetail.TicketDetailActivity
import project.docs.files.addrequest_kotlin.utils.C


class TicketListActivity : AppCompatActivity(), TicketAdapter.TicketClickListener {


    private lateinit var mRecyclerView: RecyclerView
    private var mTicketAdapter: TicketAdapter? = null
    private var mViewModel: TicketListViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        mTicketAdapter = TicketAdapter()
        mTicketAdapter!!.setup(this)

        mRecyclerView = findViewById(R.id.recyclerViewItems)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mTicketAdapter

        mViewModel = ViewModelProviders.of(this).get(TicketListViewModel::class.java)
        mViewModel!!.setup()
        mViewModel!!.updateAdapter(mTicketAdapter!!)

    }


    override fun onBackPressed() {
        // do nothing
    }


    override fun onItemClickListener(ticketId: Int) {
        val intent = Intent(this, TicketDetailActivity::class.java)
        intent.putExtra(C.KEY_ITEM_ID, ticketId)
        startActivity(intent)
    }


}