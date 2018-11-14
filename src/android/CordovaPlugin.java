package io.repro.cordova;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.repro.android.Repro;
import io.repro.android.CordovaBridge;

/**
 * Created by nekoe on 1/15/16.
 * Copyright (c) 2016 Repro Inc. All rights reserved.
 */
public final class CordovaPlugin extends org.apache.cordova.CordovaPlugin {

    // Cordova I/F

    @Override
    public boolean execute(final String action, final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if ("setup".equals(action)) {
            return setup(args, callbackContext);
        }
        else if ("setLogLevel".equals(action)) {
            return setLogLevel(args, callbackContext);
        }
        else if ("startRecording".equals(action)) {
            return startRecording(args, callbackContext);
        }
        else if ("stopRecording".equals(action)) {
            return stopRecording(args, callbackContext);
        }
        else if ("pauseRecording".equals(action)) {
            return pauseRecording(args, callbackContext);
        }
        else if ("resumeRecording".equals(action)) {
            return resumeRecording(args, callbackContext);
        }
        else if ("maskWithRect".equals(action)) {
            return maskWithRect(args, callbackContext);
        }
        else if ("maskFullScreen".equals(action)) {
            return maskFullScreen(args, callbackContext);
        }
        else if ("unmask".equals(action)) {
            return unmask(args, callbackContext);
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
        else if ("showInAppMessage".equals(action)) {
            return showInAppMessage(args, callbackContext);
        }
        else if ("disableInAppMessageOnActive".equals(action)) {
            return disableInAppMessageOnActive(args, callbackContext);
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

        return false;
    }

    // API implementation

    private boolean setup(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String token = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                CordovaBridge.startSession(token);
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

    private boolean startRecording(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.startRecording();
                return null;
            }
        });
        return true;
    }

    private boolean stopRecording(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.stopRecording();
                return null;
            }
        });
        return true;
    }

    private boolean pauseRecording(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.pauseRecording();
                return null;
            }
        });
        return true;
    }

    private boolean resumeRecording(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.resumeRecording();
                return null;
            }
        });
        return true;
    }

    private boolean maskWithRect(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);
        final int x = args.getInt(1);
        final int y = args.getInt(2);
        final int w = args.getInt(3);
        final int h = args.getInt(4);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.mask(key, new Rect(x, y, w+x, h+y));
                return null;
            }
        });

        return true;
    }

    private boolean maskFullScreen(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);

        final WindowManager wm = (WindowManager)cordova.getActivity().getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getRealSize(size);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.mask(key, new Rect(0, 0, size.x, size.y));
                return null;
            }
        });

        return true;
    }

    private boolean unmask(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        final String key = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.unmask(key);
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

    private boolean showInAppMessage(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.showInAppMessage(cordova.getActivity());
                return null;
            }
        });
        return true;
    }

    private boolean disableInAppMessageOnActive(final CordovaArgs args, final CallbackContext callbackContext) {
        callAPI(new API(callbackContext) {
            Void api() {
                Repro.disableInAppMessageOnActive();
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
