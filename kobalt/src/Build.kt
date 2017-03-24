import com.beust.kobalt.*
import com.beust.kobalt.api.*
import com.beust.kobalt.plugin.apt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.android.*
import com.beust.kobalt.plugin.retrolambda.*
import com.beust.kobalt.plugin.java.*

val bs = buildScript {
    val pl = plugins("com.beust:kobalt-retrolambda:1.0.9","com.beust:kobalt-android:0.98")
}

// Note: this defeats incremental builds since the time changes every build
fun buildTime() = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(java.util.Date())

val p = project {

    name = "u2020"
    group = "com.jakewharton.u2020"
    artifactId = name
    version = "0.1"

    android {
        defaultConfig {
            minSdkVersion = 15
            versionCode = 100000
            versionName = "1.0.0"
            compileSdkVersion = "23"
            buildToolsVersion = "23.0.1"
            applicationId = "com.jakewharton.u2020"
        }
    }

    // Retrolambda is currently in Kobalt's core so it's only kicking in if it has
    // a configuration directive. Once it gets moved to its own repo, it will automatically
    // trigger if included so this won't be necessary any more (unless you want to customize it).
    retrolambda {}

    productFlavor("internal") {}
    productFlavor("production") {}
    buildType("debug") {
        buildConfig {
            field("String", "GIT_SHA", "\"gitSha\"")
            field("String", "BUILD_TIME", "\"${buildTime()}\"")
        }
    }
    buildType("release") {}

    apt{}

    dependencies {
        apt("com.squareup.dagger:dagger-compiler:1.2.2",
                "com.squareup.dagger:dagger:1.2.2",
                "com.jakewharton:butterknife:7.0.1"
        )

        compile(
                "com.android.support:appcompat-v7:23.0.1",
                "com.android.support:support-v4:23.0.1",
                "com.android.support:support-annotations:23.0.1",
                "com.android.support:recyclerview-v7:23.0.1",
                "com.android.support:design:23.0.1",

                "com.squareup.dagger:dagger:1.2.2",
                "com.squareup:javawriter:",
                "javax.inject:javax.inject:1",
                "com.squareup.okhttp:okhttp:2.6.0",
                "com.squareup.okhttp:logging-interceptor:2.6.0",
                "com.squareup.picasso:picasso:2.5.2",

                "com.squareup.retrofit:retrofit:2.0.0-beta2",
                "com.squareup.retrofit:converter-moshi:2.0.0-beta2",
                "com.squareup.retrofit:adapter-rxjava:2.0.0-beta2",

                "com.jakewharton:butterknife:7.0.1",
                "com.jakewharton.byteunits:byteunits:0.9.1",
                "com.jakewharton.rxbinding:rxbinding:aar:0.2.0",
                "io.reactivex:rxjava:1.0.14",
                "org.threeten:threetenbp:1.3.1",

                // internalDebugCompile
                "com.squareup.retrofit:retrofit-mock:2.0.0-beta2",
                "com.squareup.retrofit:adapter-rxjava-mock:2.0.0-beta2",
                "com.jakewharton.madge:madge:1.1.2",
                "com.jakewharton.scalpel:scalpel:1.1.2",
                "com.jakewharton:process-phoenix:aar:1.0.2",


                "io.reactivex:rxandroid:aar:1.0.1",
                "com.f2prateek.rx.preferences:rx-preferences:aar:1.0.0",
                "com.jakewharton.threetenabp:threetenabp:aar:1.0.2",
                "com.jakewharton.timber:timber:aar:4.0.1",
                "com.mattprecious.telescope:telescope:aar:1.5.0",
                "com.squareup.leakcanary:leakcanary-android:aar:1.3.1"
        )
    }

    javaCompiler {
        args("-source", "1.8")
    }

    dependenciesTest {
        //        compile("org.testng:testng:6.9.5")

    }
}
