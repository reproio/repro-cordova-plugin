//
//  CDVRepro.m
//
//  Created by jollyjoester_pro on 2014/12/10.
//  Copyright (c) 2014 Repro Inc. All rights reserved.
//

#import "CDVRepro.h"
#import "Repro/Repro.h"

@implementation CDVRepro

- (void)setup:(CDVInvokedUrlCommand*)command
{
    NSString *key = [command.arguments objectAtIndex:0];
    [Repro setup:key];
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
    NSNumber *x = [command.arguments objectAtIndex:0];
    NSNumber *y = [command.arguments objectAtIndex:1];
    NSNumber *width = [command.arguments objectAtIndex:2];
    NSNumber *height = [command.arguments objectAtIndex:3];
    NSString *password = [command.arguments objectAtIndex:4];
    
    [Repro maskWithRect:CGRectMake(x.floatValue, y.floatValue, width.floatValue
                                   , height.floatValue) key:password];
}

- (void)unmaskWithRect:(CDVInvokedUrlCommand*)command
{
    NSString *password = [command.arguments objectAtIndex:0];
    [Repro unmaskForKey:password];
}

- (void)setUserID:(CDVInvokedUrlCommand*)command
{
    NSString *userId = [command.arguments objectAtIndex:0];
    [Repro setUserID:userId];
}

- (void)track:(CDVInvokedUrlCommand*)command
{
    NSString *eventName = [command.arguments objectAtIndex:0];
    [Repro track:eventName properties:nil];
}

- (void)trackWithProperties:(CDVInvokedUrlCommand*)command
{
    NSString *eventName = [command.arguments objectAtIndex:0];
    NSString *jsonDictinary = [command.arguments objectAtIndex:1];
    
    [Repro track:eventName properties:convertNSStringJSONToNSDictionary(jsonDictinary)];
}

- (void)survey:(CDVInvokedUrlCommand*)command
{
    NSError *error = nil;
    [Repro survey:&error];
}
    
- (void)enableUsabilityTesting:(CDVInvokedUrlCommand*)command
{
    [Repro enableUsabilityTesting];
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
