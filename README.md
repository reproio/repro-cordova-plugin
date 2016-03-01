# io.repro.cordova

This plugin provides the ability to use [Repro](https://repro.io/)

## Installation

```
cordova plugin add cordova-plugin-repro
```

### Supported Platforms

- iOS
- Android

### Quick Example

```
onDeviceReady: function() {
    app.receivedEvent('deviceready');
    Repro.setup("YOUR_APP_TOKEN");
    Repro.startRecording();
},
```

For more detail, see http://docs.repro.io/
