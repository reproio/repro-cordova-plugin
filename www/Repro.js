var exec = require('cordova/exec');

function Repro(){};

Repro.prototype.setup = function(key, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setup", [key]);
};

Repro.prototype.optIn = function(endUserOptedIn, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "optIn", [endUserOptedIn]);
};

Repro.prototype.setLogLevel = function(logLevel, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setLogLevel", [logLevel]);
};

Repro.prototype.setUserID = function(userId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserID", [userId]);
};

Repro.prototype.setStringUserProfile = function(key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setStringUserProfile", [key, value]);
};

Repro.prototype.setIntUserProfile = function(key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setIntUserProfile", [key, value]);
};

Repro.prototype.setDoubleUserProfile = function(key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setDoubleUserProfile", [key, value]);
};

Repro.prototype.setDateUserProfile = function(key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setDateUserProfile", [key, value.getTime()]);
};

Repro.prototype.track = function(eventName, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "track", [eventName]);
};

Repro.prototype.trackWithProperties = function(eventName, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackWithProperties", [eventName, properties]);
};

Repro.prototype.trackViewContent = function(contentID, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackViewContent", [contentID, properties]);
};

Repro.prototype.trackSearch = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackSearch", [properties]);
};

Repro.prototype.trackAddToCart = function(contentID, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddToCart", [contentID, properties]);
};

Repro.prototype.trackAddToWishlist = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddToWishlist", [properties]);
};

Repro.prototype.trackInitiateCheckout = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackInitiateCheckout", [properties]);
};

Repro.prototype.trackAddPaymentInfo = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddPaymentInfo", [properties]);
};

Repro.prototype.trackPurchase = function(contentID, value, currency, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackPurchase", [contentID, value, currency, properties]);
};

Repro.prototype.trackShare = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackShare", [properties]);
};

Repro.prototype.trackLead = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackLead", [properties]);
};

Repro.prototype.trackCompleteRegistration = function(properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackCompleteRegistration", [properties]);
};

Repro.prototype.setPushDeviceToken = function(deviceToken, successCallback, errorCallback) {
    // Deprecated
};

Repro.prototype.enablePushNotification = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotification");
};

Repro.prototype.enablePushNotificationForAndroid = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotification");
};

Repro.prototype.enablePushNotificationForIOS= function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotificationForIOS", []);
};

Repro.prototype.enableInAppMessagesOnForegroundTransition = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enableInAppMessagesOnForegroundTransition", []);
};

Repro.prototype.disableInAppMessagesOnForegroundTransition = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "disableInAppMessagesOnForegroundTransition", []);
};

Repro.prototype.getUserID = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getUserID", []);
};

Repro.prototype.getDeviceID = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getDeviceID", []);
};

Repro.prototype.trackNotificationOpened = function(notificationId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackNotificationOpened", [notificationId]);
};

Repro.prototype.setSilverEggCookie = function(cookie, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setSilverEggCookie", [cookie]);
};

Repro.prototype.setSilverEggProdKey = function(prodKey, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setSilverEggProdKey", [prodKey]);
};

Repro.prototype.getNewsFeedsWithLimit = function(limit, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimit", [limit]);
};

Repro.prototype.getNewsFeedsWithLimitAndOffsetId = function(limit, offsetId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimitAndOffsetId", [limit, offsetId]);
};

Repro.prototype.updateNewsFeeds = function(newsFeeds, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "updateNewsFeeds", [newsFeeds]);
};

module.exports = new Repro();
