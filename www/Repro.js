var exec = require('cordova/exec');

function Repro() { };

Repro.prototype.setup = function (key, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setup", [key]);
};

Repro.prototype.optIn = function (endUserOptedIn, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "optIn", [endUserOptedIn]);
};

Repro.prototype.setLogLevel = function (logLevel, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setLogLevel", [logLevel]);
};

Repro.prototype.setUserID = function (userId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserID", [userId]);
};

Repro.prototype.setStringUserProfile = function (key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setStringUserProfile", [key, value]);
};

Repro.prototype.setIntUserProfile = function (key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setIntUserProfile", [key, value]);
};

Repro.prototype.setDoubleUserProfile = function (key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setDoubleUserProfile", [key, value]);
};

Repro.prototype.setDateUserProfile = function (key, value, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setDateUserProfile", [key, value.getTime()]);
};

Repro.prototype.setUserGender = function (gender, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserGender", [gender]);
};

Repro.prototype.setUserEmailAddress = function (email, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserEmailAddress", [email]);
};

Repro.prototype.setUserResidencePrefecture = function (prefecture, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserResidencePrefecture", [prefecture]);
};

Repro.prototype.setUserDateOfBirth = function (date, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserDateOfBirth", [date.getTime()]);
};

Repro.prototype.setUserAge = function (age, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setUserAge", [age]);
};

Repro.prototype.track = function (eventName, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "track", [eventName]);
};

Repro.prototype.trackWithProperties = function (eventName, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackWithProperties", [eventName, properties]);
};

Repro.prototype.trackViewContent = function (contentID, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackViewContent", [contentID, properties]);
};

Repro.prototype.trackSearch = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackSearch", [properties]);
};

Repro.prototype.trackAddToCart = function (contentID, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddToCart", [contentID, properties]);
};

Repro.prototype.trackAddToWishlist = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddToWishlist", [properties]);
};

Repro.prototype.trackInitiateCheckout = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackInitiateCheckout", [properties]);
};

Repro.prototype.trackAddPaymentInfo = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackAddPaymentInfo", [properties]);
};

Repro.prototype.trackPurchase = function (contentID, value, currency, properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackPurchase", [contentID, value, currency, properties]);
};

Repro.prototype.trackShare = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackShare", [properties]);
};

Repro.prototype.trackLead = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackLead", [properties]);
};

Repro.prototype.trackCompleteRegistration = function (properties, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackCompleteRegistration", [properties]);
};

Repro.prototype.setPushDeviceToken = function (deviceToken, successCallback, errorCallback) {
    // Deprecated
};

Repro.prototype.enablePushNotification = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotification");
};

Repro.prototype.enablePushNotificationForAndroid = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotification");
};

Repro.prototype.enablePushNotificationForIOS = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enablePushNotificationForIOS", []);
};

Repro.prototype.enableInAppMessagesOnForegroundTransition = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "enableInAppMessagesOnForegroundTransition", []);
};

Repro.prototype.disableInAppMessagesOnForegroundTransition = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "disableInAppMessagesOnForegroundTransition", []);
};

Repro.prototype.getUserID = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getUserID", []);
};

Repro.prototype.getDeviceID = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getDeviceID", []);
};

Repro.prototype.trackNotificationOpened = function (notificationId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "trackNotificationOpened", [notificationId]);
};

Repro.prototype.setSilverEggCookie = function (cookie, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setSilverEggCookie", [cookie]);
};

Repro.prototype.setSilverEggProdKey = function (prodKey, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setSilverEggProdKey", [prodKey]);
};

Repro.prototype.getNewsFeedsWithLimit = function (limit, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimit", [limit]);
};

Repro.prototype.getNewsFeedsWithLimitAndOffsetId = function (limit, offsetId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimitAndOffsetId", [limit, offsetId]);
};

Repro.prototype.getNewsFeedsWithLimitAndCampaignType = function (limit, campaignType, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimitAndCampaignType", [limit, campaignType]);
};

Repro.prototype.getNewsFeedsWithLimitAndOffsetIdAndCampaignType = function (limit, offsetId, campaignType, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "getNewsFeedsWithLimitAndOffsetIdAndCampaignType", [limit, offsetId, campaignType]);
};

Repro.prototype.updateNewsFeeds = function (newsFeeds, successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "updateNewsFeeds", [newsFeeds]);
};

Repro.prototype.setOpenUrlCallback = function(successCallback, errorCallback) {
    exec(successCallback, errorCallback, "Repro", "setOpenUrlCallback", []);
};

Repro.prototype.CampaignType = Object.freeze(
    {
        InAppMessage: "in_app_message",
        PushNotification: "push_notification",
        WebMessage: "web_message",
        All: "all"
    }
)

Repro.prototype.UserProfileGender = Object.freeze (
    {
        Other: 0,
        Male: 1,
        Female: 2,
    }
)

Repro.prototype.UserProfilePrefecture = Object.freeze (
    {
        Hokkaido: 1, Aomori: 2, Iwate: 3, Miyagi: 4, Akita: 5, Yamagata: 6, Fukushima: 7, Ibaraki: 8, Tochigi: 9, Gunma: 10,
        Saitama: 11, Chiba: 12, Tokyo: 13, Kanagawa: 14, Niigata: 15, Toyama: 16, Ishikawa: 17, Fukui: 18, Yamanashi: 19, Nagano: 20,
        Gifu: 21, Shizuoka: 22, Aichi: 23, Mie: 24, Shiga: 25, Kyoto: 26, Osaka: 27, Hyogo: 28, Nara: 29, Wakayama: 30,
        Tottori: 31, Shimane: 32, Okayama: 33, Hiroshima: 34, Yamaguchi: 35, Tokushima: 36, Kagawa: 37, Ehime: 38, Kochi: 39, Fukuoka: 40,
        Saga: 41, Nagasaki: 42, Kumamoto: 43, Oita: 44, Miyazaki: 45, Kagoshima: 46, Okinawa: 47
    }
)



module.exports = new Repro();
