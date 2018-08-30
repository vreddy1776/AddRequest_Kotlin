package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_item_detail.*
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.settings.UserProfile
import project.docs.files.addrequest_kotlin.ui.TicketList.TicketListActivity
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.DateTimeUtils
import java.util.*


class TicketDetailActivity : AppCompatActivity(), TicketDetailContract.View {


    private var mTitleText: EditText? = null
    private var mDescriptionText: EditText? = null
    private var mVideoWrapper: FrameLayout? = null
    //private var mStreamVideo: FrameLayout? = null
    private var mSubmitButton: Button? = null
    private var mVideoButton: ImageView? = null
    private var mVideoDeleteButton: ImageView? = null
    private var mTrashButton: ImageView? = null

    private var mTicketType = C.VIEW_TICKET_TYPE
    private var mReceivedTicketId = C.DEFAULT_TICKET_ID

    private var mVideoUri: Uri? = null

    private var mPresenter: TicketDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        receiveTicketInfo()

        mPresenter = TicketDetailPresenter()
        mPresenter?.setupView(this, mTicketType, mReceivedTicketId)

        //setupVideoPlayer(savedInstanceState)
        //setupViewModelFactory()
        initViews()

    }


    override fun updateText(itemName: String, itemDescription: String) {
        mTitleText?.setText(itemName)
        mDescriptionText?.setText(itemDescription)
    }


    override fun updateContent(itemUrl: String) {
        /*
        Glide.with(this)
                .load(itemUrl)
                .into(this.mImageViewTicketUrl!!)
                */
    }


    /**
     * Get ticket IdUtils from MainActivity
     */
    private fun receiveTicketInfo() {
        val intent = intent
        mReceivedTicketId = intent.getIntExtra(C.KEY_TICKET_ID, C.DEFAULT_TICKET_ID)
        mTicketType = intent.getIntExtra(C.KEY_TICKET_TYPE, C.VIEW_TICKET_TYPE)
    }


    /**
     * initViews is called from onCreate to init the member variable views
     */
    private fun initViews() {

        setupActionBar()

        mTitleText = findViewById(R.id.editTextTicketTitle)
        mDescriptionText = findViewById(R.id.editTextTicketDescription)
        mSubmitButton = findViewById(R.id.submitButton)
        mVideoButton = findViewById(R.id.videoButton)
        mVideoDeleteButton = findViewById(R.id.videoDelete)
        mVideoWrapper = findViewById(R.id.videoWrapper)
        mTrashButton = findViewById(R.id.trash)
        //mStreamVideo = findViewById(R.id.stream_video)

        when (mTicketType) {
            C.VIEW_TICKET_TYPE -> {
                mTitleText!!.isEnabled = false
                mDescriptionText!!.isEnabled = false
                mSubmitButton!!.visibility = View.INVISIBLE
                mTrashButton!!.visibility = View.INVISIBLE
            }
            C.UPDATE_TICKET_TYPE -> mSubmitButton!!.setText(R.string.update_button)
            C.ADD_TICKET_TYPE -> {
                mSubmitButton!!.setText(R.string.submit_button)
                mTrashButton!!.visibility = View.INVISIBLE
            }
            else -> {
                //do nothing
            }
        }

        updateText(mPresenter?.tempTicket?.ticketTitle!!, mPresenter?.tempTicket?.ticketDescription!!)

        setVideoView()

    }


    private fun setupActionBar() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val window = this.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }


    fun setVideoView() {

        if (mPresenter?.tempTicket?.ticketVideoPostId.equals(C.DEFAULT_TICKET_VIDEO_POST_ID)) {
            //mStreamVideo?.visibility = View.INVISIBLE
            mVideoButton?.visibility = View.VISIBLE
            mVideoDeleteButton?.visibility = View.INVISIBLE
            if (mTicketType == C.VIEW_TICKET_TYPE) {
                mVideoWrapper?.background = ContextCompat.getDrawable(MyApplication.appContext!!, R.drawable.background_border_solid)
                mVideoButton?.background = ContextCompat.getDrawable(MyApplication.appContext!!, R.drawable.ic_no_video)
                mVideoButton?.isEnabled = false
            } else {
                videoWrapper.background = ContextCompat.getDrawable(MyApplication.appContext!!, R.drawable.background_border_dashed)
                videoButton.background = ContextCompat.getDrawable(MyApplication.appContext!!, R.drawable.ic_add_video)
                videoButton.isEnabled = true
            }
        } else {
            //mStreamVideo?.visibility = View.VISIBLE
            mVideoButton?.visibility = View.INVISIBLE
            videoWrapper.setBackgroundColor(ContextCompat.getColor(MyApplication.appContext!!, R.color.videoBackground))
            if (mTicketType != C.VIEW_TICKET_TYPE) {
                mVideoDeleteButton?.visibility = View.VISIBLE
            }
            mVideoUri = if(mPresenter?.tempTicket?.ticketVideoPostId.equals(C.VIDEO_EXISTS_TICKET_VIDEO_POST_ID)) {
                Uri.parse(mPresenter?.tempTicket!!.ticketVideoInternetUrl)
            } else {
                Uri.parse(mPresenter?.tempTicket!!.ticketVideoLocalUri)
            }
            /*
            currentWindow = 0
            playbackPosition = 0
            initializePlayer()
            */
        }
    }


    fun onVideoDeleteButtonClicked(view: View) {

        mPresenter?.tempTicket?.ticketVideoPostId = C.DEFAULT_TICKET_VIDEO_POST_ID
        mPresenter?.tempTicket?.ticketVideoLocalUri = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
        mPresenter?.tempTicket?.ticketVideoInternetUrl = C.DEFAULT_TICKET_VIDEO_INTERNET_URL

        setVideoView()
    }


    /**
     * Add or update entry when SUBMIT button is clicked.
     */
    fun onSubmitButtonClicked(view: View) {

        setTicketValues()
        setUserValues()
        mPresenter?.addTicketToDb(mTicketType)
        finish()

    }


    private fun setTicketValues() {

        // Set title if blank or not
        val title = mTitleText?.text.toString()
        if (title == C.BLANK_TICKET_TITLE) {
            mPresenter?.tempTicket?.ticketTitle = C.DEFAULT_TICKET_TITLE
        } else {
            mPresenter?.tempTicket?.ticketTitle = title
        }

        // Set description if blank or not
        val description = mDescriptionText?.text.toString()
        if (description == C.BLANK_DESCRIPTION_TITLE) {
            mPresenter?.tempTicket?.ticketDescription = C.DEFAULT_TICKET_DESCRIPTION
        } else {
            mPresenter?.tempTicket?.ticketDescription = description
        }

        mPresenter?.tempTicket?.ticketDate = DateTimeUtils.dateToString(Date())

    }


    private fun setUserValues() {

        mPresenter?.tempTicket?.userId = UserProfile.getUserID()
        mPresenter?.tempTicket?.userName = UserProfile.getUsername()
        mPresenter?.tempTicket?.userPhotoUrl = UserProfile.getUserPhotoURL()

    }


    fun onDeleteButtonClicked(view: View){
        //mPresenter?.deleteTicket(mReceivedTicketId)

        val intent = Intent(this, TicketListActivity::class.java)
        intent.putExtra(C.KEY_TICKET_ID, mReceivedTicketId)
        startActivityForResult(intent, C.REQUEST_DELETE_TICKET)

        //finish()
    }


}

