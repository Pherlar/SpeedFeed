package com.example.chrisl.speedfeed;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {

    //Variable declarations
    private TextView mEmptyStateTV;
    private static final int LOADER_ID = 1;
    private CustomAdapter mAdapter;
    private static final String REQUEST_URL =
            "https://content.guardianapis.com/search?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Load in custom Toolbar
        Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //get a reference to the List View and attach the adapter to the list view
        ListView listView = findViewById(R.id.list);

        // Create a new adapter that takes an empty list of newsItems as input
        mAdapter = new CustomAdapter(this, new ArrayList<NewsItem>());
        mEmptyStateTV = findViewById(R.id.empty_view);

        //set the empty adapter on the list view
        listView.setAdapter(mAdapter);

        //Handle on click events for each row item in adapter
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current newsItem that was clicked on
                NewsItem currentNewsItem = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsStoryUri = Uri.parse(currentNewsItem.getUrl());

                // Create a new intent to view the newsItem URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsStoryUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message

            mEmptyStateTV.setText(R.string.no_internet_connection);


        }

    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        //order-by=newest&format=json&section=sport&page-size=100&order-by=newest&show-tags=contributor&q=motorsports%20or%20formula%20one%20or%20f1%20&api-key=f9e65902-1b87-44a4-bcec-9924bd25c8fd"
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("section", "sport");
        uriBuilder.appendQueryParameter("page-size", "30");
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("q", "motorsports OR \"formula one\" OR f1 OR Supercars");
        uriBuilder.appendQueryParameter("api-key","f9e65902-1b87-44a4-bcec-9924bd25c8fd");
        return new NewsItemLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No News Items found."

        // Clear the adapter of previous news item data
        mAdapter.clear();

        // If there is a valid list of NewsItems, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsItems != null && !newsItems.isEmpty()) {
            mAdapter.addAll(newsItems);
            mEmptyStateTV.setVisibility(View.GONE);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }



}
