package com.cvm.android.dancesterz.utilities;

import android.content.Context;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.regex.Pattern;

/**
 * Created by Devalopment-1 on 15-12-2017.
 */

public class AppConstants {
    public static final int sDefaultTimeout = 3000;
    public static final int FADE_OUT = 1;
    public static final int SHOW_PROGRESS = 2;
    public static AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    public static final String validUsername = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
    public static final String validPassword = "^([a-zA-Z+]+[0-9+]+[&@!#+]+)$";
    public static final Pattern emailPattern = Patterns.EMAIL_ADDRESS;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int ONE_MINUTE = 60000;
    public static final int MAX_PROGRESS = 100;
    public static final int BACKGROUND_PROGRESS_BAR_WIDTH = 10;
    public static final int FORGROUND_PROGRESS_BAR_WIDTH = 15;
    public static final String VIDEO_FORMAT = ".mp4";
    public static final int COUNT_DOWN_MILLIS_IN_FUTURE = 5000;
    public static final int COUNT_DOWN_INTERVAL = 1000;
    public static final String BRODCAST_PLAY_NEW_AUDIO = "com.cvm.android.videoplayer.PlayNewAudio";
    public static final String ACTION_PLAY = "com.cvm.android.videoplayer.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.cvm.android.videoplayer.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.cvm.android.videoplayer.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.cvm.android.videoplayer.ACTION_NEXT";
    public static final String ACTION_STOP = "com.cvm.android.videoplayer.ACTION_STOP";
    public static final String PLAY_AUDIO = "audio";
    public static final String PLAY_VIDEO= "video";

    public static final String CHALLENGE_VIDEOID= "challenge_videoId";
    public static final String CHALLENGE_RESPONSE_ID ="challenge_responseId";
    public static final String CHALLENGE_ID= "challenge_Id";
    public static final String OWNER_USERID= "owner_videoId";
    public static final String TOTAL_VOTES= "Total_votes";
    public static final String CHALLENGE_AUDIOPATH= "owner_audioId";
    public static final String CHAUDIO_ID= "ChallengeaudioId";
    public static final String AUDIO_ID= "AudioId";
    public static final String CHALLENGE_NAME= "challenge_name";
    public static final String OWNER_NAME = "owner_name";
    public static final String OWNER_PROFILE_PIC = "owner_profile_pic";
    public static final String ACCEPTER_NAME = "accepter_name";
    public static final String WHOLE_DANCE_STYLES = "dancestyles";
    public static final String ACCEPTER_PROFILE_PIC = "accepter_profile_pic";
    public static final String ACCEPTER_LIST= "alarrayList";
    public static final String CHALLENGE_LIST= "challengeList";


    public static final String PREFERENCE_KEY = "LoginPref";
    public static final String KEY_TOKEN = "TokenKey";
    public static final String KEY_REFRESHTOKEN = "RefreshTokenKey";
    public static final String KEY_USERID = "UserIdKey";
    public static final String KEY_USERNAME = "UsernameKey";
    public static final String KEY_PROFILE_PIC = "ProfilePicKey";
    public static final String KEY_NICK_NAME = "NickNameKey";

    public static final String MALE = "m";
    public static final  String FEMALE = "f";
    public static final   String OTHER = "o";
    public static final   String MALE_TEXT = "Male";
    public static final   String FEMALE_TEXT = "Female";
    public static final   String OTHER_TEXT = "Other";
    public static Context contextOfApplication;
    public static    String errmessage = "";
    public static    String errorCode = "";
    public static    boolean logOut = false;
    public static final String KEY_LOGGEDIN = "LOGGEDIN";


    public static final String URL = "url";
    public static final String SOURCEPATH = "sourcepath";
    public static final String HEADING = "heading";
    public static final String HOT_CHALLENGES = "Hot Challenges";
    public static final String TRENDING_NOW = "Trending Now";
    public static final String MY_VIDEOS = "My videos & Expired Challenges";
    public static final String ACTIVE_CHALLENGES = "Active Challenges";
    public static final String RECENT_ACTIVITIES = "Recent activities";
    public static final String NEWS_FEEDS = "News Feeds";
    public static final String LAST_WEEK = "Last week";
    public static final String SEE_ALL = "seeall";


}
