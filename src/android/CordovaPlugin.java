package io.repro.cordova;

import android.app.AppOpsManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;
import android.net.Uri;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.repro.android.Repro;
import io.repro.android.CordovaBridge;
import io.repro.android.newsfeed.NewsFeedEntry;
import io.repro.android.newsfeed.NewsFeedCampaignType;
import io.repro.android.remoteconfig.RemoteConfigListener;
import io.repro.android.remoteconfig.RemoteConfigValue;
import io.repro.android.user.UserProfileGender;
import io.repro.android.user.UserProfilePrefecture;

/**
 * Created by nekoe on 1/15/16.
 * Copyright (c) 2016 Repro Inc. All rights reserved.
 */
public final class CordovaPlugin extends org.apache.cordova.CordovaPlugin {

    private static final String REPRO_CORDOVA_BRIDGE_VERSION = "6.24.4";

    private interface CordovaReproCommand {
        boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException;
    }

    private static final Map<String, CordovaReproCommand> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put("setup", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setup(args, callbackContext);
            }
        });
        COMMAND_MAP.put("optIn", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.optIn(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setLogLevel", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setLogLevel(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserID", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserID(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setStringUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setStringUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setIntUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setIntUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setDoubleUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setDoubleUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setDateUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setDateUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserGender", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserGender(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserEmailAddress", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserEmailAddress(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserResidencePrefecture", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserResidencePrefecture(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserDateOfBirth", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserDateOfBirth(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setUserAge", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setUserAge(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentStringUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentStringUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentIntUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentIntUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentDoubleUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentDoubleUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentDateUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentDateUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("incrementIntUserProfileBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.incrementIntUserProfileBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("decrementIntUserProfileBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.decrementIntUserProfileBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("incrementDoubleUserProfileBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.incrementDoubleUserProfileBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("decrementDoubleUserProfileBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.decrementDoubleUserProfileBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentUserGender", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentUserGender(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentUserEmailAddress", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentUserEmailAddress(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentUserResidencePrefecture", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentUserResidencePrefecture(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentUserDateOfBirth", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentUserDateOfBirth(args, callbackContext);
            }
        });
        COMMAND_MAP.put("onlySetIfAbsentUserAge", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.onlySetIfAbsentUserAge(args, callbackContext);
            }
        });
        COMMAND_MAP.put("incrementUserAgeBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.incrementUserAgeBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("decrementUserAgeBy", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.decrementUserAgeBy(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserProfile", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserProfile(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserGender", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserGender(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserEmailAddress", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserEmailAddress(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserResidencePrefecture", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserResidencePrefecture(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserDateOfBirth", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserDateOfBirth(args, callbackContext);
            }
        });
        COMMAND_MAP.put("deleteUserAge", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.deleteUserAge(args, callbackContext);
            }
        });
        COMMAND_MAP.put("track", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.track(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackWithProperties", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackWithProperties(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackViewContent", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackViewContent(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackSearch", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackSearch(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackAddToCart", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackAddToCart(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackAddToWishlist", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackAddToWishlist(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackInitiateCheckout", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackInitiateCheckout(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackAddPaymentInfo", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackAddPaymentInfo(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackPurchase", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackPurchase(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackShare", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackShare(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackLead", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackLead(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackCompleteRegistration", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackCompleteRegistration(args, callbackContext);
            }
        });
        COMMAND_MAP.put("enableInAppMessagesOnForegroundTransition", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.enableInAppMessagesOnForegroundTransition(args, callbackContext);
            }
        });
        COMMAND_MAP.put("disableInAppMessagesOnForegroundTransition", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.disableInAppMessagesOnForegroundTransition(args, callbackContext);
            }
        });
        COMMAND_MAP.put("enablePushNotification", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.enablePushNotification(args, callbackContext);
            }
        });
        COMMAND_MAP.put("enablePushNotificationForIOS", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return true;
            }
        });
        COMMAND_MAP.put("getUserID", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getUserID(args, callbackContext);
            }
        });
        COMMAND_MAP.put("getDeviceID", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getDeviceID(args, callbackContext);
            }
        });
        COMMAND_MAP.put("trackNotificationOpened", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.trackNotificationOpened(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setSilverEggCookie", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setSilverEggCookie(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setSilverEggProdKey", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setSilverEggProdKey(args, callbackContext);
            }
        });
        COMMAND_MAP.put("linkLineID", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.linkLineID(args, callbackContext);
            }
        });
        COMMAND_MAP.put("unlinkLineID", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.unlinkLineID(args, callbackContext);
            }
        });
        COMMAND_MAP.put("getNewsFeedsWithLimit", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getNewsFeedsWithLimit(args, callbackContext);
            }
        });
        COMMAND_MAP.put("getNewsFeedsWithLimitAndOffsetId", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getNewsFeedsWithLimitAndOffsetId(args, callbackContext);
            }
        });
        COMMAND_MAP.put("getNewsFeedsWithLimitAndCampaignType", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getNewsFeedsWithLimitAndCampaignType(args, callbackContext);
            }
        });
        COMMAND_MAP.put("getNewsFeedsWithLimitAndOffsetIdAndCampaignType", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.getNewsFeedsWithLimitAndOffsetIdAndCampaignType(args, callbackContext);
            }
        });
        COMMAND_MAP.put("updateNewsFeeds", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.updateNewsFeeds(args, callbackContext);
            }
        });
        COMMAND_MAP.put("setOpenUrlCallback", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.setOpenUrlCallback(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_fetch", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_fetch(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_activateFetched", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_activateFetched(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_setDefaultsFromJson", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_setDefaultsFromJson(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_setDefaultsFromJsonString", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_setDefaultsFromJsonString(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_getAllValues", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_getAllValues(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_getAllValuesWithPrefix", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_getAllValuesWithPrefix(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_getValue", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_getValue(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_getLocalDefaultValue", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_getLocalDefaultValue(args, callbackContext);
            }
        });
        COMMAND_MAP.put("remoteConfig_forceReset", new CordovaReproCommand() {
            public boolean execute(CordovaPlugin plugin, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
                return plugin.remoteConfig_forceReset(args, callbackContext);
            }
        });
    }

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    static {
        sDateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
    }

    // Cordova I/F

    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        CordovaReproCommand command = COMMAND_MAP.get(action);
        if (command != null) {
            return command.execute(this, args, callbackContext);
        }
        return false;
    }

    private void passPlatformValues() {
        final Map<String, Object> platformValues = new HashMap<>();
        platformValues.put("sub_sdk_platform", "cordova");
        platformValues.put("sub_sdk_bridge_version",  REPRO_CORDOVA_BRIDGE_VERSION);

        try {
            Class cordovaKlass = Class.forName("org.apache.cordova.CordovaWebView");
            Field verField = cordovaKlass.getDeclaredField("CORDOVA_VERSION");
            platformValues.put("sub_sdk_platform_version", (String)verField.get(null)); // null because static field
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
            Method method = Repro.class.getDeclaredMethod("_passRuntimeValues", Map.class);
            method.setAccessible(true);
            method.invoke(null, platformValues);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // API implementation
    // CordovaArgs: https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaArgs.java

    private boolean setup(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String token = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                passPlatformValues();
                CordovaBridge.startSession(token);
                return null;
            }
        });

        return true;
    }

    private boolean optIn(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final boolean endUserOptedIn = args.getBoolean(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.optIn(endUserOptedIn);
                return null;
            }
        });

        return true;
    }

    private boolean setLogLevel(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String logLevel = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                if ("Debug".equals(logLevel)) {
                    Repro.setLogLevel(android.util.Log.DEBUG);
                } else if ("Info".equals(logLevel)) {
                    Repro.setLogLevel(android.util.Log.INFO);
                } else if ("Warn".equals(logLevel)) {
                    Repro.setLogLevel(android.util.Log.WARN);
                } else if ("Error".equals(logLevel)) {
                    Repro.setLogLevel(android.util.Log.ERROR);
                }
                return null;
            }
        });

        return true;
    }

    private boolean setUserID(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String userId = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserID(userId);
                return null;
            }
        });

        return true;
    }

    private boolean setStringUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final String value = args.getString(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setStringUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean setIntUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final int value = args.getInt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setIntUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean setDoubleUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final double value = args.getDouble(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setDoubleUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean setDateUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final long value = args.getLong(1);
        final Date date = new Date(value);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setDateUserProfile(key, date);
                return null;
            }
        });

        return true;
    }

    private boolean setUserGender(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int gender = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserGender(UserProfileGender.values()[gender]);
                return null;
            }
        });

        return true;
    }
    
    private boolean setUserEmailAddress(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String email = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserEmailAddress(email);
                return null;
            }
        });

        return true;
    }

    private boolean setUserResidencePrefecture(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int prefecture = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserResidencePrefecture(UserProfilePrefecture.values()[prefecture - 1]);
                return null;
            }
        });

        return true;
    }

    private boolean setUserDateOfBirth(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final long value = args.getLong(0);
        final Date date = new Date(value);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserDateOfBirth(date);
                return null;
            }
        });

        return true;
    }

    
    private boolean setUserAge(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int age = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setUserAge(age);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentStringUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final String value = args.getString(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentStringUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentIntUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final int value = args.getInt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentIntUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentDoubleUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final double value = args.getDouble(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentDoubleUserProfile(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentDateUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final long timestamp = args.getLong(1);
        final Date date = new Date(timestamp);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentDateUserProfile(key, date);
                return null;
            }
        });

        return true;
    }

    private boolean incrementIntUserProfileBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final int value = args.getInt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.incrementIntUserProfileBy(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean decrementIntUserProfileBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final int value = args.getInt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.decrementIntUserProfileBy(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean incrementDoubleUserProfileBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final double value = args.getDouble(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.incrementDoubleUserProfileBy(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean decrementDoubleUserProfileBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final double value = args.getDouble(1);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.decrementDoubleUserProfileBy(key, value);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentUserGender(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int gender = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentUserGender(UserProfileGender.values()[gender]);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentUserEmailAddress(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String email = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentUserEmailAddress(email);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentUserResidencePrefecture(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int prefecture = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentUserResidencePrefecture(UserProfilePrefecture.values()[prefecture - 1]);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentUserDateOfBirth(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final long value = args.getLong(0);
        final Date date = new Date(value);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentUserDateOfBirth(date);
                return null;
            }
        });

        return true;
    }

    private boolean onlySetIfAbsentUserAge(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int age = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.onlySetIfAbsentUserAge(age);
                return null;
            }
        });

        return true;
    }

    private boolean incrementUserAgeBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int value = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.incrementUserAgeBy(value);
                return null;
            }
        });

        return true;
    }

    private boolean decrementUserAgeBy(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final int value = args.getInt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.decrementUserAgeBy(value);
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserProfile(key);
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserGender(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserGender();
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserEmailAddress(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserEmailAddress();
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserResidencePrefecture(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserResidencePrefecture();
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserDateOfBirth(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserDateOfBirth();
                return null;
            }
        });

        return true;
    }

    private boolean deleteUserAge(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.deleteUserAge();
                return null;
            }
        });

        return true;
    }


    private boolean track(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String name = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.track(name);
                return null;
            }
        });

        return true;
    }

    private boolean trackWithProperties(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String name = args.getString(0);
        final Object properties = args.opt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    callTrackAPI(name, properties);
                } catch (JSONException e) {
                    android.util.Log.e("Repro", "Didn't track custom event \"" + name + "\": Invalid second argument for trackWithProperties.", e);
                }
                return null;
            }

            private void callTrackAPI(final String name, final Object properties) throws JSONException {
                if (properties == null || properties == JSONObject.NULL) {
                    Repro.track(name);
                } else if (properties instanceof JSONObject) {
                    Repro.track(name, jsonToMap((JSONObject)properties));
                } else if (properties instanceof String) {
                    android.util.Log.w("Repro", "trackWithProperties(String, String) will be deprecated. Use trackWithProperties(String, JSON) instead.");
                    Repro.track(name, jsonToMap(new JSONObject((String)properties)));
                } else {
                    android.util.Log.e("Repro", "Didn't track custom event \"" + name + "\": Invalid second argument for trackWithProperties. " + properties.getClass().getName() + " is not allowed.");
                }
            }

            private Map<String, Object> jsonToMap(final JSONObject json) throws JSONException {
              return new HashMap<String, Object>() {{
                  final Iterator<String> it = json.keys();
                  while (it.hasNext()) {
                      final String key = it.next();
                      final Object value = json.get(key);
                      put(key, value);
                  }
              }};
            }
        });

        return true;
    }

    private boolean trackViewContent(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);
        final Object arg1 = args.opt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                if (!(arg0 instanceof String)) {
                    android.util.Log.e("Repro", "Didn't track standard event \"view_content\": ContentID is required, and should be String. null or undefined is not allowed.");
                    return null;
                }
                final String contentId = (String)arg0;

                try {
                    Repro.trackViewContent(contentId, StandardEventPropertiesFactory.convertToViewContentProperties(arg1));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"view_content\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackSearch(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackSearch(StandardEventPropertiesFactory.convertToSearchProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"search\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackAddToCart(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);
        final Object arg1 = args.opt(1);

        callAPI(new API(callbackContext) {
            Void api() {
                if (!(arg0 instanceof String)) {
                    android.util.Log.e("Repro", "Didn't track standard event \"add_to_cart\": ContentID is required, and should be String. null or undefined is not allowed.");
                    return null;
                }
                final String contentId = (String)arg0;

                try {
                    Repro.trackAddToCart(contentId, StandardEventPropertiesFactory.convertToAddToCartProperties(arg1));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"add_to_cart\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackAddToWishlist(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackAddToWishlist(StandardEventPropertiesFactory.convertToAddToWishlistProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"add_to_wishlist\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackInitiateCheckout(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackInitiateCheckout(StandardEventPropertiesFactory.convertToInitiateCheckoutProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"initiate_checkout\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackAddPaymentInfo(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackAddPaymentInfo(StandardEventPropertiesFactory.convertToAddPaymentInfoProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"add_payment_info\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackPurchase(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);
        final Object arg1 = args.opt(1);
        final Object arg2 = args.opt(2);
        final Object arg3 = args.opt(3);

        callAPI(new API(callbackContext) {
            Void api() {
                if (!(arg0 instanceof String)) {
                    android.util.Log.e("Repro", "Didn't track standard event \"purchase\": ContentID is required, and should be String. null or undefined is not allowed.");
                    return null;
                }
                final String contentId = (String)arg0;

                if (!(arg1 instanceof Number)) {
                    android.util.Log.e("Repro", "Didn't track standard event \"purchase\": value is required, and should be number. null or undefined is not allowed.");
                    return null;
                }
                final double value = ((Number)arg1).doubleValue();

                if (!(arg2 instanceof String)) {
                    android.util.Log.e("Repro", "Didn't track standard event \"purchase\": currency is required, and should be String. null or undefined is not allowed.");
                    return null;
                }
                final String currency = (String)arg2;

                try {
                    Repro.trackPurchase(contentId, value, currency, StandardEventPropertiesFactory.convertToPurchaseProperties(arg3));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"purchase\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackShare(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackShare(StandardEventPropertiesFactory.convertToShareProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"share\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackLead(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackLead(StandardEventPropertiesFactory.convertToLeadProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"lead\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean trackCompleteRegistration(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object arg0 = args.opt(0);

        callAPI(new API(callbackContext) {
            Void api() {
                try {
                    Repro.trackCompleteRegistration(StandardEventPropertiesFactory.convertToCompleteRegistrationProperties(arg0));
                } catch (Exception e) {
                    android.util.Log.e("Repro", "Didn't track standard event \"complete_registration\": " + e.getMessage());
                }
                return null;
            }
        });

        return true;
    }

    private boolean enableInAppMessagesOnForegroundTransition(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.enableInAppMessagesOnForegroundTransition(cordova.getActivity());
                return null;
            }
        });
        return true;
    }

    private boolean disableInAppMessagesOnForegroundTransition(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.disableInAppMessagesOnForegroundTransition();
                return null;
            }
        });
        return true;
    }

    private boolean enablePushNotification(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.enablePushNotification();
                return null;
            }
        });

        return true;
    }

    private boolean getUserID(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            String api() {
                return Repro.getUserID();
            }
        });

        return true;
    }

    private boolean getDeviceID(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        callAPI(new API(callbackContext) {
            String api() {
                return Repro.getDeviceID();
            }
        });

        return true;
    }

    private boolean trackNotificationOpened(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String notificationId = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.trackNotificationOpened(notificationId);
                return null;
            }
        });

        return true;
    }

    private boolean setSilverEggCookie(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object cookie = args.opt(0);
        if (!(cookie instanceof String)) {
            android.util.Log.e("Repro", "Didn't set silver egg cookie: silver egg cookie is required, and should be String. null or undefined is not allowed.");
            return true;
        }

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setSilverEggCookie((String)cookie);
                return null;
            }
        });

        return true;
    }

    private boolean setSilverEggProdKey(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object prodKey = args.opt(0);
        if (!(prodKey instanceof String)) {
            android.util.Log.e("Repro", "Didn't set silver egg prod key: silver egg prod key is required, and should be String. null or undefined is not allowed.");
            return true;
        }

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.setSilverEggProdKey((String)prodKey);
                return null;
            }
        });

        return true;
    }

    private boolean linkLineID(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object lineUserId = args.opt(0);
        final Object lineChannelId = args.opt(1);
        if (!(lineUserId instanceof String) || !(lineChannelId instanceof String)) {
            android.util.Log.e("Repro", "Didn't link LINE ID: LINE user ID and LINE channel ID are required, and should be String. null or undefined is not allowed.");
            return true;
        }

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.linkLineID((String)lineUserId, (String)lineChannelId);
                return null;
            }
        });

        return true;
    }

    private boolean unlinkLineID(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object lineUserId = args.opt(0);
        final Object lineChannelId = args.opt(1);
        if (!(lineUserId instanceof String) || !(lineChannelId instanceof String)) {
            android.util.Log.e("Repro", "Didn't unlink LINE ID: LINE user ID and LINE channel ID are required, and should be String. null or undefined is not allowed.");
            return true;
        }

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.unlinkLineID((String)lineUserId, (String)lineChannelId);
                return null;
            }
        });

        return true;
    }

    private boolean getNewsFeedsWithLimit(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object limit = args.opt(0);
        if (!isValidNewsFeedRequestNumericParam(limit)) {
            android.util.Log.e("Repro", "Didn't get NewsFeed: limit should be Number and more than 0.");
            return true;
        }

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                JSONArray arr = new JSONArray();
                try {
                    List<NewsFeedEntry> newsFeedEntries = Repro.getNewsFeeds(((Number) limit).intValue());
                    for (NewsFeedEntry newsFeedEntry : newsFeedEntries) {
                        arr.put(newsFeedEntryToJSONObject(newsFeedEntry));
                    }
                    callbackContext.success(arr);
                } catch (Exception e) {
                    callbackContext.error("Failed to get NewsFeeds: " + e.getMessage());
                }
            }
        });

        return true;
    }

    private boolean getNewsFeedsWithLimitAndOffsetId(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object limit = args.opt(0);
        if (!isValidNewsFeedRequestNumericParam(limit)) {
            android.util.Log.e("Repro", "Didn't get NewsFeed: limit should be Number and more than 0.");
            return true;
        }

        final Object offsetId = args.opt(1);
        if (!isValidNewsFeedRequestNumericParam(offsetId)) {
            android.util.Log.e("Repro", "Didn't get NewsFeed: offset id should be Number and more than 0.");
            return true;
        }

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                JSONArray arr = new JSONArray();
                try {
                    List<NewsFeedEntry> newsFeedEntries = Repro.getNewsFeeds(((Number) limit).intValue(), ((Number) offsetId).intValue());
                    for (NewsFeedEntry newsFeedEntry : newsFeedEntries) {
                        arr.put(newsFeedEntryToJSONObject(newsFeedEntry));
                    }
                    callbackContext.success(arr);
                } catch (Exception e) {
                    callbackContext.error("Failed to get NewsFeeds: " + e.getMessage());
                }
            }
        });

        return true;
    }

    private boolean getNewsFeedsWithLimitAndCampaignType(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object limit = args.opt(0);
        if (!isValidNewsFeedRequestNumericParam(limit)) {
            android.util.Log.e("Repro", "Cannot get NewsFeed: limit should be Number and more than 0.");
            return true;
        }

        final NewsFeedCampaignType mappedCampaignType = mapToCampaignTypes(args.optString(1));
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                JSONArray arr = new JSONArray();
                try {
                    List<NewsFeedEntry> newsFeedEntries = Repro.getNewsFeeds(((Number) limit).intValue(), mappedCampaignType);
                    for (NewsFeedEntry newsFeedEntry : newsFeedEntries) {
                        arr.put(newsFeedEntryToJSONObject(newsFeedEntry));
                    }
                    callbackContext.success(arr);
                } catch (Exception e) {
                    callbackContext.error("Failed to get NewsFeeds: " + e.getMessage());
                }
            }
        });

        return true;
    }

    private boolean getNewsFeedsWithLimitAndOffsetIdAndCampaignType(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final Object limit = args.opt(0);
        if (!isValidNewsFeedRequestNumericParam(limit)) {
            android.util.Log.e("Repro", "Cannot get NewsFeed: limit should be Number and more than 0.");
            return true;
        }

        final Object offsetId = args.opt(1);
        if (!isValidNewsFeedRequestNumericParam(offsetId)) {
            android.util.Log.e("Repro", "Cannot get NewsFeed: offset id should be Number and more than 0.");
            return true;
        }

        final NewsFeedCampaignType mappedCampaignType = mapToCampaignTypes(args.optString(2));
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                JSONArray arr = new JSONArray();
                try {
                    List<NewsFeedEntry> newsFeedEntries = Repro.getNewsFeeds(((Number) limit).intValue(), ((Number) offsetId).intValue(), mappedCampaignType);
                    for (NewsFeedEntry newsFeedEntry : newsFeedEntries) {
                        arr.put(newsFeedEntryToJSONObject(newsFeedEntry));
                    }
                    callbackContext.success(arr);
                } catch (Exception e) {
                    callbackContext.error("Failed to get NewsFeeds: " + e.getMessage());
                }
            }
        });

        return true;
    }

    private NewsFeedCampaignType mapToCampaignTypes(final String campaignType) {
        if (campaignType == null) {
            return NewsFeedCampaignType.Unknown;
        }

        if (campaignType.equals(NewsFeedCampaignType.PushNotification.getValue())) {
            return NewsFeedCampaignType.PushNotification;
        } else if (campaignType.equals(NewsFeedCampaignType.InAppMessage.getValue())) {
            return NewsFeedCampaignType.InAppMessage;
        } else if (campaignType.equals(NewsFeedCampaignType.WebMessage.getValue())) {
            return NewsFeedCampaignType.WebMessage;
        }  else if (campaignType.equals(NewsFeedCampaignType.All.getValue())) {
            return NewsFeedCampaignType.All;
        } else {
            return NewsFeedCampaignType.Unknown;
        }
    }

    private boolean isValidNewsFeedRequestNumericParam(Object param) {
        return param instanceof Number && ((Number) param).intValue() > 0;
    }

    private JSONObject newsFeedEntryToJSONObject(NewsFeedEntry entry) throws Exception {
        JSONObject entryObject = new JSONObject();
        String linkUrl = entry.linkUrl.toString().equals("null") ? "" : entry.linkUrl.toString();
        String imageUrl = entry.imageUrl.toString().equals("null") ? "" : entry.imageUrl.toString();

        entryObject.put("newsfeed_id", entry.id);
        entryObject.put("device_id", entry.deviceID);
        entryObject.put("title", entry.title);
        entryObject.put("summary", entry.summary);
        entryObject.put("body", entry.body);
        entryObject.put("link_url", linkUrl);
        entryObject.put("link_url_string", entry.linkUrlString);
        entryObject.put("image_url", imageUrl);
        entryObject.put("image_url_string", entry.imageUrlString);
        entryObject.put("delivered_at", sDateFormat.format(entry.deliveredAt));
        entryObject.put("campaign_type", entry.campaignType.getValue());
        entryObject.put("shown", entry.shown);
        entryObject.put("read", entry.read);

        return entryObject;
    }

    private boolean updateNewsFeeds(final CordovaArgs args, final CallbackContext callbackContext) {
        final JSONArray newsFeedEntries = args.optJSONArray(0);
        if (newsFeedEntries == null) {
            android.util.Log.e("Repro", "Should pass Array.");
            return true;
        }

        final List<NewsFeedEntry> newsFeedEntryList = new ArrayList<>();

        try {
            for (int i = 0; i < newsFeedEntries.length(); i++) {
                NewsFeedEntry newsFeedEntry = new NewsFeedEntry(newsFeedEntries.optJSONObject(i));
                newsFeedEntryList.add(newsFeedEntry);
            }
        } catch (Exception e) {
            callbackContext.error("Something wrong with passed NewsFeed: " + e.getMessage());
            return true;
        }

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Repro.updateNewsFeeds(newsFeedEntryList);
                    callbackContext.success("success");
                } catch (Exception e) {
                    callbackContext.error("Failed to update NewsFeed");
                }
            }
        });

        return true;
    }

    private boolean setOpenUrlCallback(final CordovaArgs args, final CallbackContext callbackContext) {
        Repro.setOpenUrlCallback(new Repro.OpenUrlCallback() {
            @Override
            public void onOpened(Uri uri) {
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, uri.toString());
                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);
            }
        });
        return true;
    }


    private boolean remoteConfig_fetch(final CordovaArgs args, final CallbackContext callbackContext) {
        int timeout = args.optInt(0); // fallback: 0
        Repro.getRemoteConfig().fetch(timeout, new RemoteConfigListener() {
            @Override
            public void onCompletion(FetchStatus fetchStatus) {
                if (fetchStatus == FetchStatus.TIMEOUT_REACHED) {
                    callbackContext.error(fetchStatus.ordinal());
                } else {
                    callbackContext.success(fetchStatus.ordinal());
                }
            }
        });
        return true;
    }

    private boolean remoteConfig_activateFetched(final CordovaArgs args, final CallbackContext callbackContext) {
        if (Repro.getRemoteConfig().activateFetched()) {
            callbackContext.success();
        } else {
            callbackContext.error("Failed to do 'activateFetched'.");
        }
        return true;
    }

    private boolean remoteConfig_setDefaultsFromJson(final CordovaArgs args, final CallbackContext callbackContext) {
        JSONObject defaults = args.optJSONObject(0);
        if (Repro.getRemoteConfig().setDefaultsFromJsonString(defaults.toString())) {
            callbackContext.success();
        } else {
            callbackContext.error("Failed to do 'setDefaultsFromJson'.");
        }
        return true;
    }

    private boolean remoteConfig_setDefaultsFromJsonString(final CordovaArgs args, final CallbackContext callbackContext) {
        String jsonString = args.optString(0);
        if (Repro.getRemoteConfig().setDefaultsFromJsonString(jsonString)) {
            callbackContext.success();
        } else {
            callbackContext.error("Failed to do 'setDefaultsFromJsonString'.");
        }
        return true;
    }

    private boolean remoteConfig_getAllValues(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API<JSONObject>(callbackContext) {
            @Override
            JSONObject api() {
                Map<String, RemoteConfigValue> values = Repro.getRemoteConfig().getAllValues();
                return mapToJson(values);
            }
        });
        return true;
    }

    private boolean remoteConfig_getAllValuesWithPrefix(final CordovaArgs args, final CallbackContext callbackContext) {
        String prefix = args.optString(0);
        callAPI(new API<JSONObject>(callbackContext) {
            @Override
            JSONObject api() {
                Map<String, RemoteConfigValue> values = Repro.getRemoteConfig().getAllValuesWithPrefix(prefix);
                return mapToJson(values);
            }
        });
        return true;
    }

    private boolean remoteConfig_getValue(final CordovaArgs args, final CallbackContext callbackContext) {
        Object key = args.opt(0);
        if (!(key instanceof String)) {
            callbackContext.error("A non-String value was specified for key.");
            return true;
        }

        RemoteConfigValue value = Repro.getRemoteConfig().get((String)key);
        String stringValue = value.asString();
        callbackContext.success(stringValue);
        return true;
    }

    private boolean remoteConfig_getLocalDefaultValue(final CordovaArgs args, final CallbackContext callbackContext) {
        Object key = args.opt(0);
        if (!(key instanceof String)) {
            callbackContext.error("A non-String value was specified for key.");
            return true;
        }

        RemoteConfigValue value = Repro.getRemoteConfig().getLocalDefaultValue((String)key);
        String stringValue = value.asString();
        callbackContext.success(stringValue);
        return true;
    }

    private boolean remoteConfig_forceReset(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API<Void>(callbackContext) {
            @Override
            Void api() {
                Repro.getRemoteConfig().forceReset();
                return null;
            }
        });
        return true;
    }

    // helper

    private static abstract class API<T> implements Runnable {
        private final CallbackContext mCallbackContext;
        API(final CallbackContext callbackContext) {
            super();
            mCallbackContext = callbackContext;
        }

        @Override
        public void run() {
            final T ret = api();

            if (ret == null) {
              mCallbackContext.success();
            } else if (ret instanceof String) {
              mCallbackContext.success((String)ret);
            } else if (ret instanceof Integer) {
              mCallbackContext.success((Integer)ret);
            } else if (ret instanceof JSONObject) {
              mCallbackContext.success((JSONObject)ret);
            } else if (ret instanceof JSONArray) {
              mCallbackContext.success((JSONArray)ret);
            }
        }

        abstract T api();
    }

    private void callAPI(final API api) {
        cordova.getActivity().runOnUiThread(api);
    }

    private JSONObject mapToJson(final Map<String, RemoteConfigValue> map) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : map.keySet()) {
                RemoteConfigValue value = map.get(key);
                if (value != null) {
                    jsonObject.put(key, value.asString());
                }
            }
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
