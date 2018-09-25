#!/usr/bin/env node

module.exports = function(context) {
  var APPLICATION_NAME = 'io.repro.android.CordovaApplication';
  var fs = require('fs');
  var path = require('path');
  var xml2js = require('xml2js');
  var parser = new xml2js.Parser();
  var MANIFEST_FILE_PATH_OLD = path.join(context.opts.projectRoot, 'platforms/android/AndroidManifest.xml');
  var MANIFEST_FILE_PATH_NEW = path.join(context.opts.projectRoot, 'platforms/android/app/src/main/AndroidManifest.xml');
  var path;
  var data;
  try {
    data = fs.readFileSync(MANIFEST_FILE_PATH_OLD, 'utf8');
    path = MANIFEST_FILE_PATH_OLD;
  } catch (err) {
    data = fs.readFileSync(MANIFEST_FILE_PATH_NEW, 'utf8');
    path = MANIFEST_FILE_PATH_NEW;
  }

  parser.parseString(data, function (err, result) {
    var applicationTag = result['manifest']['application'][0]['$'];
    if (applicationTag['android:name'] === undefined) {
      applicationTag['android:name'] = APPLICATION_NAME;
    }
    var builder = new xml2js.Builder();
    var xml = builder.buildObject(result);
    fs.writeFileSync(path, xml, 'utf8');
  });
};
