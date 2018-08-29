package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.settings.UserProfileSettings
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.DateTimeUtils
import java.util.*


class TicketDetailActivity : AppCompatActivity(), TicketDetailContract.View {


    private var mTitleText: EditText? = null
    private var mDescriptionText: EditText? = null

    private var mTicketType = C.VIEW_TICKET_TYPE
    private var mReceivedTicketId = C.DEFAULT_TICKET_ID

    private var mTextViewTicketName: TextView? = null
    private var mTextViewTicketDescription: TextView? = null
    private var mImageViewTicketUrl: ImageView? = null

    private var mPresenter: TicketDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        setupActionBar()

        mTitleText = findViewById(R.id.editTextTicketTitle)
        mDescriptionText = findViewById(R.id.editTextTicketDescription)

        mTextViewTicketName = findViewById(R.id.itemName)
        mTextViewTicketDescription = findViewById(R.id.itemDescription)
        mImageViewTicketUrl = findViewById(R.id.itemUrl)

        receiveTicketInfo()

        mPresenter = TicketDetailPresenter()
        mPresenter?.setupView(this, mTicketType, mReceivedTicketId)

    }


    override fun updateText(itemName: String, itemDescription: String) {
        mTextViewTicketName?.text = itemName
        mTextViewTicketDescription?.text = itemDescription
    }


    override fun updateContent(itemUrl: String) {
        Glide.with(this)
                .load(itemUrl)
                .into(this.mImageViewTicketUrl!!)
    }


    /**
     * Get ticket IdUtils from MainActivity
     */
    private fun receiveTicketInfo() {
        val intent = intent
        mReceivedTicketId = intent.getIntExtra(C.KEY_TICKET_ID, C.DEFAULT_TICKET_ID)
        mTicketType = intent.getIntExtra(C.KEY_TICKET_TYPE, C.VIEW_TICKET_TYPE)
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


    /**
     * Add or update entry when SUBMIT button is clicked.
     */
    fun onSubmitButtonClicked(view: View) {

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

        mPresenter?.tempTicket?.userId = UserProfileSettings.getUserID()
        mPresenter?.tempTicket?.userName = UserProfileSettings.getUsername()
        mPresenter?.tempTicket?.userPhotoUrl = UserProfileSettings.getUserPhotoURL()

    }


}

