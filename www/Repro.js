var exec = require('cordova/exec');

function Repro(){};

Repro.prototype.setUp = function(key, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUp", [key]);
};

Repro.prototype.startRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "startRecording");
};

Repro.prototype.stopRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "stopRecording");
};

Repro.prototype.pauseRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "pauseRecording");
};

Repro.prototype.resumeRecording = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "resumeRecording");
};

Repro.prototype.maskWithRect = function(x, y, width, height, password, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "maskWithRect", [x, y, width, height, password]);
};

Repro.prototype.unmaskWithRect = function(password, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "unmaskWithRect", [password]);
};

Repro.prototype.setUserID = function(userId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserID", [userId]);
};

Repro.prototype.track = function(eventName, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "track", [eventName]);
};

Repro.prototype.trackWithProperties = function(eventName, jsonDictionary, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackWithProperties", [eventName, jsonDictionary]);
};

Repro.prototype.survey = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "survey");
};

module.exports = new Repro();
