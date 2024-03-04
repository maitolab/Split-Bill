@file:Suppress("MemberVisibilityCanBePrivate")


object Build {
    const val applicationId = "com.solid.app"
    const val versionMajor = 0
    const val versionMinor = 0
    const val versionPatch = 0
    const val versionBuild = 1

    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 33
    const val versionCode = versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"

    const val proguard_android = "proguard-android.txt"
    const val proguard_common = "proguard-common.txt"
    const val proguard_specific = "proguard-specific.txt"
}