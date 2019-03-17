package com.example.chrisl.speedfeed;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static String NETWORK_RESPONSE = "PLACEHOLDER";


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of NewsItem objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<NewsItem> extractNewsItems() {

        // Create an empty ArrayList that we can start adding News Items to
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        // Try to parse the network response. If there's a problem
        // an exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //get the data from the network
            getNetworkData();

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(NETWORK_RESPONSE);

            // Extract the JSONObject associated with the key called "response",
            // which represents a list of news items.
            JSONObject jsonResponseObject = baseJsonResponse.getJSONObject("response");

            //Create an Array of news items by using the key "results"
            JSONArray newsItemArray = jsonResponseObject.getJSONArray("results");

            // For each item in the Array, create a NewsItem object
            for (int i = 0; i < newsItemArray.length(); i++) {

                // Get a single news item at position i within the list of news items
                JSONObject currentNewsItem = newsItemArray.getJSONObject(i);

                // Extract the value for the key called "sectionId"
                String sectionName = currentNewsItem.getString("sectionName");

                // Extract the value for the key called "place"
                //String location = properties.getString("place");

                // Extract the value for the key called "time"
                //String time = properties.getString("time");

                // Create a new {@link Earthquake} object with the magnitude, location, and time
                // from the JSON response.
                //Earthquake earthquake = new Earthquake(magnitude, location, time);

                // Add the new {@link Earthquake} to the list of earthquakes.
                //earthquakes.add(earthquake);

                Log.i("NewsItem", sectionName);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON response", e);
        }

        // Return the list of earthquakes
        return newsItems;
    }

    private static String getNetworkData() {

        /** Sample JSON response for a USGS query */
         NETWORK_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":11802,\"startIndex\":1,\"pageSize\":3,\"currentPage\":1,\"pages\":3934,\"orderBy\":\"newest\",\"results\":[{\"id\":\"sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"type\":\"liveblog\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-17T04:40:11Z\",\"webTitle\":\"Australian Grand Prix: Formula One 2019 season opener – live!\",\"webUrl\":\"https://www.theguardian.com/sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"apiUrl\":\"https://content.guardianapis.com/sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"type\":\"liveblog\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-16T11:10:36Z\",\"webTitle\":\"Sportwatch: NRL, A-League, AFLW and more - as it happened\",\"webUrl\":\"https://www.theguardian.com/sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"apiUrl\":\"https://content.guardianapis.com/sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"sport/2019/mar/16/lewis-hamilton-claims-australian-grand-prix-pole-as-mercedes-shine\",\"type\":\"article\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-16T07:34:27Z\",\"webTitle\":\"Lewis Hamilton claims Australian Grand Prix pole as Mercedes shine\",\"webUrl\":\"https://www.theguardian.com/sport/2019/mar/16/lewis-hamilton-claims-australian-grand-prix-pole-as-mercedes-shine\",\"apiUrl\":\"https://content.guardianapis.com/sport/2019/mar/16/lewis-hamilton-claims-australian-grand-prix-pole-as-mercedes-shine\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"}]}}";

        return NETWORK_RESPONSE;

    }

}