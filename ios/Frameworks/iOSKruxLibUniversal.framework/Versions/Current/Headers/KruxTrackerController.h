//
//  TrackerController.h
//  KruxADM
//
//  Copyright © 2016 Krux Digital. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol KruxTrackerController <NSObject>

// Track a page view.
- (void)trackPageView:(NSString *)section
       pageAttributes:(NSDictionary *)pageAttributes
       userAttributes:(NSDictionary *)userAttributes;

// Track a page view.
// All the attributes are treated as page attributes
- (void)trackPageView:(NSString *)section
           attributes:(NSDictionary *)attributes;

// Track an event. This event is fired using fireEvent
// Returns YES on success or NO on error with error set to the specific error.
- (BOOL)fireEvent:(NSString *)eventUid
  eventAttributes:(NSDictionary *)eventAttributes
        withError:(NSError **)error;

// Track Transactions
- (void)trackTransactionWithAttributes:(NSDictionary *)transactionAttributes;

- (void)consentSetRequest:(NSDictionary *)consentSetAttributes;

- (void)consentGetRequest:(NSDictionary *)consentGetAttributes;

- (void)consumerRemoveRequest:(NSDictionary *)consumerRemoveAttributes;

- (void)consumerPortabilityRequest:(NSDictionary *)consumerPortabilityAttributes;

@end
