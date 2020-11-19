package com.b4dnetwork.godotfb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;

import java.util.Arrays;
import java.util.List;

public class FacebookAnalytics extends GodotPlugin {
    private Activity activity = null;
    private AppEventsLogger logger;
    private static final String TAG = "FBAnalytics";


    public FacebookAnalytics(Godot godot) {
        super(godot);
        activity = godot;
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "FacebookAnalytics";
    }

    @NonNull
    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList(
                "Init","setAutoLogAppEventsEnabled",
                "setAutoInitEnabled","setAdvertiserIdCollectionEnabled",
                "logAdClickEvent","setDebugEnabled","logCustomEvent","logCustomSumEvent"
        );
    }

    /**
     * Initialize Facebook ads sdk
     * @param gameId Facebook game ID
     */
    public void Init(String gameId){
        if(FacebookSdk.isInitialized()) return;
        FacebookSdk.setApplicationId(gameId);
        FacebookSdk.sdkInitialize(activity);
        logger = AppEventsLogger.newLogger(activity);
        Log.d(TAG, "Facebook Analytics Initialized ");
    }


    /**
     * Enable(true) or disable (false) auto log app events
     * @param autoLogEnabled Enable =>true or disable => false
     */
    public void setAutoLogAppEventsEnabled(boolean autoLogEnabled){
        FacebookSdk.setAutoLogAppEventsEnabled(autoLogEnabled);
        Log.d(TAG, "setAutoLogAppEventsEnabled");
    }

    /**
     * Enable or disable auto FB sdk initialization
     * @param autoInitEnabled enable => true disable => false
     */
    public void setAutoInitEnabled(boolean autoInitEnabled){
        FacebookSdk.setAutoInitEnabled(autoInitEnabled);
        Log.d(TAG, "setAutoInitEnabled");
    }

    /**
     * Enable or disable Advertiser Id Collection
     * @param idCollectionEnabled : enable => true or disable => false
     */
    public void setAdvertiserIdCollectionEnabled(boolean idCollectionEnabled){
        FacebookSdk.setAdvertiserIDCollectionEnabled(idCollectionEnabled);
        Log.d(TAG, "setAdvertiserIdCollectionEnabled");
    }

    /**
     * Enable or disable debug mode
     * @param debugEnabled enable => true , disable=> false
     */
    public void setDebugEnabled(boolean debugEnabled){
        FacebookSdk.setIsDebugEnabled(debugEnabled);
        if(!debugEnabled) return;
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        Log.d(TAG, "setDebugEnabled");
    }


    //Events

    /**
     *Log Ad click Event with a given ad name
     * @param adType : string type(name) of ad eg.
     */
    public void logAdClickEvent (String adType) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_AD_TYPE, adType);
        logger.logEvent(AppEventsConstants.EVENT_NAME_AD_CLICK, params);
        Log.d(TAG, "logAdClickEvent: " + adType);
    }

    /**
     *Log a custom Facebook app event e.g : (LevelFinishEvent)
     * @param eventName event name
     */
    public void logCustomEvent(String eventName){
        logger.logEvent(eventName);
        Log.d(TAG, "logCustomEvent: " + eventName);
    }

    /**
     * Log a custom Event with a value.
     * @param eventName Event Name
     * @param valueToSum Event value to sum
     */
    public void logCustomSumEvent(String eventName , int valueToSum){
        logger.logEvent(eventName,valueToSum);
        Log.d(TAG, "logCustomSumEvent: " + eventName + "value : " + valueToSum);
    }

}
