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
#代码迭代优化的次数，取值范围0~7，默认是5
-optimizationpasses 5
#混淆时不使用大小写混合的方式，这样混淆后都是小写字母的组合
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#混淆时不做欲校验，欲校验是Proguard四大功能之一，在Android中一般不需要欲校验，这样可以加快混淆的速度
-dontpreverify
#混淆时记录日志，同时会生成映射文件，Android Studio中，生成的默认映射文件是 'build/outputs/mapping/release/mapping.txt'
-verbose
#生成指定的映射文件的路径和名称
-printmapping build/outputs/mapping/release/mymapping.txt
#混淆时所采用的算法，参数是Google官方推荐的过滤器算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#如果项目中用到了注解，需要保留注解属性
-keepattributes *Annotation*,InnerClasses
#不混淆泛型
-keepattributes Signature
#保留代码行号，这在混淆后代码运行中抛出异常信息时，有利于定位出问题的代码
-keepattributes SourceFile,LineNumberTable

#2.默认保留区
#保持Android SDK相关API类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
