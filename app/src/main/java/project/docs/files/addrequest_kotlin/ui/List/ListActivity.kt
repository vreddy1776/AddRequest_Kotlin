package project.docs.files.addrequest_kotlin.ui.List

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.adapter.ItemAdapter
import project.docs.files.addrequest_kotlin.ui.ItemDetail.ItemDetailActivity
import project.docs.files.addrequest_kotlin.utils.C


class ListActivity : AppCompatActivity(), ItemAdapter.ItemClickListener {


    private lateinit var mRecyclerView: RecyclerView
    private var mItemAdapter: ItemAdapter? = null
    private var mViewModel: ListViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        mItemAdapter = ItemAdapter()
        mItemAdapter!!.setup(this,this)

        mRecyclerView = findViewById(R.id.recyclerViewItems)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mItemAdapter

        mViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        mViewModel!!.setup()
        mViewModel!!.updateAdapter(mItemAdapter!!)

    }


    override fun onBackPressed() {
        // do nothing
    }


    override fun onItemClickListener(itemId: Int) {
        val intent = Intent(this, ItemDetailActivity::class.java)
        intent.putExtra(C.KEY_ITEM_ID, itemId)
        startActivity(intent)
    }


}