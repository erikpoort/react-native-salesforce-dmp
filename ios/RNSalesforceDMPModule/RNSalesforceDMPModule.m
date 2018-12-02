//
//  RNSalesforceDMPModule.m
//  RNSalesforceDMPModule
//
//  Created by Erik Poort on 30/11/2018.
//  Copyright (c) 2019 MediaMonks. All rights reserved.
//

#import "RNSalesforceDMPModule.h"
#import <iOSKruxLibUniversal/KruxTracker.h>

static NSString *const kRejectCode = @"SalesforceDMPModule";

@implementation RNSalesforceDMPModule {
    KruxTracker *_kruxTracker;
}

RCT_EXPORT_MODULE();

#pragma mark - Configuration

RCT_EXPORT_METHOD(
      setupTracker:(NSString *)configId
      debugFlag:(BOOL)debugFlag
      resolver:(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    if (_kruxTracker) {
        reject(kRejectCode, @"Salesforce DMP is already initialised", nil);
        return;
    }
    if (!configId || [configId isEqualToString:@""]) {
        reject(kRejectCode, @"Your configId is empty", nil);
        return;
    }
    
    _kruxTracker = [KruxTracker sharedEventTrackerWithConfigId:configId debugFlag:debugFlag dryRunFlag:false consentCallback:nil];
    
    if (_kruxTracker) {
        resolve(@YES);
    } else {
        reject(kRejectCode, @"Couldn't initialise Salesforce DMP", nil);
    }
}

#pragma mark - Pages

RCT_EXPORT_METHOD(
      trackPageView:(NSString *)name
      pageAttributes:(NSDictionary *)pageAttributes
      userAttributes:(NSDictionary *)userAttributes
      resolver:(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    if (!_kruxTracker) {
        reject(kRejectCode, @"Salesforce DMP is not initialised", nil);
        return;
    }
    if (!name || [name isEqualToString:@""]) {
        reject(kRejectCode, @"Name can't be empty", nil);
        return;
    }

    if (!userAttributes || userAttributes.count == 0) {
        [_kruxTracker trackPageView:name attributes:pageAttributes];
    } else {
        [_kruxTracker trackPageView:name pageAttributes:pageAttributes userAttributes:userAttributes];
    }
    resolve(@YES);
}

#pragma mark - Events

RCT_EXPORT_METHOD(
      fireEvent:(NSString *)event
      attributes:(NSDictionary *)attributes
      resolver:(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    if (!_kruxTracker) {
        reject(kRejectCode, @"Salesforce DMP is not initialised", nil);
        return;
    }
    if (!event || [event isEqualToString:@""]) {
        reject(kRejectCode, @"Event can't be empty", nil);
        return;
    }
    
    if (!attributes) {
        attributes = @{};
    }
    
    NSError *error;
    if ([_kruxTracker fireEvent:event eventAttributes:attributes withError:&error]) {
        resolve(@YES);
    } else {
        reject(kRejectCode, @"Fire event failed with error", error);
    }
}

#pragma mark - Transactions

RCT_EXPORT_METHOD(
      trackTransaction:(NSDictionary *)attributes
      resolver:(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    if (!_kruxTracker) {
        reject(kRejectCode, @"Salesforce DMP is not initialised", nil);
        return;
    }
    if (!attributes || attributes.count == 0) {
        reject(kRejectCode, @"Attributes can't be empty", nil);
        return;
    }
    
    [_kruxTracker trackTransactionWithAttributes:attributes];
    resolve(@YES);
}

@end
