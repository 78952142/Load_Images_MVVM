package com.example.a80_tech_task.views.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.dasfilm.azzeddine.dasfilm.APIUtils.SpaceItemDecoration
import com.example.a80_tech_task.R
import com.example.a80_tech_task.viewmodels.ImagesViewModel
import com.example.a80_tech_task.views.Adapters.FullImageAdapter

class Full_PageImages_Activity : AppCompatActivity() {
    private var mMoviesViewModel: ImagesViewModel? = null
    private var mRecyclerView: RecyclerView? = null
    private var adapter: FullImageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate:")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_imagepage)

        mRecyclerView = findViewById(R.id.list_full_page)
        adapter = FullImageAdapter(this)
        mMoviesViewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        mMoviesViewModel!!.getMoviesInTheaterList().observe(this, Observer { movies ->
            Log.d(TAG, "List" + movies!!.size)
            adapter!!.submitList(movies)
        })
        mMoviesViewModel!!.networkStateLiveData.observe(this, Observer { networkState ->
            if (networkState != null) {
                adapter!!.setNetworkState(networkState)
            }
        })

        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView!!.addItemDecoration(SpaceItemDecoration(4));
        mRecyclerView!!.adapter = adapter

    }

    companion object {
        private val TAG = "MainActivity"
    }
}
