package io.repro.cordova;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.repro.android.tracking.*;

/**
 * Created by nekoe on 2017/07/04.
 * Copyright (c) 2017 Repro Inc. All rights reserved.
 */

public final class StandardEventPropertiesFactory {

    public static ViewContentProperties convertToViewContentProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final ViewContentProperties propsObj = new ViewContentProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static SearchProperties convertToSearchProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final SearchProperties propsObj = new SearchProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_id"))       propsObj.setContentId(propsJSON.getString("content_id"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("search_string"))    propsObj.setSearchString(propsJSON.getString("search_string"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static AddToCartProperties convertToAddToCartProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final AddToCartProperties propsObj = new AddToCartProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static AddToWishlistProperties convertToAddToWishlistProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final AddToWishlistProperties propsObj = new AddToWishlistProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("content_id"))       propsObj.setContentId(propsJSON.getString("content_id"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static InitiateCheckoutProperties convertToInitiateCheckoutProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final InitiateCheckoutProperties propsObj = new InitiateCheckoutProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("content_id"))       propsObj.setContentId(propsJSON.getString("content_id"));
        if (propsJSON.has("num_items"))        propsObj.setNumItems(propsJSON.getInt("num_items"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static AddPaymentInfoProperties convertToAddPaymentInfoProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final AddPaymentInfoProperties propsObj = new AddPaymentInfoProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("content_id"))       propsObj.setContentId(propsJSON.getString("content_id"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static PurchaseProperties convertToPurchaseProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final PurchaseProperties propsObj = new PurchaseProperties();

        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("num_items"))        propsObj.setNumItems(propsJSON.getInt("num_items"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static ShareProperties convertToShareProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final ShareProperties propsObj = new ShareProperties();

        if (propsJSON.has("content_category"))  propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("content_id"))        propsObj.setContentId(propsJSON.getString("content_id"));
        if (propsJSON.has("content_name"))      propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("service_name"))      propsObj.setServiceName(propsJSON.getString("service_name"));
        if (propsJSON.has("extras"))            propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static LeadProperties convertToLeadProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final LeadProperties propsObj = new LeadProperties();

        if (propsJSON.has("value"))            propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))         propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name"))     propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("content_category")) propsObj.setContentCategory(propsJSON.getString("content_category"));
        if (propsJSON.has("extras"))           propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    public static CompleteRegistrationProperties convertToCompleteRegistrationProperties(final Object props) throws Exception {

        final JSONObjectWrapper propsJSON = new JSONObjectWrapper(props);
        final CompleteRegistrationProperties propsObj = new CompleteRegistrationProperties();

        if (propsJSON.has("value"))        propsObj.setValue(propsJSON.getDouble("value"));
        if (propsJSON.has("currency"))     propsObj.setCurrency(propsJSON.getString("currency"));
        if (propsJSON.has("content_name")) propsObj.setContentName(propsJSON.getString("content_name"));
        if (propsJSON.has("status"))       propsObj.setStatus(propsJSON.getString("status"));
        if (propsJSON.has("extras"))       propsObj.setExtras(propsJSON.getMap("extras"));

        return propsObj;
    }

    private static class JSONObjectWrapper {

        private final JSONObject mJSONObject;

        JSONObjectWrapper(final Object props) throws Exception {
            if (props == null || props == JSONObject.NULL) {
                mJSONObject = null;
            } else if (props instanceof JSONObject) {
                mJSONObject = (JSONObject)props;
            } else {
                throw new Exception("Properties can't be " + props.getClass().getName() + ".");
            }
        }

        boolean has(final String propName) {
            return mJSONObject != null && mJSONObject.has(propName);
        }

        String getString(final String propName) throws Exception {
            final Object propValue = mJSONObject.get(propName);
            if (propValue instanceof String) {
                return (String)propValue;
            } else if (propValue == JSONObject.NULL) {
                return null;
            } else {
                throw new Exception("Property \"" + propName + "\" can't be " + propValue.getClass().getName() + ".");
            }
        }

        double getDouble(final String propName) throws Exception {
            final Object propValue = mJSONObject.get(propName);
            if (propValue instanceof Double) {
                return (Double)propValue;
            } else if (propValue instanceof Integer) {
                return (Integer)propValue;
            } else if (propValue == JSONObject.NULL) {
                throw new Exception("Property \"" + propName + "\" can't be null.");
            } else {
                throw new Exception("Property \"" + propName + "\" can't be " + propValue.getClass().getName() + ".");
            }
        }

        int getInt(final String propName) throws Exception {
            final Object propValue = mJSONObject.get(propName);
            if (propValue instanceof Integer) {
                return (Integer)propValue;
            } else if (propValue == JSONObject.NULL) {
                throw new Exception("Property \"" + propName + "\" can't be null.");
            } else {
                throw new Exception("Property \"" + propName + "\" can't be " + propValue.getClass().getName() + ".");
            }
        }

        Map<String, Object> getMap(final String propName) throws Exception {
            final Object propValue = mJSONObject.get(propName);
            if (propValue instanceof JSONObject) {
                return jsonToMap((JSONObject)propValue);
            } else if (propValue == JSONObject.NULL) {
                return null;
            } else {
                throw new Exception("Property \"" + propName + "\" can't be " + propValue.getClass().getName() + ".");
            }
        }

        private Map<String, Object> jsonToMap(final JSONObject json) throws JSONException {

            if (json == null) {
                return null;
            }

            final Map<String, Object> map = new HashMap<String, Object>();

            final Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                final String key = keys.next();
                map.put(key, json.get(key));
            }

            return map;
        }
    }
}
