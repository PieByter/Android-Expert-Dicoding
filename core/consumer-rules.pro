##---------------Begin: proguard configuration for SQLCipher  ----------
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }

##---------------Begin: proguard configuration for Gson  ----------
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

##---------------Begin: proguard configuration for Retrofit  ----------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-dontwarn kotlinx.**

##---------------Begin: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

##---------------Begin: proguard configuration for RxJava  ----------
-keep class kotlin.coroutines.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class kotlinx.coroutines.internal.** { *; }
-dontwarn kotlinx.coroutines.internal.**
-dontwarn kotlin.coroutines.**
-keep class kotlin.coroutines.intrinsics.** { *; }
-keep class kotlin.coroutines.jvm.internal.** { *; }

##---------------Begin: proguard configuration for Dynamic Features  ----------
-keep class com.google.android.play.core.splitinstall.** { *; }
-keep class com.google.android.play.core.splitinstall.SplitInstallManager { *; }
-keep class com.google.android.play.core.splitinstall.SplitInstallRequest { *; }
-keep class com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener { *; }

##---------------Keep all data classes  ----------
-keep class **.data.** { *; }

# Enable ProGuard debugging
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage usage.txt
-printmapping mapping.txt

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keep class com.example.core.data.source.remote.response.DetailUserResponse
-keep class com.example.core.data.source.remote.response.ItemsItem
-keep class com.example.core.data.source.remote.response.GithubResponse
-keep class com.example.core.domain.model.DetailUser
-keep class com.example.core.domain.model.GithubUser
-keep class com.example.core.domain.model.User
