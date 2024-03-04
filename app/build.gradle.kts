import deps.dependOn
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val versions = rootProject.file("version.properties")
val props = Properties()
props.load(FileInputStream(versions))
val major = props["majorVersion"].toString().toInt()
val minor = props["minorVersion"].toString().toInt()
val patch = props["patchVersion"].toString().toInt()

android {
    namespace = Build.applicationId
    compileSdk = Build.compileSdk

    defaultConfig {
        applicationId = Build.applicationId
        minSdk = Build.minSdk
        targetSdk = Build.targetSdk
        versionCode = 10000 * major + 1000 * minor + 10 * patch
        versionName = "$major.$minor.$patch"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget =  JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.Compose.Versions.composeCompiler
    }
}

secrets {
    propertiesFileName = "application.properties"
}

dependencies {
    implementation("io.github.tiendung717:chipmango:0.2.8")
    implementation("io.github.tiendung717:chipmango-uikit:0.2.8")
    dependOn(
        deps.AndroidX,
        deps.Compose,
        deps.Hilt,
        deps.Log,
        deps.Room,
        deps.Retrofit
    )
}

class ApplicationVariantAction : Action<com.android.build.gradle.api.ApplicationVariant> {
    override fun execute(variant: com.android.build.gradle.api.ApplicationVariant) {
        val fileName = createFileName(variant)
        variant.outputs.all(VariantOutputAction(fileName))
    }

    private fun createFileName(variant: com.android.build.gradle.api.ApplicationVariant): String {
        return "${variant.name}.apk"
    }

    class VariantOutputAction(
        private val fileName: String
    ) : Action<com.android.build.gradle.api.BaseVariantOutput> {
        override fun execute(output: com.android.build.gradle.api.BaseVariantOutput) {
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = fileName
            }
        }
    }
}
