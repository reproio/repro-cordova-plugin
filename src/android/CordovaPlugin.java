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

/**
 * Created by nekoe on 1/15/16.
 * Copyright (c) 2016 Repro Inc. All rights reserved.
 */
public final class CordovaPlugin extends org.apache.cordova.CordovaPlugin {

    private static final String REPRO_CORDOVA_BRIDGE_VERSION = "6.10.3";

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    static {
        sDateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
    }

    // Cordova I/F

    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if ("setup".equals(action)) {
            return setup(args, callbackContext);
        }
        else if ("optIn".equals(action)) {
            return optIn(args, callbackContext);
        }
        else if ("setLogLevel".equals(action)) {
            return setLogLevel(args, callbackContext);
        }
        else if ("setUserID".equals(action)) {
            return setUserID(args, callbackContext);
        }
        else if ("setStringUserProfile".equals(action)) {
            return setStringUserProfile(args, callbackContext);
        }
        else if ("setIntUserProfile".equals(action)) {
            return setIntUserProfile(args, callbackContext);
        }
        else if ("setDoubleUserProfile".equals(action)) {
            return setDoubleUserProfile(args, callbackContext);
        }
        else if ("setDateUserProfile".equals(action)) {
            return setDateUserProfile(args, callbackContext);
        }
        else if ("track".equals(action)) {
            return track(args, callbackContext);
        }
        else if ("trackWithProperties".equals(action)) {
            return trackWithProperties(args, callbackContext);
        }
        else if ("trackViewContent".equals(action)) {
            return trackViewContent(args, callbackContext);
        }
        else if ("trackSearch".equals(action)) {
            return trackSearch(args, callbackContext);
        }
        else if ("trackAddToCart".equals(action)) {
            return trackAddToCart(args, callbackContext);
        }
        else if ("trackAddToWishlist".equals(action)) {
            return trackAddToWishlist(args, callbackContext);
        }
        else if ("trackInitiateCheckout".equals(action)) {
            return trackInitiateCheckout(args, callbackContext);
        }
        else if ("trackAddPaymentInfo".equals(action)) {
            return trackAddPaymentInfo(args, callbackContext);
        }
        else if ("trackPurchase".equals(action)) {
            return trackPurchase(args, callbackContext);
        }
        else if ("trackShare".equals(action)) {
            return trackShare(args, callbackContext);
        }
        else if ("trackLead".equals(action)) {
            return trackLead(args, callbackContext);
        }
        else if ("trackCompleteRegistration".equals(action)) {
            return trackCompleteRegistration(args, callbackContext);
        }
        else if ("enableInAppMessagesOnForegroundTransition".equals(action)) {
            return enableInAppMessagesOnForegroundTransition(args, callbackContext);
        }
        else if ("disableInAppMessagesOnForegroundTransition".equals(action)) {
            return disableInAppMessagesOnForegroundTransition(args, callbackContext);
        }
        else if ("enablePushNotification".equals(action)) {
            return enablePushNotification(args, callbackContext);
        }
        else if ("enablePushNotificationForIOS".equals(action)) {
            // do nothing
            return true;
        }
        else if ("getUserID".equals(action)) {
            return getUserID(args, callbackContext);
        }
        else if ("getDeviceID".equals(action)) {
            return getDeviceID(args, callbackContext);
        }
        else if ("trackNotificationOpened".equals(action)) {
            return trackNotificationOpened(args, callbackContext);
        }
        else if ("setSilverEggCookie".equals(action)) {
            return setSilverEggCookie(args, callbackContext);
        }
        else if ("setSilverEggProdKey".equals(action)) {
            return setSilverEggProdKey(args, callbackContext);
        }
        else if ("getNewsFeedsWithLimit".equals(action)) {
            return getNewsFeedsWithLimit(args, callbackContext);
        }
        else if ("getNewsFeedsWithLimitAndOffsetId".equals(action)) {
            return getNewsFeedsWithLimitAndOffsetId(args, callbackContext);
        }
        else if ("getNewsFeedsWithLimitAndCampaignType".equals(action)) {
            return getNewsFeedsWithLimitAndCampaignType(args, callbackContext);
        }
        else if ("getNewsFeedsWithLimitAndOffsetIdAndCampaignType".equals(action)) {
            return getNewsFeedsWithLimitAndOffsetIdAndCampaignType(args, callbackContext);
        }
        else if ("updateNewsFeeds".equals(action)) {
            return updateNewsFeeds(args, callbackContext);
        }
        else if ("setOpenUrlCallback".equals(action)) {
            return setOpenUrlCallback(args, callbackContext);
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
        entryObject.put("image_url", imageUrl);
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

            if (ret instanceof Void) {
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
}
