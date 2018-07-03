package com.evgeniy.restapp.RecyclerView

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var previousTotal = 0
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (previousTotal > totalItemCount) {
            previousTotal = 0
        }


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }


        val visibleThreshold = 1

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {

            onLoadMore()

            loading = true
        }
    }


    abstract fun onLoadMore()

}