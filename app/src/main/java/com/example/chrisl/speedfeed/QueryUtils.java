package com.example.chrisl.speedfeed;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    //Tag for the log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


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
    public static List<NewsItem> extractNewsItemsFromJson(String newsItemJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsItemJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding News Items to
        List<NewsItem> newsItems = new ArrayList<>();

        // Try to parse the network response. If there's a problem
        // an exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(newsItemJSON);

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
                String type = currentNewsItem.getString("type");

                //Extract the value for the key called "type"
                String webUrl = currentNewsItem.getString("webUrl");

                //In order to extract the author name we need to get it from the 'tags' object
                //Extract the JSONarray for the key "tag"
                JSONArray tagsArray = currentNewsItem.getJSONArray("tags");


                //if Tags Array has one entry, get the Author name out
                //String author = Resources.getSystem().getString(R.string.empty_string);
                //if (tagsArray.length()==1){
                JSONObject tagsObject = tagsArray.getJSONObject(0);
                //Extract the value for the Author (inside "tags" Array, key "WebTitle")
                String author = tagsObject.getString("webTitle");
                //else if there is no tags array
                //else{
                //    return null;


                // Create a new NewsItem object with the attributes collected above
                NewsItem newsItem = new NewsItem(webTitle, sectionName, author, webPublicationDate, webUrl, type);

                // Add the new NewsItem to the list of NewsItems.
                newsItems.add(newsItem);

                Log.i("NewsItem", sectionName + " , " + webTitle + " , " + webPublicationDate + " , " + author + " , " + webUrl);
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

    public static List<NewsItem> getNetworkData(String requestUrl) {

        //create URL object
        URL url = createUrl(requestUrl);

        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem making the Http request");
        }

        List<NewsItem> newsItems = extractNewsItemsFromJson(jsonResponse);

        //Return the list of newsItems
        return newsItems;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the newsItem JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }



}