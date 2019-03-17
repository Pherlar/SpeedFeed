package com.example.chrisl.speedfeed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CustomAdapter extends ArrayAdapter<NewsItem> {

    public CustomAdapter(Context context, List<NewsItem> newsItems){
        super(context, 0, newsItems);
    }

    /**
     * Returns a list item view that displays relevant news item information
     * in the list of News items
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the news item at the given position in the list of NewsItems
        NewsItem currentNewsItem = getItem(position);

        //create a date object and format the date using helper functions
        String datePublishedString = currentNewsItem.getDatePublished();

        //Covert date string to java date object
        Date datePublished = stringToDate(datePublishedString);

        //format date to two separate fields
        String formattedDatePublished = formatDate(datePublished);
        String formattedTimePublished = formatTime(datePublished);

        // Find the TextView with view ID headline_tv
        TextView headlineTVHolder = listItemView.findViewById(R.id.headline_tv);
        // Display the headline of the current news story in that TextView
        headlineTVHolder.setText(currentNewsItem.getHeadline());

        // Find the TextView with view ID author_tv
        TextView authorTVHolder = listItemView.findViewById(R.id.author_tv);
        // Display the location of the current earthquake in that TextView
        authorTVHolder.setText(currentNewsItem.getAuthor());

        // Find the TextView with view ID date_tv
        TextView dateTVHolder = listItemView.findViewById(R.id.date_tv);
        // Display the date of the current earthquake in that TextView
        dateTVHolder.setText(formattedDatePublished);

        // Find the TextView with view ID time_tv
        TextView timeTVHolder = listItemView.findViewById(R.id.time_tv);
        // Display the date of the current earthquake in that TextView
        timeTVHolder.setText(formattedTimePublished);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }


    /**
     * Convert the String Date to a Java Date object
     */
    private Date stringToDate(String dateObject) {
        //format a date in this format "2019-03-17T07:04:22Z"
        String mDateObject = dateObject;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date d = sdf.parse(mDateObject);
            return d;
        } catch (ParseException ex) {
            Log.e("Exception", ex.getLocalizedMessage());
                    }
        return null;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}