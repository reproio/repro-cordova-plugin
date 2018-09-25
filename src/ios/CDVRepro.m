//
//  CDVRepro.m
//
//  Created by jollyjoester_pro on 2014/12/10.
//  Copyright (c) 2014 Repro Inc. All rights reserved.
//

#import <UserNotifications/UserNotifications.h>

#import "Repro/Repro.h"

#import "CDVRepro.h"
#import "CDVReproEventPropertiesFactory.h"

@implementation CDVRepro

- (void)setup:(CDVInvokedUrlCommand*)command
{
    NSString *key = [command.arguments objectAtIndex:0];
    [Repro setup:key];
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

- (void)startRecording:(CDVInvokedUrlCommand*)command
{
    [Repro startRecording];
}

- (void)stopRecording:(CDVInvokedUrlCommand*)command
{
    [Repro stopRecording];
}

- (void)pauseRecording:(CDVInvokedUrlCommand*)command
{
    [Repro pauseRecording];
}

- (void)resumeRecording:(CDVInvokedUrlCommand*)command
{
    [Repro resumeRecording];
}

- (void)maskWithRect:(CDVInvokedUrlCommand*)command
{
    NSString *key = [command.arguments objectAtIndex:0];
    NSNumber *x = [command.arguments objectAtIndex:1];
    NSNumber *y = [command.arguments objectAtIndex:2];
    NSNumber *width = [command.arguments objectAtIndex:3];
    NSNumber *height = [command.arguments objectAtIndex:4];

    [Repro maskWithRect:CGRectMake(x.floatValue, y.floatValue, width.floatValue, height.floatValue) key:key];
}

- (void)maskFullScreen:(CDVInvokedUrlCommand*)command
{
    NSString *key = [command.arguments objectAtIndex:0];
    CGRect winSize = [UIScreen.mainScreen bounds];
    CGFloat width = winSize.size.width;
    CGFloat height = winSize.size.height;

    [Repro maskWithRect:CGRectMake(0, 0, width, height) key:key];
}

- (void)unmask:(CDVInvokedUrlCommand*)command
{
    NSString *key = [command.arguments objectAtIndex:0];
    [Repro unmaskForKey:key];
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

- (void)showInAppMessage:(CDVInvokedUrlCommand*)command
{
    [Repro showInAppMessage];
}

- (void)disableInAppMessageOnActive:(CDVInvokedUrlCommand*)command
{
    [Repro disableInAppMessageOnActive];
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

@end
