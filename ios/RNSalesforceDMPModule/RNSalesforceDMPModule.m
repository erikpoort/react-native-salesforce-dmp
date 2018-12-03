//
//  RNSalesforceDMPModule.m
//  RNSalesforceDMPModule
//
//  Created by Erik Poort on 30/11/2018.
//  Copyright (c) 2019 MediaMonks. All rights reserved.
//

#import "RNSalesforceDMPModule.h"

static NSString *const kRejectCode = @"SalesforceDMPModule";

@implementation RNSalesforceDMPModule {
    KruxTracker *_kruxTracker;
    
    RCTPromiseResolveBlock _consentSetResolve;
    RCTPromiseRejectBlock _consentSetReject;
    RCTPromiseResolveBlock _consentGetResolve;
    RCTPromiseRejectBlock _consentGetReject;
    RCTPromiseResolveBlock _consumerRemoveResolve;
    RCTPromiseRejectBlock _consumerRemoveReject;
    RCTPromiseResolveBlock _consumerPortabilityResolve;
    RCTPromiseRejectBlock _consumerPortabilityReject;
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
    
    _kruxTracker = [KruxTracker sharedEventTrackerWithConfigId:configId debugFlag:debugFlag dryRunFlag:false consentCallback:self];
    
    if (_kruxTracker) {
        resolve(@YES);
    } else {
        reject(kRejectCode, @"Couldn't initialise Salesforce DMP", nil);
    }
}

RCT_EXPORT_METHOD(
      getSegments:(RCTPromiseResolveBlock)resolve
      rejecter:(RCTPromiseRejectBlock)reject
) {
    if (!_kruxTracker) {
        reject(kRejectCode, @"Salesforce DMP is not initialised", nil);
        return;
    }
    
    NSArray *segments = [_kruxTracker getSegments];
    if (segments.count == 0) {
        reject(kRejectCode, @"No segments loaded yet", nil);
    } else {
        resolve([segments componentsJoinedByString:@","]);
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

#pragma mark - Consent

RCT_EXPORT_METHOD(
      consentSetRequest:(NSDictionary *)attributes
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
    if (_consentSetResolve || _consentSetReject) {
        reject(kRejectCode, @"Consent set request already active", nil);
        return;
    }
    
    _consentSetResolve = resolve;
    _consentSetReject = reject;
    [_kruxTracker consentSetRequest:attributes];
}

RCT_EXPORT_METHOD(
      consentGetRequest:(NSDictionary *)attributes
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
    if (_consentGetResolve || _consentGetReject) {
        reject(kRejectCode, @"Consent get request already active", nil);
        return;
    }
    
    _consentGetResolve = resolve;
    _consentGetReject = reject;
    [_kruxTracker consentGetRequest:attributes];
}

RCT_EXPORT_METHOD(
      consumerRemoveRequest:(NSDictionary *)attributes
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
    if (_consumerRemoveResolve || _consumerRemoveReject) {
        reject(kRejectCode, @"Consumer remove request already active", nil);
        return;
    }
    
    _consumerRemoveResolve = resolve;
    _consumerRemoveReject = reject;
    [_kruxTracker consumerRemoveRequest:attributes];
}

RCT_EXPORT_METHOD(
      consumerPortabilityRequest:(NSDictionary *)attributes
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
    if (_consumerPortabilityResolve || _consumerPortabilityReject) {
        reject(kRejectCode, @"Consumer portability request already active", nil);
        return;
    }
    
    _consumerPortabilityResolve = resolve;
    _consumerPortabilityReject = reject;
    [_kruxTracker consumerPortabilityRequest:attributes];
}

#pragma mark - KruxConsentCallback

- (void)handleConsentSetResponse:(NSString *)consentSetResponse {
    if (!_consentSetResolve) {
        NSLog(@"No promise to resolve");
    } else {
        _consentSetResolve(consentSetResponse);
    }
    
    _consentSetResolve = nil;
    _consentSetReject = nil;
}

- (void)handleConsentSetError:(NSString *)consentSetError {
    if (!_consentSetReject) {
        NSLog(@"No promise to reject");
    } else {
        _consentSetReject(kRejectCode, consentSetError, nil);
    }
    
    _consentSetResolve = nil;
    _consentSetReject = nil;
}

- (void)handleConsentGetResponse:(NSString *)consentGetResponse {
    if (!_consentGetResolve) {
        NSLog(@"No promise to resolve");
    } else {
        _consentGetResolve(consentGetResponse);
    }
    
    _consentGetResolve = nil;
    _consentGetReject = nil;
}

- (void)handleConsentGetError:(NSString *)consentGetError {
    if (!_consentGetReject) {
        NSLog(@"No promise to reject");
    } else {
        _consentGetReject(kRejectCode, consentGetError, nil);
    }
    
    _consentGetResolve = nil;
    _consentGetReject = nil;
}

- (void)handleConsumerRemoveResponse:(NSString *)consumerRemoveResponse {
    if (!_consumerRemoveResolve) {
        NSLog(@"No promise to resolve");
    } else {
        _consumerRemoveResolve(consumerRemoveResponse);
    }
    
    _consumerRemoveResolve = nil;
    _consumerRemoveReject = nil;
}

- (void)handleConsumerRemoveError:(NSString *)consumerRemoveError {
    if (!_consumerRemoveReject) {
        NSLog(@"No promise to reject");
    } else {
        _consumerRemoveReject(kRejectCode, consumerRemoveError, nil);
    }
    
    _consumerRemoveResolve = nil;
    _consumerRemoveReject = nil;
}

- (void)handleConsumerPortabilityResponse:(NSString *)consumerPortabilityResponse {
    if (!_consumerPortabilityResolve) {
        NSLog(@"No promise to resolve");
    } else {
        _consumerPortabilityResolve(consumerPortabilityResponse);
    }
    
    _consumerPortabilityResolve = nil;
    _consumerPortabilityReject = nil;
}

- (void)handleConsumerPortabilityError:(NSString *)consumerPortabilityError {
    if (!_consumerPortabilityReject) {
        NSLog(@"No promise to reject");
    } else {
        _consumerPortabilityReject(kRejectCode, consumerPortabilityError, nil);
    }
    
    _consumerPortabilityResolve = nil;
    _consumerPortabilityReject = nil;
}

@end
