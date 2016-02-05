#!/usr/bin/env node

module.exports = function(context) {

  var MANIFEST_FILE_PATH = 'platforms/android/AndroidManifest.xml';
  var APPLICATION_NAME = 'io.repro.android.CordovaApplication';

  var fs = context.requireCordovaModule('fs');
  var path = context.requireCordovaModule('path');
  var xmlHelpers = context.requireCordovaModule('cordova-common').xmlHelpers;

  var manifestFileXml = xmlHelpers.parseElementtreeSync(MANIFEST_FILE_PATH);

  var app = manifestFileXml.findall('./application')[0];
  if (app.get('android:name') == undefined) {
    app.set('android:name', APPLICATION_NAME)
  }

  var out = manifestFileXml.write({'xml_declaration': false, 'indent': 4});
  fs.writeFileSync(path.join(context.opts.projectRoot, MANIFEST_FILE_PATH), out);
};
