//
//  CDVRepro.m
//
//  Created by jollyjoester_pro on 2014/12/10.
//  Copyright (c) 2014 Repro Inc. All rights reserved.
//

#import <UserNotifications/UserNotifications.h>

#import "Repro/Repro.h"
#import "Repro/RPRNewsFeedEntry.h"

#import "CDVRepro.h"
#import "CDVReproEventPropertiesFactory.h"


#if __has_include(<Cordova/CDVAvailability.h>)
#import <Cordova/CDVAvailability.h>
#elif __has_include("CDVAvailability.h")
#import "CDVAvailability.h"
#elif __has_include("Cordova/CDVAvailability.h")
#import "Cordova/CDVAvailability.h"
#endif


#define isNSNumber(OBJECT)           ([OBJECT isKindOfClass:NSNumber.class])


@interface Repro (NonPublicApi)
+ (void)_passRuntimeValues:(nonnull NSDictionary<NSString *, NSString *> *)values;
@end


@implementation CDVRepro

- (void)setup:(CDVInvokedUrlCommand*)command
{
    if ([Repro respondsToSelector:@selector(_passRuntimeValues:)]) {
         [Repro _passRuntimeValues:@{
             @"sub_sdk_platform":            @"cordova",
#ifdef CDV_VERSION
             @"sub_sdk_platform_version":    CDV_VERSION,
#endif
             @"sub_sdk_bridge_version":      [NSString stringWithUTF8String:REPRO_CORDOVA_BRIDGE_VERSION],
         }];
    }

    NSString *key = [command.arguments objectAtIndex:0];
    [Repro setup:key];
}

- (void)optIn:(CDVInvokedUrlCommand*)command
{
    NSNumber *endUserOptedIn = [command.arguments objectAtIndex:0];
    [Repro optIn:[endUserOptedIn boolValue]];
}

- (void)setLogLevel:(CDVInvokedUrlCommand*)command
{
    NSString *logLevel = [command.arguments objectAtIndex:0];
    if ([logLevel isEqualToString:@"Debug"]) {
        [Repro setLogLevel:RPRLogLevelDebug];
    } else if ([logLevel isEqualToString:@"Info"]) {
        [Repro setLogLevel:RPRLogLevelInfo];
    } else if ([logLevel isEqualToString:@"Warn"]) {
        [Repro setLogLevel:RPRLogLevelWarn];
    } else if ([logLevel isEqualToString:@"Error"]) {
        [Repro setLogLevel:RPRLogLevelError];
    } else if ([logLevel isEqualToString:@"None"]) {
        [Repro setLogLevel:RPRLogLevelNone];
    }
}

- (void)setUserID:(CDVInvokedUrlCommand*)command
{
    NSString *userId = [command.arguments objectAtIndex:0];
    [Repro setUserID:userId];
}

- (void)setStringUserProfile:(CDVInvokedUrlCommand*)command
{
  NSString* key = [command.arguments objectAtIndex:0];
  NSString* value = [command.arguments objectAtIndex:1];
  [Repro setStringUserProfile:value forKey:key];
}

- (void)setIntUserProfile:(CDVInvokedUrlCommand*)command
{
  NSString* key = [command.arguments objectAtIndex:0];
  NSNumber* value = [command.arguments objectAtIndex:1];
  [Repro setIntUserProfile:value.intValue forKey:key];
}

- (void)setDoubleUserProfile:(CDVInvokedUrlCommand*)command
{
  NSString* key = [command.arguments objectAtIndex:0];
  NSNumber* value = [command.arguments objectAtIndex:1];
  [Repro setDoubleUserProfile:value.doubleValue forKey:key];
}

- (void)setDateUserProfile:(CDVInvokedUrlCommand*)command
{
  NSString* key = [command.arguments objectAtIndex:0];
  NSNumber* value = [command.arguments objectAtIndex:1];

  NSDate* date = [NSDate dateWithTimeIntervalSince1970:(value.longValue) / 1000.0];
  [Repro setDateUserProfile:date forKey:key];
}

- (void)track:(CDVInvokedUrlCommand*)command
{
    NSString *eventName = [command.arguments objectAtIndex:0];
    [Repro track:eventName properties:nil];
}

