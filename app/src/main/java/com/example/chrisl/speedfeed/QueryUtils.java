package com.example.chrisl.speedfeed;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

                // Extract the value for the key called "sectionName"
                String sectionName = currentNewsItem.getString("sectionName");

                // Extract the value for the key called "place"
                String webTitle = currentNewsItem.getString("webTitle");

                // Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNewsItem.getString("webPublicationDate");

                //Extract the value for the key called "webUrl"
                String webUrl = currentNewsItem.getString("webUrl");

                //Extract the JSONarray for the key "tag"
                JSONArray tagsArray = currentNewsItem.getJSONArray("tags");

                //Assume Tags Array only has one entry
                JSONObject tagsObject = tagsArray.getJSONObject(0);
                //Extract the value for the Author (inside "tags" Array, key "WebTitle")
                String author = tagsObject.getString("webTitle");

                // Create a new NewsItem object with the attributes collected above
                NewsItem newsItem = new NewsItem(webTitle,sectionName,author,webPublicationDate,webUrl);

                // Add the new NewsItem to the list of NewsItems.
                newsItems.add(newsItem);

                Log.i("NewsItem", sectionName + " , " + webTitle + " , " + webPublicationDate + " , " + author + " , " + webUrl );

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the JSON response", e);
        }

        // Return the list of newsItems
        return newsItems;
    }





    private static String getNetworkData() {

        /** Sample JSON response for a USGS query */
         NETWORK_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":11803,\"startIndex\":1,\"pageSize\":3,\"currentPage\":1,\"pages\":3935,\"orderBy\":\"newest\",\"results\":[{\"id\":\"sport/2019/mar/17/valtteri-bottas-leads-from-first-turn-to-win-australian-grand-prix\",\"type\":\"article\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-17T07:11:29Z\",\"webTitle\":\"Valtteri Bottas dominates Australian Grand Prix to win Formula One opener\",\"webUrl\":\"https://www.theguardian.com/sport/2019/mar/17/valtteri-bottas-leads-from-first-turn-to-win-australian-grand-prix\",\"apiUrl\":\"https://content.guardianapis.com/sport/2019/mar/17/valtteri-bottas-leads-from-first-turn-to-win-australian-grand-prix\",\"tags\":[{\"id\":\"profile/gilesrichards\",\"type\":\"contributor\",\"webTitle\":\"Giles Richards\",\"webUrl\":\"https://www.theguardian.com/profile/gilesrichards\",\"apiUrl\":\"https://content.guardianapis.com/profile/gilesrichards\",\"references\":[],\"bio\":\"<p>Giles Richards is a Guardian sports writer</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2017/09/28/Giles_Richards_280.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2018/10/19/Giles_Richards,_L.png\",\"firstName\":\"Giles\",\"lastName\":\"Richards\",\"twitterHandle\":\"giles_richards\"}],\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"type\":\"liveblog\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-17T07:04:22Z\",\"webTitle\":\"Australian Grand Prix: Bottas wins Formula One season opener – as it happened\",\"webUrl\":\"https://www.theguardian.com/sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"apiUrl\":\"https://content.guardianapis.com/sport/live/2019/mar/17/australian-grand-prix-formula-one-2019-season-opener-live\",\"tags\":[{\"id\":\"profile/richard-gadsby\",\"type\":\"contributor\",\"webTitle\":\"Richard Gadsby\",\"webUrl\":\"https://www.theguardian.com/profile/richard-gadsby\",\"apiUrl\":\"https://content.guardianapis.com/profile/richard-gadsby\",\"references\":[],\"bio\":\"<p>Richard is a sports journalist based in Sydney. He has worked for the Sydney Morning Herald, Fox Sports and News Limited</p>\",\"firstName\":\"gadsby\",\"lastName\":\"richard\"}],\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"type\":\"liveblog\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2019-03-16T11:10:36Z\",\"webTitle\":\"Sportwatch: NRL, A-League, AFLW and more - as it happened\",\"webUrl\":\"https://www.theguardian.com/sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"apiUrl\":\"https://content.guardianapis.com/sport/live/2019/mar/16/sportwatch-nrl-a-league-aflw-and-more-live\",\"tags\":[{\"id\":\"profile/richard-parkin\",\"type\":\"contributor\",\"webTitle\":\"Richard Parkin\",\"webUrl\":\"https://www.theguardian.com/profile/richard-parkin\",\"apiUrl\":\"https://content.guardianapis.com/profile/richard-parkin\",\"references\":[],\"bio\":\"<p>Richard Parkin is a Sydney-based journalist and the casual sport editor at Guardian Australia. He was the football analyst on SBS's The Full Brazilian</p>\",\"bylineImageUrl\":\"https://uploads.guim.co.uk/2019/03/04/Richard_Parkin_small.jpg\",\"bylineLargeImageUrl\":\"https://uploads.guim.co.uk/2019/03/04/Richard_Parkinlarge.png\",\"firstName\":\"Richard\",\"lastName\":\"Parkin\",\"twitterHandle\":\"rrjparkin\"}],\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"}]}}";

        return NETWORK_RESPONSE;

    }



}