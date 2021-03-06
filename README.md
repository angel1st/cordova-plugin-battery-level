cordova-plugin-battery-status-ondemand
=============================

[Cordova][cordova] plugin to get battery status on demand.

## Overview
1. [Supported Platforms](#supported-platforms)
2. [Installation](#installation)
3. [ChangeLog](#changelog)
4. [Using the plugin](#using-the-plugin)

## Supported Platforms
- __iOS__ *(including iOS8)*
- __android__

## Installation
The plugin can be installed from git repository.

### Local development environment
From master:
```bash
# ~~ from master branch ~~
cordova plugin add https://github.com/angel1st/cordova-plugin-battery-status-ondemand.git
```

## Using the plugin
The plugin creates the object `cordova.plugins.battery` with the following method:

[battery.level][get]
[battery.isPluggedIn][is-plugged-in]
[battery.status][status]

### Plugin initialization
The plugin and its methods are not available before the *deviceready* event has been fired.

```javascript
document.addEventListener('deviceready', function () {
// cordova.plugins.battery.level is now available
}, false);
```

### Get the battery level
The battery level can be accessed through the `battery.level` interface.<br>
The method takes a callback function as its argument which will be called with the battery level. Optional the scope of the callback function ca be defined through a second argument.

```javascript
cordova.plugins.battery.level(function (battLevel) {
// console.log('battery level: ' + battLevel);
}, scope);
```

### Check if device is plugged in
The isPluggedIn flag can be accessed through the `battery.level.isPluggedIn` interface.<br>
The method takes a callback function as its argument which will be called with the isPluggedIn flag. Optional the scope of the callback function ca be defined through a second argument.

```javascript
cordova.plugins.battery.isPluggedIn(function (isPluggedIn) {
// console.log('device is ' + isPluggedIn ? 'plugged in' : 'plugged out');
}, scope);
```

### Check battery status
The third method returns and object `{level: batteryLevel, isPlugged: isDevicePlugged}`. Basically, this method provides two above methods information at once.
It is available via `cordova.plugins.battery.level.status` interface.<br>
The method takes a callback function as its argument which will return `status` object. Optional the scope of the callback function ca be defined through a second argument.

```javascript
cordova.plugins.battery.status(function (battStatus) {
// console.log('battery status: ' + JSON.stringify(battStatus));
}, scope);
```

## License

This software is released under the [Apache 2.0 License][apache2_license].

© 2018 Angel Todorov. All rights reserved


[cordova]: https://cordova.apache.org
[CLI]: http://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-line%20Interface
[PGB]: http://docs.build.phonegap.com/en_US/index.html
[PGB_plugin]: https://build.phonegap.com/plugins/1195
[changelog]: CHANGELOG.md
[get]: #get-the-battery-level
[apache2_license]: http://opensource.org/licenses/Apache-2.0

