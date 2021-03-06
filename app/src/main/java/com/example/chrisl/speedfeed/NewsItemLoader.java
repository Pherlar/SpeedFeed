package com.example.chrisl.speedfeed;

import java.util.List;
import android.content.AsyncTaskLoader;
import android.content.Context;

public class NewsItemLoader extends AsyncTaskLoader<List<NewsItem>> {


    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new News Item Loader.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsItemLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */

    @Override
    public List<NewsItem> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of NewsItems.
        List<NewsItem> newsItems = QueryUtils.getNetworkData(mUrl);
        return newsItems;
    }

}
