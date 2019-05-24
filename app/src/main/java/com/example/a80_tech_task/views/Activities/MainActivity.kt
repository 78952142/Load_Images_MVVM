package com.example.a80_tech_task.views.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Adapter
import com.dasfilm.azzeddine.dasfilm.APIUtils.SpaceItemDecoration
import com.example.a80_tech_task.R
import com.example.a80_tech_task.viewmodels.ImagesViewModel
import com.example.a80_tech_task.views.Adapters.ImagesAdapter


class MainActivity : AppCompatActivity() {
    private var mMoviesViewModel: ImagesViewModel? = null
    private var mRecyclerView: RecyclerView? = null
    private var adapter: ImagesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.list)

        adapter = ImagesAdapter(this)

        mMoviesViewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        mMoviesViewModel!!.getMoviesInTheaterList().observe(this, Observer { movies ->
            adapter!!.submitList(movies)
        })
        mMoviesViewModel!!.networkStateLiveData.observe(this, Observer { networkState ->

            if (networkState != null) {
                adapter!!.setNetworkState(networkState)
            }
        })

        mRecyclerView!!.layoutManager = GridLayoutManager(this, 3)
        mRecyclerView!!.addItemDecoration(SpaceItemDecoration(4));
        mRecyclerView!!.adapter = adapter

        adapter!!.setOnItemClickListener(object : ImagesAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
                val i = Intent(applicationContext, Full_PageImages_Activity::class.java)
                startActivity(i)
            }


        })

    }

    companion object {
        private val TAG = "MainActivity"
    }
}
