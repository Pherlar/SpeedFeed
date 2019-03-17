package com.example.chrisl.speedfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<NewsItem> newsItems = QueryUtils.extractNewsItems();

        //get a reference to the List View and attach the adapter to the list view
        ListView listView = findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        CustomAdapter mAdapter = new CustomAdapter(this, newsItems);

        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        listView.setAdapter(mAdapter);
    }
}
