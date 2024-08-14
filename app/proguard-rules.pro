# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#-keep class org.apache.cordova.** { *; }
#-keep public class * extends org.apache.cordova.CordovaPlugin
#
#-keep class com.ionicframework.cordova.webview.** { *; }
#-keep class com.ionicframework.cordova.webview.*
#-keep class org.apache.http.impl.auth.NegotiateScheme
#-dontwarn org.apache.commons.**
#-dontwarn org.apache.**


#Proguard
-dontwarn io.ionic.**
-keep class io.ionic.** { *; }
-keep interface io.ionic.** { *; }
-keepclassmembers  class io.ionic.** {*;}

-dontwarn com.github.kevinsawicki.**
-keep class com.github.kevinsawicki.** { *; }
-keep interface com.github.kevinsawicki.** { *; }
-keepclassmembers  class com.github.kevinsawicki.** {*;}

-dontwarn com.google.cordova.**
-keep class com.google.cordova.** { *; }
-keep interface com.google.cordova.** { *; }
-keepclassmembers  class com.google.cordova.** {*;}

-dontwarn com.ibm.**
-keep class com.ibm.** { *; }
-keep interface com.ibm.** { *; }
-keepclassmembers  class com.ibm.** {*;}

-dontwarn com.ionicframework.**
-keep class com.ionicframework.** { *; }
-keep interface com.ionicframework.** { *; }
-keepclassmembers  class com.ionicframework.** {*;}

-dontwarn com.pbakondy.**
-keep class com.pbakondy.** { *; }
-keep interface com.pbakondy.** { *; }
-keepclassmembers  class com.pbakondy.** {*;}

-dontwarn com.synconset.**
-keep class com.synconset.** { *; }
-keep interface com.synconset.** { *; }
-keepclassmembers  class com.synconset.** {*;}

-dontwarn com.wordsbaking.cordova.**
-keep class com.wordsbaking.cordova.** { *; }
-keep interface com.wordsbaking.cordova.** { *; }
-keepclassmembers  class com.wordsbaking.cordova.** {*;}

-dontwarn herbiebot.sunsmart.**
-keep class herbiebot.sunsmart.** { *; }
-keep interface herbiebot.sunsmart.** { *; }
-keepclassmembers  class herbiebot.sunsmart.** {*;}

-dontwarn com.wordsbaking.cordova.**
-keep class com.wordsbaking.cordova.** { *; }
-keep interface com.wordsbaking.cordova.** { *; }
-keepclassmembers  class com.wordsbaking.cordova.** {*;}

-dontwarn org.apache.cordova.**
-keep class org.apache.cordova.** { *; }
-keep interface org.apache.cordova.** { *; }
-keepclassmembers  class org.apache.cordova.** {*;}


-dontwarn com.worklight.**
-keep class com.worklight.** { *; }
-keep interface com.worklight.** { *; }
-keepclassmembers  class com.worklight.** {*;}

-dontwarn io.liteglue.**
-keep class io.liteglue.** { *; }
-keep interface io.liteglue.** { *; }
-keepclassmembers  class io.liteglue.** {*;}

#Warning: library class android.net.http.AndroidHttpClientConnection extends or implements program class org.apache.http.HttpInetConnection
-dontwarn org.apache.http.**
-dontwarn android.net.**
-keepclassmembers  class org.apache.http.** {*;}


#-ignorewarnings
#-keep class * {
#    public private *;
#}

# class:
#-keepclassmembers class android.webkit.WebView {
#   public *;
#}

#-injars      bin/classes
#-injars      libs
#-outjars     bin/classes-processed.jar

# Using Google's License Verification Library
-keep class com.android.vending.licensing.ILicensingService

# Specifies to write out some more information during processing.
# If the program terminates with an exception, this option will print out the entire stack trace, instead of just the exception message.
-verbose

# Annotations are represented by attributes that have no direct effect on the execution of the code.
-keepattributes *Annotation*

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepattributes InnerClasses
-keep class **.R
-keep class **.R$* {
    <fields>;
}

# These options let obfuscated applications or libraries produce stack traces that can still be deciphered later on
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Enable proguard with Cordova
-keep class org.apache.cordova.** { *; }
-keep public class * extends org.apache.cordova.CordovaPlugin

-keep class com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader { java.lang.ClassLoader sClassLoader; }
-keep class com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor { int MODULE_VERSION; }
-keep class com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor { java.lang.String MODULE_ID; }

-keep class org.apache.cordova.CordovaBridge { org.apache.cordova.PluginManager pluginManager; }
-keep class org.apache.cordova.CordovaInterfaceImpl { org.apache.cordova.PluginManager pluginManager; }
-keep class org.apache.cordova.CordovaResourceApi { org.apache.cordova.PluginManager pluginManager; }
-keep class org.apache.cordova.CordovaWebViewImpl { org.apache.cordova.PluginManager pluginManager; }
-keep class org.apache.cordova.ResumeCallback { org.apache.cordova.PluginManager pluginManager; }
-keep class org.apache.cordova.engine.SystemWebViewEngine { org.apache.cordova.PluginManager pluginManager; }

-keep class com.google.gson.internal.UnsafeAllocator { ** theUnsafe; }
-keep class me.leolin.shortcutbadger.ShortcutBadger { ** extraNotification; }
-keep class me.leolin.shortcutbadger.impl.XiaomiHomeBadger { ** messageCount; }
-keep class me.leolin.shortcutbadger.impl.XiaomiHomeBadger { ** extraNotification; }

-dontnote org.apache.harmony.xnet.provider.jsse.NativeCrypto
-dontnote sun.misc.Unsafe

-keep class com.worklight.androidgap.push.** { *; }
-keep class com.worklight.wlclient.push.** { *; }

# Enable proguard with Google libs
-keep class com.google.** { *; }
-dontwarn com.google.common.**
-dontwarn com.google.ads.**

# apache.http
-optimizations !class/merging/vertical*,!class/merging/horizontal*,!code/simplification/arithmetic,!field/*,!code/allocation/variable

-keep class net.sqlcipher.** { *; }
-dontwarn net.sqlcipher.**

-keep class org.codehaus.** { *; }
-keepattributes *Annotation*,EnclosingMethod

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Remove debug logs in release build
-assumenosideeffects class android.util.Log {
    public static *** d(...);
}

# These classes contain references to external jars which are not included in the default MobileFirst project.
-dontwarn com.worklight.common.internal.WLTrusteerInternal*
-dontwarn com.worklight.jsonstore.**
-dontwarn org.codehaus.jackson.map.ext.*
-dontwarn com.worklight.androidgap.push.GCMIntentService
-dontwarn com.worklight.androidgap.plugin.WLInitializationPlugin

-dontwarn android.support.v4.**
-dontwarn android.net.SSLCertificateSocketFactory
-dontwarn android.net.http.*

# These clases contain references to cordova webView
-keep class com.ionicframework.cordova.webview.** {*; }
-keep class com.ionicframework.cordova.webview.*
