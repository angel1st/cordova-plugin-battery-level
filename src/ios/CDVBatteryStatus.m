/*
 Copyright 2013-2014 Polaris Telematics Ltd (HK)
 
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

#import "CDVBatteryStatus.h"
#import <Cordova/CDVAvailability.h>

@implementation CDVBatteryStatus

#pragma mark -
#pragma mark Plugin interface methods

/**
 * Gets the battery level.
 *
 * @param callback
 *      The function to be exec as the callback
 */
- (void) getBatteryLevel:(CDVInvokedUrlCommand *)command
{
    [self.commandDelegate runInBackground:^{
        CDVPluginResult* result;
        
        [[UIDevice currentDevice] setBatteryMonitoringEnabled:YES];
        UIDevice *myDevice = [UIDevice currentDevice];
        [myDevice setBatteryMonitoringEnabled:YES];
        double batLevel = (float)[myDevice batteryLevel] * 100;
        
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[NSString stringWithFormat:@"%.0f",batLevel]];
        
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

/**
 * Check if device is plugged in.
 *
 * @param callback
 *      The function to be exec as the callback
 */
- (void) isPluggedIn:(CDVInvokedUrlCommand *)command
{
    CDVPluginResult* result;
    
    UIDevice* currentDevice = [UIDevice currentDevice];
    UIDeviceBatteryState currentState = [currentDevice batteryState];
    
    BOOL isPlugged = FALSE; // UIDeviceBatteryStateUnknown or UIDeviceBatteryStateUnplugged
    if ((currentState == UIDeviceBatteryStateCharging) || (currentState == UIDeviceBatteryStateFull)) {
        isPlugged = TRUE;
    }
    
    result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                                 messageAsBool:isPlugged];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

/**
 * Check if device is plugged in.
 *
 * @param callback
 *      The function to be exec as the callback
 */
- (void) getBatteryStatus:(CDVInvokedUrlCommand *)command
{
    UIDevice* currentDevice = [UIDevice currentDevice];
    UIDeviceBatteryState currentState = [currentDevice batteryState];

    isPlugged = FALSE; // UIDeviceBatteryStateUnknown or UIDeviceBatteryStateUnplugged
    if ((currentState == UIDeviceBatteryStateCharging) || (currentState == UIDeviceBatteryStateFull)) {
        isPlugged = TRUE;
    }
    float currentLevel = [currentDevice batteryLevel];

    if ((currentLevel != self.level) || (currentState != self.state)) {
        self.level = currentLevel;
        self.state = currentState;
    }

    // W3C spec says level must be null if it is unknown
    NSObject* w3cLevel = nil;
    if ((currentState == UIDeviceBatteryStateUnknown) || (currentLevel == -1.0)) {
        w3cLevel = [NSNull null];
    } else {
        w3cLevel = [NSNumber numberWithFloat:(currentLevel * 100)];
    }
    NSMutableDictionary* batteryData = [NSMutableDictionary dictionaryWithCapacity:2];
    [batteryData setObject:[NSNumber numberWithBool:isPlugged] forKey:@"isPlugged"];
    [batteryData setObject:w3cLevel forKey:@"level"];

    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:[batteryData]];
    [self.commandDelegate sendPluginResult:result callbackId:self.callbackId];
    
    [self.commandDelegate sendPluginResult:isPluggedRes callbackId:command.callbackId];
}

@end