- (void)trackWithProperties:(CDVInvokedUrlCommand*)command
{
    NSString *eventName = [command.arguments objectAtIndex:0];
    id properties = [command.arguments objectAtIndex:1];

    if (properties == nil || properties == NSNull.null) {
        [Repro track:eventName properties:nil];
    } else if ([properties isKindOfClass:NSDictionary.class]) {
        [Repro track:eventName properties:properties];
    } else if ([properties isKindOfClass:NSString.class]) {
        NSLog(@"WARN: Repro trackWithProperties(String, String) will be deprecated. Use trackWithProperties(String, JSON) instead.");
        [Repro track:eventName properties:convertNSStringJSONToNSDictionary(properties)];
    } else {
        NSLog(@"ERROR: Repro Didn't track custom event \"%@\": Invalid second argument for trackWithProperties. %@ is not allowed.", eventName, [properties class]);
    }
}

- (void)trackViewContent:(CDVInvokedUrlCommand*)command
{
    id contentId = [command.arguments objectAtIndex:0];
    if (![contentId isKindOfClass:NSString.class]) {
        NSLog(@"ERROR: Repro Didn't track standard event \"view_content\": ContentID is required, and should be String. null or undefined is not allowed.");
        return;
    }

    NSError *error;
    id props = [command.arguments objectAtIndex:1];
    RPRViewContentProperties *propsObj = [CDVReproEventPropertiesFactory convertToViewContentProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"view_content\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackViewContent:contentId properties:propsObj];
}

- (void)trackSearch:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRSearchProperties *propsObj = [CDVReproEventPropertiesFactory convertToSearchProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"search\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackSearch:propsObj];
}

- (void)trackAddToCart:(CDVInvokedUrlCommand*)command
{
    id contentId = [command.arguments objectAtIndex:0];
    if (![contentId isKindOfClass:NSString.class]) {
        NSLog(@"ERROR: Repro Didn't track standard event \"add_to_cart\": ContentID is required, and should be String. null or undefined is not allowed.");
        return;
    }

    NSError *error;
    id props = [command.arguments objectAtIndex:1];
    RPRAddToCartProperties *propsObj = [CDVReproEventPropertiesFactory convertToAddToCartProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"add_to_cart\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackAddToCart:contentId properties:propsObj];
}

- (void)trackAddToWishlist:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRAddToWishlistProperties *propsObj = [CDVReproEventPropertiesFactory convertToAddToWishlistProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"add_to_wishlist\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackAddToWishlist:propsObj];
}

- (void)trackInitiateCheckout:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRInitiateCheckoutProperties *propsObj = [CDVReproEventPropertiesFactory convertToInitiateCheckoutProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"initiate_checkout\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackInitiateCheckout:propsObj];
}

- (void)trackAddPaymentInfo:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRAddPaymentInfoProperties *propsObj = [CDVReproEventPropertiesFactory convertToAddPaymentInfoProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"add_payment_info\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackAddPaymentInfo:propsObj];
}

- (void)trackPurchase:(CDVInvokedUrlCommand*)command
{
    id arg0 = [command.arguments objectAtIndex:0];
    if (![arg0 isKindOfClass:NSString.class]) {
        NSLog(@"ERROR: Repro Didn't track standard event \"purchase\": ContentID is required, and should be String. null or undefined is not allowed.");
        return;
    }
    NSString *contentId = (NSString*)arg0;

    id arg1 = [command.arguments objectAtIndex:1];
    if (![arg1 isKindOfClass:NSNumber.class]) {
        NSLog(@"ERROR: Repro Didn't track standard event \"purchase\": value is required, and should be number. null or undefined is not allowed.");
        return;
    }
    double value = [((NSNumber*)arg1) doubleValue];

    id arg2 = [command.arguments objectAtIndex:2];
    if (![arg2 isKindOfClass:NSString.class]) {
        NSLog(@"ERROR: Repro Didn't track standard event \"purchase\": currency is required, and should be String. null or undefined is not allowed.");
        return;
    }
    NSString* currency = (NSString*)arg2;

    NSError *error;
    id props = [command.arguments objectAtIndex:3];
    RPRPurchaseProperties *propsObj = [CDVReproEventPropertiesFactory convertToPurchaseProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"purchase\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackPurchase:contentId value:value currency:currency properties:propsObj];
}

- (void)trackShare:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRShareProperties *propsObj = [CDVReproEventPropertiesFactory convertToShareProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"share\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackShare:propsObj];
}

- (void)trackLead:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRLeadProperties *propsObj = [CDVReproEventPropertiesFactory convertToLeadProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"lead\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackLead:propsObj];
}

- (void)trackCompleteRegistration:(CDVInvokedUrlCommand*)command
{
    NSError *error;
    id props = [command.arguments objectAtIndex:0];
    RPRCompleteRegistrationProperties *propsObj = [CDVReproEventPropertiesFactory convertToCompleteRegistrationProperties:props error:&error];
    if (error) {
        NSLog(@"ERROR: Repro Didn't track standard event \"complete_registration\": %@", error.userInfo[@"cause"]);
        return;
    }

    [Repro trackCompleteRegistration:propsObj];
}

- (void)enablePushNotification:(CDVInvokedUrlCommand*)command
{
    // do nothing
}

- (void)enablePushNotificationForIOS:(CDVInvokedUrlCommand*)command
{
    if (floor(NSFoundationVersionNumber) > NSFoundationVersionNumber_iOS_9_x_Max) {
        UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
        [center requestAuthorizationWithOptions:(UNAuthorizationOptionAlert | UNAuthorizationOptionBadge | UNAuthorizationOptionSound) completionHandler:^(BOOL granted, NSError * _Nullable error) {
        }];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    } else {
        UIUserNotificationType types = UIUserNotificationTypeBadge | UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    }
}

- (void)enableInAppMessagesOnForegroundTransition:(CDVInvokedUrlCommand*)command
{
    [Repro enableInAppMessagesOnForegroundTransition];
}

- (void)disableInAppMessagesOnForegroundTransition:(CDVInvokedUrlCommand*)command
{
    [Repro disableInAppMessagesOnForegroundTransition];
}

- (void)getUserID:(CDVInvokedUrlCommand*)command
{
  NSString* value = [Repro getUserID];

  CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getDeviceID:(CDVInvokedUrlCommand*)command
{
  NSString* value = [Repro getDeviceID];

  CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)trackNotificationOpened:(CDVInvokedUrlCommand*)command
{
    // do nothing
}

static NSDictionary* convertNSStringJSONToNSDictionary(NSString* json) {
    if (json) {
        NSData* data = [json dataUsingEncoding:NSUTF8StringEncoding];
        return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
    } else {
        return nil;
    }
}

- (void)setSilverEggCookie:(CDVInvokedUrlCommand*)command
{
  id cookie = [command.arguments objectAtIndex:0];
  if (![cookie isKindOfClass:NSString.class]) {
    NSLog(@"ERROR: Repro Didn't set silver egg cookie: silver egg cookie is required, and should be String. null or undefined is not allowed.");
    return;
  }

  [Repro setSilverEggCookie:cookie];
}

- (void)setSilverEggProdKey:(CDVInvokedUrlCommand*)command
{
  id prodKey = [command.arguments objectAtIndex:0];
  if (![prodKey isKindOfClass:NSString.class]) {
    NSLog(@"ERROR: Repro Didn't set silver egg prod key: silver egg prod key is required, and should be String. null or undefined is not allowed.");
    return;
  }

  [Repro setSilverEggProdKey:prodKey];
}

- (void)getNewsFeedsWithLimit:(CDVInvokedUrlCommand*)command
{
  NSNumber* limit = [command.arguments objectAtIndex:0];
  if (![self isValidNewsFeedRequestParam:limit]) {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: limit should be Number and more than 0.");
    return;
  }

  [self.commandDelegate runInBackground:^{
    NSError *error = nil;
    NSArray<RPRNewsFeedEntry *> *entries = [Repro getNewsFeeds:[limit unsignedLongLongValue] error:&error];

    if (error)
    {
      NSString *errString = [error localizedDescription];
      NSString *errMessage = [NSString.alloc initWithFormat:@"Failed to get NewsFeeds: %@", errString];
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errMessage];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
      return;
    }

    NSMutableArray<NSDictionary *> *arr = [NSMutableArray.alloc initWithCapacity:entries.count];
    for (RPRNewsFeedEntry *entry in entries) {
      [arr addObject:[self newsFeedEntryToDictionary:entry]];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:arr];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }];
}

- (void)getNewsFeedsWithLimitAndOffsetId:(CDVInvokedUrlCommand*)command
{
  NSNumber* limit = [command.arguments objectAtIndex:0];
  if (![self isValidNewsFeedRequestParam:limit]) {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: limit should be Number and more than 0.");
    return;
  }

  NSNumber* offsetId = [command.arguments objectAtIndex:1];
  if (![self isValidNewsFeedRequestParam:offsetId])
  {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: offset id should be Number and more than 0.");
    return;
  }

  [self.commandDelegate runInBackground:^{
    NSError *error = nil;
    NSArray<RPRNewsFeedEntry *> *entries = [Repro getNewsFeeds:[limit unsignedLongLongValue] offsetID:[offsetId unsignedLongLongValue] error:&error];

    if (error)
    {
      NSString *errString = [error localizedDescription];
      NSString *errMessage = [NSString.alloc initWithFormat:@"Failed to get NewsFeeds: %@", errString];
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errMessage];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
      return;
    }

    NSMutableArray<NSDictionary *> *arr = [NSMutableArray.alloc initWithCapacity:entries.count];
    for (RPRNewsFeedEntry *entry in entries) {
      [arr addObject:[self newsFeedEntryToDictionary:entry]];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:arr];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }];
}

- (void)getNewsFeedsWithLimitAndCampaignType:(CDVInvokedUrlCommand*)command
{
  NSNumber* limit = [command.arguments objectAtIndex:0];
  if (![self isValidNewsFeedRequestParam:limit]) {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: limit should be Number and more than 0.");
    return;
  }

  NSString* rawCampaignType = [command.arguments objectAtIndex:1];
  RPRCampaignType convertedCampaigntype = [self getNewsFeedCampaignType:rawCampaignType];

  [self.commandDelegate runInBackground:^{
    NSError *error = nil;
    NSArray<RPRNewsFeedEntry *> *entries = [Repro getNewsFeeds:[limit unsignedLongLongValue] campaignType:convertedCampaigntype error:&error];

    if (error)
    {
      NSString *errString = [error localizedDescription];
      NSString *errMessage = [NSString.alloc initWithFormat:@"Failed to get NewsFeeds: %@", errString];
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errMessage];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
      return;
    }

    NSMutableArray<NSDictionary *> *arr = [NSMutableArray.alloc initWithCapacity:entries.count];
    for (RPRNewsFeedEntry *entry in entries) {
      [arr addObject:[self newsFeedEntryToDictionary:entry]];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:arr];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }];
}

- (void)getNewsFeedsWithLimitAndOffsetIdAndCampaignType:(CDVInvokedUrlCommand*)command
{
  NSNumber* limit = [command.arguments objectAtIndex:0];
  if (![self isValidNewsFeedRequestParam:limit]) {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: limit should be Number and more than 0.");
    return;
  }

  NSNumber* offsetId = [command.arguments objectAtIndex:1];
  if (![self isValidNewsFeedRequestParam:offsetId])
  {
    NSLog(@"ERROR: Repro Didn't get NewsFeed: offset id should be Number and more than 0.");
    return;
  }

  NSString* rawCampaignType = [command.arguments objectAtIndex:2];
  RPRCampaignType convertedCampaigntype = [self getNewsFeedCampaignType:rawCampaignType];

  [self.commandDelegate runInBackground:^{
    NSError *error = nil;
    NSArray<RPRNewsFeedEntry *> *entries = [Repro getNewsFeeds:[limit unsignedLongLongValue] offsetID:[offsetId unsignedLongLongValue] campaignType:convertedCampaigntype error:&error];

    if (error)
    {
      NSString *errString = [error localizedDescription];
      NSString *errMessage = [NSString.alloc initWithFormat:@"Failed to get NewsFeeds: %@", errString];
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errMessage];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
      return;
    }

    NSMutableArray<NSDictionary *> *arr = [NSMutableArray.alloc initWithCapacity:entries.count];
    for (RPRNewsFeedEntry *entry in entries) {
      [arr addObject:[self newsFeedEntryToDictionary:entry]];
    }

    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:arr];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
  }];
}

