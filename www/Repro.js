var exec = require('cordova/exec');

function Repro(){};

Repro.prototype.setup = function(key, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setup", [key]);
};

Repro.prototype.setLogLevel = function(logLevel, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setLogLevel", [logLevel]);
};

Repro.prototype.startRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "startRecording", []);
};

Repro.prototype.stopRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "stopRecording", []);
};

Repro.prototype.pauseRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "pauseRecording", []);
};

Repro.prototype.resumeRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "resumeRecording", []);
};

Repro.prototype.maskWithRect = function(key, x, y, width, height, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "maskWithRect", [key, x, y, width, height]);
};

// this object holds key for full-screen mask and orientationchange event listener
Repro.prototype.orientationChangeListener = function(key) {
    this.key = key;
    var _key = key;
    this.listener = function() {
        exec(null, null, "Repro", "maskFullScreen", [_key]);
    };
};

// array of orientationChangeListener
Repro.prototype.orientationChangeListeners = [];

Repro.prototype.maskFullScreen = function(key, successCallback, errorCallback) {

    // add new orientationChangeListener if it's not exists.
    var found = this.orientationChangeListeners.some(function(l) {return key === l.key});
    if (!found) {
        var l = new this.orientationChangeListener(key);
        window.addEventListener('orientationchange', l.listener);
        this.orientationChangeListeners.push(l);
    }

    exec(successCallback, errorCallback, "Repro", "maskFullScreen", [key]);
};

Repro.prototype.unmask = function(key, successCallback, errorCallback) {

    // remove orientationChangeListener which key euqals 'key'
    this.orientationChangeListeners = this.orientationChangeListeners.filter(function(l) {
        if (key === l.key) {
            window.removeEventListener('orientationchange', l.listener);
            return false;
        }
        return true;
    });

    exec(successCallback, errorCallback, "Repro", "unmask", [key]);
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

Repro.prototype.showInAppMessage = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "showInAppMessage", []);
};

Repro.prototype.disableInAppMessageOnActive = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "disableInAppMessageOnActive", []);
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

module.exports = new Repro();
