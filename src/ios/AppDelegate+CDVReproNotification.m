#import "AppDelegate+CDVReproNotification.h"
#import "CDVReproEnableSwizzling.h"
#import "Repro/Repro.h"
#import <objc/runtime.h>

@implementation AppDelegate (CDVReproNotification)

#if CDVREPRO_ENABLE_SWIZZLING

static void swizzle(Class klass, SEL originalSelector, SEL swizzledSelector, SEL dummySelector)
{
    Method originalMethod = class_getInstanceMethod(klass, originalSelector);
    Method swizzledMethod = class_getInstanceMethod(klass, swizzledSelector);

    if (originalMethod == NULL) {
        // This method is not implemented in the class itself or it's super classes.
        // Add a dummy implementation before swizzling.
        Method dummyMethod = class_getInstanceMethod(klass, dummySelector);
        class_addMethod(klass,
                        originalSelector,
                        method_getImplementation(dummyMethod),
                        method_getTypeEncoding(dummyMethod));
        originalMethod = class_getInstanceMethod(klass, originalSelector);
        method_exchangeImplementations(originalMethod, swizzledMethod);
    } else {
        BOOL didAddMethod = class_addMethod(klass,
                                            originalSelector,
                                            method_getImplementation(swizzledMethod),
                                            method_getTypeEncoding(swizzledMethod));

        if (didAddMethod) {
            // This method is implemented in it's super classes.
            class_replaceMethod(klass,
                                swizzledSelector,
                                method_getImplementation(originalMethod),
                                method_getTypeEncoding(originalMethod));
        } else {
            // This method is implemented in the class itself.
            method_exchangeImplementations(originalMethod, swizzledMethod);
        }
    }
}

+(void)load
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        swizzle([self class],
                @selector(application:didRegisterForRemoteNotificationsWithDeviceToken:),
                @selector(CDVReproNotification_real_application:didRegisterForRemoteNotificationsWithDeviceToken:),
                @selector(CDVReproNotification_dummy_application:didRegisterForRemoteNotificationsWithDeviceToken:));
        swizzle([self class],
                @selector(application:didFailToRegisterForRemoteNotificationsWithError:),
                @selector(CDVReproNotification_real_application:didFailToRegisterForRemoteNotificationsWithError:),
                @selector(CDVReproNotification_dummy_application:didFailToRegisterForRemoteNotificationsWithError:));
    });
}

- (void)CDVReproNotification_dummy_application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    // dummy
}

- (void)CDVReproNotification_dummy_application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    // dummy
}

- (void)CDVReproNotification_real_application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    [Repro setPushDeviceToken:deviceToken];
    [self CDVReproNotification_real_application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
}

- (void)CDVReproNotification_real_application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    NSLog(@"Remote Notification Error: %@", error);
    [self CDVReproNotification_real_application:application didFailToRegisterForRemoteNotificationsWithError:error];
}

#endif /* CDVREPRO_ENABLE_SWIZZLING */

@end
