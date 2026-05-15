-keep class com.android.billingclient.api.** { *; }
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclasseswithmembernames class * {
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <init>(...);
}
-keep class androidx.media3.** { *; }
-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite { <fields>; }
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class com.pawsup.cats.** { *; }
-keep class com.pawsup.billing.** { *; }
