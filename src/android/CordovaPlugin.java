package io.repro.android;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        else if ("setUserProfile".equals(action)) {
            return setUserProfile(args, callbackContext);
        }
        else if ("track".equals(action)) {
            return track(args, callbackContext);
        }
        else if ("trackWithProperties".equals(action)) {
            return trackWithProperties(args, callbackContext);
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
        else if ("setPushDeviceToken".equals(action)) {
            return setPushDeviceToken(args, callbackContext);
        }
        else if ("getUserID".equals(action)) {
            return getUserID(args, callbackContext);
        }
        else if ("getDeviceID".equals(action)) {
            return getDeviceID(args, callbackContext);
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

    private boolean setUserProfile(final CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if (!args.isNull(1)) {
            final String key = args.getString(0);
            final String value = args.getString(1);
            callAPI(new API(callbackContext) {
                Void api() {
                    Repro.setUserProfile(key, value);
                    return null;
                }
            });
        }
        else {
            final JSONObject profileJSON = new JSONObject(args.getString(0));
            final Map<String, String> profileMap = new HashMap<String, String>() {{
                final Iterator<String> it = profileJSON.keys();
                while (it.hasNext()) {
                    final String key = it.next();
                    final Object value = profileJSON.get(key);
                    put(key, value.toString());
                }
            }};
            callAPI(new API(callbackContext) {
                Void api() {
                    Repro.setUserProfile(profileMap);
                    return null;
                }
            });
        }

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

        final JSONObject propertiesJSON = new JSONObject(args.getString(1));
        final Map<String, Object> properties = new HashMap<String, Object>() {{
            final Iterator<String> it = propertiesJSON.keys();
            while (it.hasNext()) {
                final String key = it.next();
                final Object value = propertiesJSON.get(key);
                put(key, value);
            }
        }};

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.track(name, properties);
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
        final String senderId = args.getString(0);

        callAPI(new API(callbackContext) {
            Void api() {
                Repro.enablePushNotification(senderId);
                return null;
            }
        });

        return true;
    }

    private boolean setPushDeviceToken(final CordovaArgs args, final CallbackContext callbackContext) {
        // do nothing
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
