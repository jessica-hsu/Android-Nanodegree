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
    public static final String LISTEN_API_APP = "android_capstone";
    public static final String LISTEN_API_URL = "https://listen-api.listennotes.com/api/v2/search";
    public static final String LISTEN_KEY = "116498299c5445e8b9f6db79cb6b2694";

    // error and no results code
    public static final int NO_INTERNET_ERROR = 0;
    public static final int GOOGLE_SIGN_IN_ERROR = 1;
    public static final int LISTEN_API_ERROR = 2;
    public static final int NO_RESULTS = 3;
    public static final int PLAY_PODCAST_ERROR = 4;
    public static final int NO_AUDIO_URL_ERROR = 5;

    // null values for podcast details
    public static final String NO_AUTHOR = "No author found.";
    public static final String NO_AUDIO_LENGTH = "Audio length not available.";
    public static final String NO_DESCRIPTION = "No description available";

    // saved instance keys
    public static final String INSTANCE_KEY_POSITION = "position";
    public static final String INSTANCE_KEY_STATE = "state";
    public static final String INSTANCE_KEY_WINDOW = "window";

    // misc values
    public static final String APP_NAME = "PodcastBeMine";
}