- (RPRCampaignType)getNewsFeedCampaignType:(nullable NSString *)rawCampaignType
{
    if (![rawCampaignType isKindOfClass:NSString.class]) {
      return RPRCampaignTypeUnknown;
    }

    if ([rawCampaignType isEqualToString:@"push_notification"]) {
      return RPRCampaignTypePushNotification;
    }
    else if ([rawCampaignType isEqualToString:@"in_app_message"]) {
      return RPRCampaignTypeInAppMessage;
    }
    else if ([rawCampaignType isEqualToString:@"web_message"]) {
      return RPRCampaignTypeWebMessage;
    }
    else if ([rawCampaignType isEqualToString:@"all"]) {
      return RPRCampaignTypeAll;
    }
    else {
      return RPRCampaignTypeUnknown;
    }
}

- (NSDictionary*)newsFeedEntryToDictionary:(RPRNewsFeedEntry *)entry
{
  NSDictionary *entryJson = @{
    @"newsfeed_id": @(entry.ID),
    @"device_id": entry.deviceID,
    @"title": entry.title,
    @"summary": entry.summary,
    @"body": entry.body,
    @"shown": @(entry.shown),
    @"read": @(entry.read),
    @"delivered_at": [[self dateFormatter] stringFromDate:entry.deliveredAt],
    @"campaign_type": [self convertCampaignTypeToString:entry.campaignType],
    @"link_url": entry.linkUrl ? [entry.linkUrl absoluteString] : @"",
    @"image_url": entry.imageUrl ? [entry.imageUrl absoluteString] : @""
  };

  return entryJson;
}

