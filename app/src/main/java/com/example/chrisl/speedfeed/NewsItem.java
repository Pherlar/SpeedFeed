package com.example.chrisl.speedfeed;

public class NewsItem {

    //Headline of the Story
    private String mHeadline;

    //section of the story
    private String mSection;

    //Author
    private String mAuthor;

    //Date published
    private String mDatePublished;

    //website URL of the story
    private String mUrl;

    //Type of the item
    private String mType;

     //Create a new NewsStory object.
     //and assign variable names to the parameters passed in to the method

    public NewsItem (String headline, String section, String author, String datePublished, String Url, String type){
        mHeadline = headline;
        mSection = section;
        mAuthor = author;
        mDatePublished = datePublished;
        mUrl = Url;
        mType = type;
    }

    /**
     * Getter methods to get the attributes
     */
    public String getHeadline(){return mHeadline;}

    public String getSection(){return mSection;}

    public String getType() {return mType;}

    public String getAuthor() {return mAuthor;}

    public String getDatePublished() {return mDatePublished;}

    public String getUrl() {return mUrl;}

}
