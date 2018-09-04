package project.docs.files.addrequest_kotlin.ui.TicketList

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.firebase.ui.auth.AuthUI
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.adapter.TicketAdapter
import project.docs.files.addrequest_kotlin.settings.UserProfile
import project.docs.files.addrequest_kotlin.services.FirebaseDbListenerService
import project.docs.files.addrequest_kotlin.ui.Main.MainActivity
import project.docs.files.addrequest_kotlin.ui.TicketDetail.TicketDetailActivity
import project.docs.files.addrequest_kotlin.utils.C
import android.app.Activity


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


    /**
     * Log out, end Firebase DB listener service, go to main activity, and destroy activity
     */
    private fun logout() {

        stopService(Intent(this, FirebaseDbListenerService::class.java))
        UserProfile.setUserProfileAtLogout()
        AuthUI.getInstance().signOut(this)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }


    override fun onBackPressed() {
        logout()
    }


    override fun onItemClickListener(ticketId: Int, userId: String) {
        val intent = Intent(this, TicketDetailActivity::class.java)
        if( UserProfile.getUserID()?.equals(userId)!! ){
            intent.putExtra(C.KEY_TICKET_TYPE, C.UPDATE_TICKET_TYPE)
        } else{
            intent.putExtra(C.KEY_TICKET_TYPE, C.VIEW_TICKET_TYPE)
        }
        intent.putExtra(C.KEY_TICKET_ID, ticketId)
        startActivity(intent)
    }


    fun onFabClick(view: View ){
        val intent = Intent(this, TicketDetailActivity::class.java)
        intent.putExtra(C.KEY_TICKET_TYPE, C.ADD_TICKET_TYPE)
        startActivity(intent)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == C.REQUEST_DELETE_TICKET) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("test", "requestCode:  $requestCode  ;  resultCode:  $resultCode")
                val ticketId = data?.getIntExtra(C.KEY_TICKET_ID, C.DEFAULT_TICKET_ID)
                mViewModel?.deleteTicket(ticketId!!)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("test", "requestCode:  $requestCode  ;  resultCode:  $resultCode")
            }
        }
        Log.d("test", "requestCode:  $requestCode  ;  resultCode:  $resultCode")
    }


}