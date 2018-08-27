package project.docs.files.template_kotlin.ui.List

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import project.docs.files.template_kotlin.R
import project.docs.files.template_kotlin.adapter.ItemAdapter
import project.docs.files.template_kotlin.data.AppDatabase


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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}