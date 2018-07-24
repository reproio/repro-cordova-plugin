//
//  CDVReproEventPropertiesFactory.m
//  Repro
//
//  Created by nekoe on 2017/07/04.
//  Copyright © 2017 Repro Inc. All rights reserved.
//

#import "CDVReproEventPropertiesFactory.h"

#pragma mark - helper function

static void setError(NSError **error, NSString* cause) {
    if (error != nil) {
        if (*error != nil) {
            //NOTE: if already has error object, do not assign new one.
        } else {
            *error = [NSError errorWithDomain:@"io.repro" code:0 userInfo:@{@"cause": cause}];
        }
    }
}

#pragma mark - CDVReproDictionaryWrapper

@interface CDVReproDictionaryWrapper : NSObject
@property (nonatomic, readonly) NSDictionary *dict;

- (instancetype)initWith:(id)props error:(NSError**)error;

- (BOOL)has:(NSString*)propName;

- (NSString*)getString:(NSString*)propName error:(NSError**)error;
- (double)getDouble:(NSString*)propName error:(NSError**)error;
- (NSInteger)getInt:(NSString*)propName error:(NSError**)error;
- (NSDictionary*)getDictionary:(NSString*)propName error:(NSError**)error;
@end

@implementation CDVReproDictionaryWrapper

- (instancetype)initWith:(id)props error:(NSError**)error
{
    self = [super init];
    if (self) {
        if (props == nil || props == NSNull.null) {
            _dict = nil;
        } else if ([props isKindOfClass:NSDictionary.class]) {
            _dict = props;
        } else {
            setError(error, [NSString stringWithFormat:@"Properties can't be %@", [props class]]);
            _dict = nil;
        }
    }
    return self;
}

- (BOOL)has:(NSString*)propName
{
    return self.dict && self.dict[propName];
}

- (NSString*)getString:(NSString*)propName error:(NSError**)error
{
    id propValue = self.dict[propName];
    if ([propValue isKindOfClass:NSString.class]) {
        return propValue;
    } else if (propValue == NSNull.null) {
        return nil;
    } else {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be %@.", propName, [propValue class]]);
        return nil;
    }
}

- (double)getDouble:(NSString*)propName error:(NSError**)error
{
    id propValue = self.dict[propName];
    if ([propValue isKindOfClass:NSNumber.class]) {
        return [propValue doubleValue];
    } else if (propValue == NSNull.null) {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be null.", propName]);
        return 0;
    } else {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be %@.", propName, [propValue class]]);
        return 0;
    }
}

- (NSInteger)getInt:(NSString*)propName error:(NSError**)error
{
    id propValue = self.dict[propName];
    if ([propValue isKindOfClass:NSNumber.class]) {
        return [propValue integerValue];
    } else if (propValue == NSNull.null) {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be null.", propName]);
        return 0;
    } else {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be %@.", propName, [propValue class]]);
        return 0;
    }
}

- (NSDictionary*)getDictionary:(NSString*)propName error:(NSError**)error
{
    id propValue = self.dict[propName];
    if ([propValue isKindOfClass:NSDictionary.class]) {
        return propValue;
    } else if (propValue == NSNull.null) {
        return nil;
    } else {
        setError(error, [NSString stringWithFormat: @"Property \"%@\" can't be %@.", propName, [propValue class]]);
        return nil;
    }
}

@end

#pragma mark - CDVReproEventPropertiesFactory

@implementation CDVReproEventPropertiesFactory

+ (RPRViewContentProperties*)convertToViewContentProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRViewContentProperties *propsObj = [[RPRViewContentProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRSearchProperties*)convertToSearchProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRSearchProperties *propsObj = [[RPRSearchProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"content_id"])       propsObj.contentID       = [propsDict getString:@"content_id" error:error];
    if ([propsDict has:@"search_string"])    propsObj.searchString    = [propsDict getString:@"search_string" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRAddToCartProperties*)convertToAddToCartProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRAddToCartProperties *propsObj = [[RPRAddToCartProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRAddToWishlistProperties*)convertToAddToWishlistProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRAddToWishlistProperties *propsObj = [[RPRAddToWishlistProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"content_id"])       propsObj.contentID       = [propsDict getString:@"content_id" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRInitiateCheckoutProperties*)convertToInitiateCheckoutProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRInitiateCheckoutProperties *propsObj = [[RPRInitiateCheckoutProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"content_id"])       propsObj.contentID       = [propsDict getString:@"content_id" error:error];
    if ([propsDict has:@"num_items"])        propsObj.numItems        = [propsDict getInt:@"num_items" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRAddPaymentInfoProperties*)convertToAddPaymentInfoProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRAddPaymentInfoProperties *propsObj = [[RPRAddPaymentInfoProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"content_id"])       propsObj.contentID       = [propsDict getString:@"content_id" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRPurchaseProperties*)convertToPurchaseProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRPurchaseProperties *propsObj = [[RPRPurchaseProperties alloc] init];

    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"num_items"])        propsObj.numItems        = [propsDict getInt:@"num_items" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRShareProperties*)convertToShareProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRShareProperties *propsObj = [[RPRShareProperties alloc] init];

    if ([propsDict has:@"content_category"])  propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"content_id"])        propsObj.contentID       = [propsDict getString:@"content_id" error:error];
    if ([propsDict has:@"content_name"])      propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"service_name"])      propsObj.serviceName     = [propsDict getString:@"service_name" error:error];
    if ([propsDict has:@"extras"])            propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRLeadProperties*)convertToLeadProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRLeadProperties *propsObj = [[RPRLeadProperties alloc] init];

    if ([propsDict has:@"value"])            propsObj.value           = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])         propsObj.currency        = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"])     propsObj.contentName     = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"content_category"]) propsObj.contentCategory = [propsDict getString:@"content_category" error:error];
    if ([propsDict has:@"extras"])           propsObj.extras          = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

+ (RPRCompleteRegistrationProperties*)convertToCompleteRegistrationProperties:(id)props error:(NSError**)error
{
    CDVReproDictionaryWrapper *propsDict = [[CDVReproDictionaryWrapper alloc] initWith:props error:error];
    RPRCompleteRegistrationProperties *propsObj = [[RPRCompleteRegistrationProperties alloc] init];

    if ([propsDict has:@"value"])        propsObj.value       = [propsDict getDouble:@"value" error:error];
    if ([propsDict has:@"currency"])     propsObj.currency    = [propsDict getString:@"currency" error:error];
    if ([propsDict has:@"content_name"]) propsObj.contentName = [propsDict getString:@"content_name" error:error];
    if ([propsDict has:@"status"])       propsObj.status      = [propsDict getString:@"status" error:error];
    if ([propsDict has:@"extras"])       propsObj.extras      = [propsDict getDictionary:@"extras" error:error];

    return propsObj;
}

@end
