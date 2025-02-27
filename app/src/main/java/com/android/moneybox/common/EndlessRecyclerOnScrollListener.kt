package com.android.moneybox.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/***
 *
 * I got this from here.. was in a rush
 * https://github.com/shandilyaaman/SampleEndlessRecyclerView/blob/master/SampleEndlessRecyclerView/endlessrecyclerview/src/main/java/com/endlessrecyclerview/android/EndlessRecyclerOnScrollListener.java
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    //    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    /**
     * The total number of items in the dataset after the last load
     */
    private var mPreviousTotal = 0

    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var mLoading = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem =
            (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        val visibleThreshold = 5
        if (!mLoading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {
            // End has been reached
            onLoadMore()
            mLoading = true
        }
    }

    abstract fun onLoadMore()
}
