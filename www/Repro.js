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

Repro.prototype.setUserProfile = function(key, value, successCallback, errorCallback) {
    if ((typeof key === "string") && (typeof value === "string")) {
        exec(successCallback, errorCallback, "Repro", "setUserProfile", [key, value]);
    } else if ((typeof key === "object") && (!value || (typeof value === "function"))) {
        exec(value, successCallback, "Repro", "setUserProfile", [JSON.stringify(key)]);
    } else {
        return errorCallback("Invalid params");
    }
};

Repro.prototype.track = function(eventName, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "track", [eventName]);
};

Repro.prototype.trackWithProperties = function(eventName, jsonDictionary, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackWithProperties", [eventName, jsonDictionary]);
};

Repro.prototype.setPushDeviceToken = function(deviceToken, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setPushDeviceToken", [deviceToken]);
};

Repro.prototype.enablePushNotification = function(senderId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotification", [senderId]);
};

Repro.prototype.showInAppMessage = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "showInAppMessage", []);
};

Repro.prototype.disableInAppMessageOnActive = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "disableInAppMessageOnActive", []);
};

module.exports = new Repro();
