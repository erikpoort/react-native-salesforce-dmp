//
//  KruxTracker.h
//  iOSKruxLib
//
//  Version 4.3.2
//  Copyright © 2017 Krux Digital. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "KruxTrackerController.h"
#import "KruxConsentCallback.h"

// Krux Tracker. Tracked pageview and events are stored
// in a persistent store and dispatched in the background to the server
@interface KruxTracker : NSObject<KruxTrackerController>

// if the debug flag is set, debug messages will be written to the logs.
// It is useful to debug calls to Krux Tracker.
// By default, debug flag is false.
@property(readwrite) BOOL debug;

// If the dryRun flag is set, hits will not be sent to Krux Tracker.
// It is useful for testing and debugging calls to the Krux SDK.
// By default, the dryRun flag is disabled.
@property(readwrite) BOOL dryRun;

// This is the unique configId that is given to an app developer.
@property(readonly, strong) NSString *configId;

@property(readwrite, strong) NSString *configHost;
@property(readwrite, strong) NSString *jslogHost;
@property(readwrite, strong) NSObject<KruxConsentCallback>* consentCallback;

// This the message to be sent from the apps, with the Config Id, to form a singleton instance.
+ (instancetype)sharedEventTrackerWithConfigId:(NSString *)configId
                                     debugFlag:(BOOL)debug
                                    dryRunFlag:(BOOL)dryRun;

+ (instancetype)sharedEventTrackerWithConfigId:(NSString *)confId
                                     debugFlag:(BOOL)debugFlag
                                    dryRunFlag:(BOOL)dryRunFlag
                                    configHost:(NSString *)configHost
                                     jslogHost:(NSString *)jslogHost;

+ (instancetype)sharedEventTrackerWithConfigId:(NSString *)configId
                                     debugFlag:(BOOL)debug
                                    dryRunFlag:(BOOL)dryRun
                               consentCallback:(NSObject<KruxConsentCallback>*)consentCallback;

+ (instancetype)sharedEventTrackerWithConfigId:(NSString *)confId
                                     debugFlag:(BOOL)debugFlag
                                    dryRunFlag:(BOOL)dryRunFlag
                                    configHost:(NSString *)configHost
                                     jslogHost:(NSString *)jslogHost
                               consentCallback:(NSObject<KruxConsentCallback>*)consentCallback;

+ (NSString *)getKruxSDKVersionNo;

- (NSArray *)getSegments;

- (void)startScheduler;

- (void)stopScheduler;

@end
