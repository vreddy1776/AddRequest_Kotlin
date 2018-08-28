package project.docs.files.addrequest_kotlin.ui.ItemDetail

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.utils.C


class ItemDetailActivity : AppCompatActivity(), ItemDetailContract.View {


    private var mTextViewItemName: TextView? = null
    private var mTextViewItemDescription: TextView? = null
    private var mImageViewItemUrl: ImageView? = null

    private var mPresenter: ItemDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        setupActionBar()
        mTextViewItemName = findViewById(R.id.itemName)
        mTextViewItemDescription = findViewById(R.id.itemDescription)
        mImageViewItemUrl = findViewById(R.id.itemUrl)

        mPresenter = ItemDetailPresenter()
        mPresenter?.setupView(this, intent.getIntExtra(C.KEY_ITEM_ID, C.DEFAULT_ITEM_ID))

    }


    override fun updateText(itemName: String, itemDescription: String) {
        mTextViewItemName?.text = itemName
        mTextViewItemDescription?.text = itemDescription
    }


    override fun updateContent(itemUrl: String) {
        Glide.with(this)
                .load(itemUrl)
                .into(this.mImageViewItemUrl!!)
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

