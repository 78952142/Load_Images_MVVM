package com.example.a80_tech_task.views.Adapters

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.a80_tech_task.APIUtils.Image
import com.example.a80_tech_task.APIUtils.NetworkState
import com.example.a80_tech_task.Entities.Movie
import com.example.a80_tech_task.R

class FullImageAdapter(private val mContext: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(Movie.DIFF_CALL) {
    private var mNetworkState: NetworkState? = null

    val isLoadingData: Boolean
        get() = mNetworkState != null && mNetworkState != NetworkState.LOADED


    override fun getItemViewType(position: Int): Int {
        return if (isLoadingData && position == itemCount - 1) LOAD_ITEM_VIEW_TYPE else MOVIE_ITEM_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View
        if (viewType == MOVIE_ITEM_VIEW_TYPE) {
            view = inflater.inflate(R.layout.full_images_item, parent, false)
            return MovieViewHolder(view)
        } else {
            view = inflater.inflate(R.layout.load_progress_item, parent, false)
            return ProgressViewHolder(view)
        }

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image: Image
        if (holder is MovieViewHolder) {
            val movie = getItem(position)
            holder.bind(movie, mContext)

        }

    }
    fun setNetworkState(networkState: NetworkState) {
        val prevState = networkState
        val wasLoading = isLoadingData
        mNetworkState = networkState
        val willLoad = isLoadingData
        if (wasLoading != willLoad) {
            if (wasLoading) notifyItemRemoved(itemCount) else notifyItemInserted(itemCount)
        }
    }

    private class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var moviePosterImageView: ImageView
        init {
            moviePosterImageView = itemView.findViewById(R.id.movie_poster)

        }
        fun bind(movie: Movie?, context: Context) {

            try {
                val image = Image(movie!!.posterImageKey!!)
                Glide.with(context).load(image.highQualityImagePath).into(moviePosterImageView)

            } catch (e: Exception) {
            }

        }
    }

    private class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private val TAG = "ImagesAdapter"
        val MOVIE_ITEM_VIEW_TYPE = 1
        val LOAD_ITEM_VIEW_TYPE = 0
    }
}