package project.docs.files.addrequest_kotlin.ui.TicketDetail

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.utils.C


class TicketDetailActivity : AppCompatActivity(), TicketDetailContract.View {


    private var mTextViewTicketName: TextView? = null
    private var mTextViewTicketDescription: TextView? = null
    private var mImageViewTicketUrl: ImageView? = null

    private var mPresenter: TicketDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        setupActionBar()
        mTextViewTicketName = findViewById(R.id.itemName)
        mTextViewTicketDescription = findViewById(R.id.itemDescription)
        mImageViewTicketUrl = findViewById(R.id.itemUrl)

        mPresenter = TicketDetailPresenter()
        mPresenter?.setupView(this, intent.getIntExtra(C.KEY_ITEM_ID, C.DEFAULT_ITEM_ID))

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


    private fun setupActionBar() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }


}

