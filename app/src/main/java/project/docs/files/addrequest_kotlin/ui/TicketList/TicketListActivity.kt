package project.docs.files.addrequest_kotlin.ui.TicketList

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.firebase.ui.auth.AuthUI
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.adapter.TicketAdapter
import project.docs.files.addrequest_kotlin.threads.FirebaseDbListenerService
import project.docs.files.addrequest_kotlin.ui.Main.MainActivity
import project.docs.files.addrequest_kotlin.ui.TicketDetail.TicketDetailActivity
import project.docs.files.addrequest_kotlin.utils.C


class TicketListActivity : AppCompatActivity(), TicketAdapter.TicketClickListener {


    private lateinit var mRecyclerView: RecyclerView
    private var mTicketAdapter: TicketAdapter? = null
    private var mViewModel: TicketListViewModel? = null
    private var fabButton: FloatingActionButton? = null


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


    /**
     * Log out, end Firebase DB listener service, go to main activity, and destroy activity
     */
    private fun logout() {

        stopService(Intent(this, FirebaseDbListenerService::class.java))
        //UserProfileSettings.setUserProfileAtLogout()
        AuthUI.getInstance().signOut(this)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }


    override fun onBackPressed() {
        logout()
    }


    override fun onItemClickListener(ticketId: Int) {
        val intent = Intent(this, TicketDetailActivity::class.java)
        intent.putExtra(C.KEY_ITEM_ID, ticketId)
        startActivity(intent)
    }


    fun goToTicketDetail(view: View ){
        val addTicketIntent = Intent(this, TicketDetailActivity::class.java)
        addTicketIntent.putExtra(C.KEY_TICKET_TYPE, C.ADD_TICKET_TYPE)
        startActivity(addTicketIntent)
    }

}