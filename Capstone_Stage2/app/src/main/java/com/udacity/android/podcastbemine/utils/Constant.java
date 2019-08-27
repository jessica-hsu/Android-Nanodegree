package com.udacity.android.podcastbemine.utils;

public class Constant {

    // intent keys
    public static final String INTENT_LABEL_USER_INFO = "userInfo";
    public static final String INTENT_LABEL_ERROR = "errorCode";
    public static final String INTENT_LABEL_PODCAST_LIST = "podcastList";
    public static final String INTENT_KEY_PODCAST = "podcastBundle";
    public static final String INTENT_LABEL_AUDIO_URL = "audioUrl";

    // API values
    public static final int RC_SIGN_IN = 9001;
    public static final String LISTEN_API_URL = "https://listen-api.listennotes.com/api/v2/search";
    public static final String LISTEN_KEY = "116498299c5445e8b9f6db79cb6b2694";

    // error and no results code
    public static final int NO_INTERNET_ERROR = 0;
    public static final int GOOGLE_SIGN_IN_ERROR = 1;
    public static final int LISTEN_API_ERROR = 2;
    public static final int NO_RESULTS = 3;
    public static final int NO_AUDIO_URL_ERROR = 5;
    public static final int DATABASE_ERROR = 6;

    // null values for podcast details
    public static final String NO_AUTHOR = "No author found.";
    public static final String NO_AUDIO_LENGTH = "Audio length not available.";
    public static final String NO_DESCRIPTION = "No description available";

    // saved instance keys
    public static final String INSTANCE_KEY_POSITION = "position";
    public static final String INSTANCE_KEY_STATE = "state";
    public static final String INSTANCE_KEY_WINDOW = "window";

    // Database stuff
    public static final String DB_NAME = "podcasts";
    public static final String DB_SEARCH_ID = "combinedId";
    public static final String DB_SEARCH_FAVORITES = "userId";
    public static final String ADD_DATABASE_MSG = "Added to favorites.";
    public static final String REMOVE_DATABASE_MSG = "Removed from favorites.";
    public static final String DEFAULT_NO_USER = "DEFAULT";

    // widget
    public static final String WIDGET_PREFERENCE = "podcastWidget";
    public static final String WIDGET_LABEL = "podcastTitleWidget";
    public static final String WIDGET_LABEL_2 = "podcastDescriptionWidget";
    public static final String WIDGET_ADDED = "Added to Widget";
    public static final String WIDGET_ERROR = "Error with widget";

    // logging tags
    public static final String FETCH_TASKS_ERROR_TAG = "FetchAsyncError";
    public static final String SEARCH_ERROR_TAG = "SearchError";
    public static final String PODCAST_LIST_ERROR_TAG = "ShowListError";
    public static final String NO_DETAILS_ERROR_TAG = "NoDetailsError";
    public static final String PLAY_ERROR_TAG = "PlayError";
    public static final String LOGOUT_ERROR_TAG = "LogoutError";
    public static final String ENDPOINT_ERROR_TAG = "EndpointError";
    public static final String HTTP_ERROR_TAG = "HttpError";
    public static final String JSON_ERROR_TAG = "JSONError";
    public static final String LOGIN_ERROR_TAG = "LoginError";
    public static final String WIDGET_ERROR_TAG = "WidgetError";


    // misc values
    public static final String APP_NAME = "PodcastBeMine";
    public static final String LOGOUT_ISSUES_MSG = "Unable to logout.";

}
