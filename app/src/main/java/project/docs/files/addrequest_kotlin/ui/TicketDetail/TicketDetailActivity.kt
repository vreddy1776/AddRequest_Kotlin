package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_item_detail.*
import org.parceler.Parcels
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.data.Ticket
import project.docs.files.addrequest_kotlin.settings.UserProfile
import project.docs.files.addrequest_kotlin.threads.VideoUploadService
import project.docs.files.addrequest_kotlin.ui.TicketList.TicketListActivity
import project.docs.files.addrequest_kotlin.utils.C
import project.docs.files.addrequest_kotlin.utils.DateTimeUtils
import java.util.*


class TicketDetailActivity : AppCompatActivity(), TicketDetailContract.View {


    private var mTitleText: EditText? = null
    private var mDescriptionText: EditText? = null
    private var mVideoWrapper: FrameLayout? = null
    private var mStreamVideo: FrameLayout? = null
    private var mSubmitButton: Button? = null
    private var mVideoButton: ImageView? = null
    private var mVideoDeleteButton: ImageView? = null
    private var mTrashButton: ImageView? = null

    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 4
    private val VIDEO_REQUEST = 5
    internal val RESULT_OK = -1

    private var mTicketType = C.VIEW_TICKET_TYPE
    private var mReceivedTicketId = C.DEFAULT_TICKET_ID

    private var mVideoUri: Uri? = null

    private var mPresenter: TicketDetailPresenter? = null

    private lateinit var player: SimpleExoPlayer
    private var shouldAutoPlay: Boolean = false
    private lateinit var trackSelector: DefaultTrackSelector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        receiveTicketInfo()

        mPresenter = TicketDetailPresenter()
        mPresenter?.setupView(this, mTicketType, mReceivedTicketId)

        initViews()

    }


    override fun updateText(ticketTitle: String, ticketDescription: String) {
        mTitleText?.setText(ticketTitle)
        mDescriptionText?.setText(ticketDescription)
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
        mStreamVideo = findViewById(R.id.stream_video)

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

        updateVideoView()

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


    override fun updateVideoView() {

        if (mPresenter?.tempTicket?.ticketVideoPostId.equals(C.DEFAULT_TICKET_VIDEO_POST_ID)) {
            mStreamVideo?.visibility = View.INVISIBLE
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
            mStreamVideo?.visibility = View.VISIBLE
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
            initPlayer()
            shouldAutoPlay = true


        }
    }


    fun onVideoDeleteButtonClicked(view: View) {

        mPresenter?.tempTicket?.ticketVideoPostId = C.DEFAULT_TICKET_VIDEO_POST_ID
        mPresenter?.tempTicket?.ticketVideoLocalUri = C.DEFAULT_TICKET_VIDEO_LOCAL_URI
        mPresenter?.tempTicket?.ticketVideoInternetUrl = C.DEFAULT_TICKET_VIDEO_INTERNET_URL

        updateVideoView()
    }


    /**
     * Start Camera Intent when video button is clicked.
     */
    fun onVideoButtonClicked(view: View) {
        requestReadExternalStoragePermission()
    }


    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Optional explanation
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            }
        } else {
            // Permission has already been granted
            val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(takeVideoIntent, VIDEO_REQUEST)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    startActivityForResult(takeVideoIntent, VIDEO_REQUEST)
                } else {
                    // permission denied, boo
                }
                return
            }
        }
    }


    /**
     * Get Result from Camera Video Intent.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {

            mVideoUri = data.data

            mPresenter?.tempTicket?.ticketVideoPostId = C.VIDEO_CREATED_TICKET_VIDEO_POST_ID
            mPresenter?.tempTicket?.ticketVideoLocalUri = mVideoUri.toString()

            Toast.makeText(this, mPresenter?.tempTicket?.ticketVideoLocalUri, Toast.LENGTH_SHORT).show()


            updateVideoView()

        }
    }


    /**
     * Add or update entry when SUBMIT button is clicked.
     */
    fun onSubmitButtonClicked(view: View) {

        setTicketValues()
        setUserValues()

        if (mPresenter?.tempTicket?.ticketVideoPostId.equals(C.VIDEO_CREATED_TICKET_VIDEO_POST_ID)) {

            /*
            var videoTicket = Ticket()
            videoTicket.setTicket(mPresenter?.tempTicket!!)

            val bundle = Bundle()
            bundle.putParcelable(C.KEY_TICKET, videoTicket)
            bundle.putInt(C.KEY_TICKET_TYPE, mTicketType)
            */

            val intent = Intent(this, VideoUploadService::class.java)
            //intent.putExtra(C.KEY_TICKET, Parcels.wrap(videoTicket))
            //intent.putExtra(C.KEY_TICKET, Parcelize.wrap(mPresenter?.tempTicket))
            //intent.putP
            intent.putExtra(C.KEY_TICKET, Parcels.wrap(mPresenter?.tempTicket))
            //intent.putExtra("bundle", bundle)
            intent.putExtra(C.KEY_TICKET_TYPE, mTicketType)
            startService(intent)

        } else {
            mPresenter?.addTicketToDb(mTicketType)
        }

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


    fun initPlayer(){
        val simpleExoPlayerView = findViewById<SimpleExoPlayerView>(R.id.stream_video)
        val bandwidthMeter = DefaultBandwidthMeter()
        val extractorsFactory = DefaultExtractorsFactory()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), bandwidthMeter as TransferListener<in DataSource>)
        val mediaSource = ExtractorMediaSource(mVideoUri,
                mediaDataSourceFactory, extractorsFactory, null, null)

        simpleExoPlayerView?.requestFocus()
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        simpleExoPlayerView?.player = player
        player.playWhenReady = shouldAutoPlay
        player.prepare(mediaSource)

    }


    fun releasePlayer() {
        player.release()
        shouldAutoPlay = player.playWhenReady
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23)) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }


}