- (NSDateFormatter*)dateFormatter
{
  static NSDateFormatter* formatter = nil;
  if (!formatter) {
    formatter = [NSDateFormatter.alloc init];
    formatter.dateFormat = @"yyyy-MM-dd'T'HH:mm:ssZZZ";
    formatter.timeZone = [NSTimeZone timeZoneWithAbbreviation:@"UTC"];
    formatter.locale = [[NSLocale alloc] initWithLocaleIdentifier:@"en_US_POSIX"];
    formatter.calendar = [NSCalendar.alloc initWithCalendarIdentifier:NSCalendarIdentifierGregorian];
  }

  return formatter;
}

- (BOOL)isValidNewsFeedRequestParam:(NSNumber *)param
{
  return isNSNumber(param) && [param integerValue] > 0;
}

- (void)updateNewsFeeds:(CDVInvokedUrlCommand*)command
{
  NSArray<NSDictionary *> *newsFeedEntries = [command.arguments objectAtIndex:0];
  if (![newsFeedEntries isKindOfClass:NSArray.class])
  {
    NSLog(@"ERROR: Repro Should pass Array.");
    return;
  }

  NSMutableArray<RPRNewsFeedEntry *> *newsFeedEntryArray = [NSMutableArray.alloc initWithCapacity:newsFeedEntries.count];

  for (NSDictionary *newsFeedEntry in newsFeedEntries) {
    [newsFeedEntryArray addObject:[RPRNewsFeedEntry.alloc initWithDictionary:newsFeedEntry]];
  }

  [self.commandDelegate runInBackground:^{
    NSError *error = nil;
    [Repro updateNewsFeeds:newsFeedEntryArray error:&error];

    if (error)
    {
      CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Failed to update NewsFeed"];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
    else {
      CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"success"];
      [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
  }];
}

- (NSString *)convertCampaignTypeToString:(RPRCampaignType)campaignType
{
    switch (campaignType) {
        case RPRCampaignTypePushNotification:
            return @"push_notification";
        case RPRCampaignTypeInAppMessage:
            return @"in_app_message";
        case RPRCampaignTypeWebMessage:
            return @"web_message";
        case RPRCampaignTypeAll:
            return @"all";

        default:
            return @"unknown";
    }
}

@end
