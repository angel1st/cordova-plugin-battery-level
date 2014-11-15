/*
    Copyright 2013-2014 Polaris Telematics Ltd. (HK)

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

var BatteryLevel = function () {};

BatteryLevel.prototype = {

    /**
     * Gets the battery level.
     *
     * @param {Function} callback
     *      The function to be exec as the callback
     * @param {Object?} scope
     *      The callback function's scope
     */
    get: function (callback, scope) {
        var fn = function (level) {
            callback.call(scope || this, level);
        };

        cordova.exec(fn, null, 'BatteryLevel', 'getBatteryLevel', []);
    }
};

var plugin  = new BatteryLevel();

module.exports = plugin;
