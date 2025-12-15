#!/usr/bin/env node

module.exports = function(context) {
  var fs = require('fs');
  var path = require('path');
  var plist = require('plist');
  var xml2js = require('xml2js');
  const platformPath = path.join(context.opts.projectRoot, 'platforms', 'ios');
  const cordovaIos = require('cordova-ios');
  const iosProject = new cordovaIos('ios', platformPath);

  // Fetch the option value
  var fetchOption = function(name) {
    var IOS_JSON = path.join(iosProject.root, 'ios.json');
    var json = JSON.parse(fs.readFileSync(IOS_JSON, 'utf8'));
    return json['installed_plugins']['cordova-plugin-repro'][name];
  }

  // Add push capability to entitlements plist
  var addPushEntitilements = function(filepath, value) {
    var data = fs.readFileSync(filepath, 'utf8');
    var json = plist.parse(data);
    json['aps-environment'] = value;
    fs.writeFileSync(filepath, plist.build(json), 'utf8');
  }

  // Set macro value to enable/disable swizzling
  var enableSwizzling = function(enable) {
    var PATH = path.join(iosProject.locations.xcodeCordovaProj, 'Plugins', 'cordova-plugin-repro', 'CDVReproEnableSwizzling.h');
    var value = Number(enable);
    fs.writeFileSync(PATH, '#define CDVREPRO_ENABLE_SWIZZLING ' + value);
  }

  var parser = new xml2js.Parser();
  var CONFIG_XML_PATH = path.join(context.opts.projectRoot, 'config.xml');
  var config = fs.readFileSync(CONFIG_XML_PATH, 'utf8');

  parser.parseString(config, function(err, result) {
    var enableIOSPush = ('true' == fetchOption('ENABLE_IOS_PUSH'));

    if (!enableIOSPush) {
      console.log("Repro: disable swizzling");
      enableSwizzling(false);

      console.log("Repro: did not enable push notificaiton capability");
    } else {
      console.log("Repro: enable swizzling");
      enableSwizzling(true);

      console.log("Repro: enable push notificaiton capability");
      var ENTITLEMENTS_DEBUG_PLIST_PATH = path.join(iosProject.locations.xcodeCordovaProj, 'Entitlements-Debug.plist');
      var ENTITLEMENTS_RELEASE_PLIST_PATH = path.join(iosProject.locations.xcodeCordovaProj, 'Entitlements-Release.plist');
      addPushEntitilements(ENTITLEMENTS_DEBUG_PLIST_PATH, "development");
      addPushEntitilements(ENTITLEMENTS_RELEASE_PLIST_PATH, "production");
    }
  })
};
