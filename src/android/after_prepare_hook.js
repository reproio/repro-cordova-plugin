#!/usr/bin/env node

module.exports = function(context) {
  var APPLICATION_NAME = 'io.repro.android.CordovaApplication';
  var fs = require('fs');
  var path = require('path');
  var xml2js = require('xml2js');
  var parser = new xml2js.Parser();
  var MANIFEST_FILE_PATH = path.join(context.opts.projectRoot, 'platforms/android/AndroidManifest.xml');

  var data = fs.readFileSync(MANIFEST_FILE_PATH, 'utf8');
  parser.parseString(data, function (err, result) {
    var applicationTag = result['manifest']['application'][0]['$'];
    if (applicationTag['android:name'] === undefined) {
      applicationTag['android:name'] = APPLICATION_NAME;
    }
    var builder = new xml2js.Builder();
    var xml = builder.buildObject(result);
    fs.writeFileSync(MANIFEST_FILE_PATH, xml, 'utf8');
  });
};
