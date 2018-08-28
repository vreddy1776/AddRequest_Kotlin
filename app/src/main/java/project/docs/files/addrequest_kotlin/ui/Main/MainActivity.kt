package project.docs.files.addrequest_kotlin.ui.Main

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.ui.TicketList.TicketListActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        goToListActivity()
    }


    private fun setupActionBar() {

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }

    }


    private fun goToListActivity() {

        val intent = Intent(this, TicketListActivity::class.java)

        val handler = Handler()
        handler.postDelayed({ startActivity(intent) }, 3000)   //3 second
    }


    override fun onBackPressed() {
        // do nothing
    }


}
