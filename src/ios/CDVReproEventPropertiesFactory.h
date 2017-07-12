//
//  CDVReproEventPropertiesFactory.h
//  Repro
//
//  Created by nekoe on 2017/07/04.
//  Copyright © 2017 Repro Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Repro/Repro.h>

@interface CDVReproEventPropertiesFactory : NSObject
+ (RPRViewContentProperties*)convertToViewContentProperties:(id)props error:(NSError**)error;
+ (RPRSearchProperties*)convertToSearchProperties:(id)props error:(NSError**)error;
+ (RPRAddToCartProperties*)convertToAddToCartProperties:(id)props error:(NSError**)error;
+ (RPRAddToWishlistProperties*)convertToAddToWishlistProperties:(id)props error:(NSError**)error;
+ (RPRInitiateCheckoutProperties*)convertToInitiateCheckoutProperties:(id)props error:(NSError**)error;
+ (RPRAddPaymentInfoProperties*)convertToAddPaymentInfoProperties:(id)props error:(NSError**)error;
+ (RPRPurchaseProperties*)convertToPurchaseProperties:(id)props error:(NSError**)error;
+ (RPRShareProperties*)convertToShareProperties:(id)props error:(NSError**)error;
+ (RPRLeadProperties*)convertToLeadProperties:(id)props error:(NSError**)error;
+ (RPRCompleteRegistrationProperties*)convertToCompleteRegistrationProperties:(id)props error:(NSError**)error;
@end
