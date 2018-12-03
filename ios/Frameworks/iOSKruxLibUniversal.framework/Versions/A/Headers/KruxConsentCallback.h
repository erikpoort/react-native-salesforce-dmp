//
//  KruxConsentCallback.h
//  iOSKruxLib
//
//  Copyright © 2018 Krux Digital. All rights reserved.
//

/**
  * Client needs to implement this protocol and provide implementation object as a parameter
  * to KruxTracker. This object will be used to pass response/error messages for consent get
  * and set requests
  */

@protocol KruxConsentCallback <NSObject>

- (void) handleConsentGetResponse:(NSString *) consentGetResponse;
- (void) handleConsentGetError:(NSString *) consentGetError;
- (void) handleConsentSetResponse:(NSString *) consentSetResponse;
- (void) handleConsentSetError:(NSString *) consentSetError;
- (void) handleConsumerRemoveResponse:(NSString *) consumerRemoveResponse;
- (void) handleConsumerRemoveError:(NSString *) consumerRemoveError;
- (void) handleConsumerPortabilityResponse:(NSString *) consumerPortabilityResponse;
- (void) handleConsumerPortabilityError:(NSString *) consumerPortabilityError;

@end

