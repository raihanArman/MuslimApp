/**
 * @author kasmadi
 * @date 27/05/2022
 */

object Dependencies {
    const val firebaseCrashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.9.2"
    const val realmGradle = "io.realm:realm-gradle-plugin:10.11.1"
    const val apolloGradle =
        "com.apollographql.apollo3:apollo-gradle-plugin:${Versions.apolloVersion}"
    const val firebasePrepGradle = "com.google.firebase:perf-plugin:1.4.1"
    const val versionChecker = "com.github.ben-manes:gradle-versions-plugin:${Versions.versionCheckerVersion}"

    //eventBus
    const val eventBus = "org.greenrobot:eventbus:3.3.1"

    //read more
    const val readMore = "com.borjabravo:readmoretextview:2.1.0"

    //looping view pager
    const val loopingViewPager = "com.kenilt.loopingviewpager:loopingviewpager:0.3.0"

    //infinite view pager
    const val infiniteViewPager = "com.asksira.android:loopingviewpager:1.4.1"

    //appier
//    const val appier = "com.appier:appier-android:7.9.1"

    const val autoFitTextView = "com.github.damaisubimawanto:android-autofittextview:1.0.0"
    const val dotsIndicator = "com.tbuonomo:dotsindicator:4.3"
    const val doubleTapPlayerView = "com.github.damaisubimawanto:DoubleTapPlayerView:0.8.1"
    const val supernovaEmoji = "com.github.damaisubimawanto:SuperNova-Emoji:2.0.3"
    const val circleProgress = "com.github.Adilhusen:circle-progress-ad-android-:1.0"
    const val simpleTooltip = "com.github.douglasjunior:android-simple-tooltip:1.1.0"
    const val viewAnimator = "com.github.florent37:viewanimator:1.1.0"
    const val bottomNavigationEx = "com.github.ittianyu:BottomNavigationViewEx:2.0.4"
    const val liveFrontBridge = "com.github.livefront:bridge:v1.2.0"
    const val otpView = "com.github.mukeshsolanki:android-otpview-pinview:2.0.3"
    const val alphabetIndexRecyclerView = "com.github.myinnos:AlphabetIndex-Fast-Scroll-RecyclerView:1.0.95"
    const val storiesProgressView = "com.gitlab.damaisubimawanto:storiesprogressviewplus:1.0.1"
    const val imageCropper = "com.gitlab.kasmadi17:Android-Image-Cropper:2.8.7"
    const val comScore = "com.comscore:android-analytics:6.9.2"
    const val androidState = "com.evernote:android-state:1.4.1"
    const val materialDialog = "com.afollestad.material-dialogs:core:3.3.0"
    const val lottie = "com.airbnb.android:lottie:5.2.0"
    const val appsFlyer = "com.appsflyer:af-android-sdk:6.9.2"
    const val ffMpeg = "com.arthenica:ffmpeg-kit-min-gpl:4.5.1-1.LTS"
    const val compressor = "id.zelory:compressor:3.0.1"
    const val blurry = "jp.wasabeef:blurry:4.0.1"
    const val barcodeScanner = "me.dm7.barcodescanner:zxing:1.9.13"
    const val movementMethod = "me.saket:better-link-movement-method:2.2.0"
    const val materialProgressbar = "me.zhanghai.android.materialprogressbar:library:1.6.1"
    const val keyboardVisibilityEvent = "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:3.0.0-RC3"
    const val internetAvailabilityChecker = "com.treebo:internetavailabilitychecker:1.0.4"
    const val dataBindingCompiler = "com.android.databinding:compiler:7.3.0"

    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val socketIo = "io.socket:socket.io-client:2.0.0"
    const val webRtc = "org.whispersystems:webrtc-android:M77"


    object Module {
        const val player = ":player"
        const val domain = ":domain"
        const val data = ":data"
        const val aarMncAnalytics = "MncDigitalAnalytics-1.1"
        const val aarHlsDownload = "HlsDwld-2.0.1"
        const val aarTiktok = "TikTok-0.1.4.1"
        const val bottomNavigationViewEx = "BottomNavigationViewEx"
    }

    object Realm{
        const val realm = "io.realm.kotlin:library-base:1.2.0"
    }

    object Android {
        const val lifecycleExtensions = "android.arch.lifecycle:extensions:1.1.1"
        const val lifecycleCommonJava8 = "android.arch.lifecycle:common-java8:1.1.1"
        const val installReferrer = "com.android.installreferrer:installreferrer:2.2"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompatVersion}"
        const val browser = "androidx.browser:browser:1.4.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        const val legacySupportCore = "androidx.legacy:legacy-support-core-ui:1.0.0"
        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"
        const val multiDex = "androidx.multidex:multidex:2.0.1"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val viewPager = "androidx.viewpager:viewpager:1.0.0"
    }

    object Apollo {
        const val runtime = "com.apollographql.apollo3:apollo-runtime:${Versions.apolloVersion}"
        const val normalizedCacheSqlite = "com.apollographql.apollo3:apollo-normalized-cache-sqlite:${Versions.apolloVersion}"
        const val apolloApi = "com.apollographql.apollo3:apollo-api:${Versions.apolloVersion}"
    }

    object Google {
        const val flexbox = "com.google.android.flexbox:flexbox:3.0.0"
        const val playServicesAds = "com.google.android.gms:play-services-ads:21.2.0"
        const val playServicesAuth = "com.google.android.gms:play-services-auth:20.2.0"
        const val playServicesAuthApiPhone = "com.google.android.gms:play-services-auth-api-phone:18.0.1"
        const val playServiceLocation = "com.google.android.gms:play-services-location:19.0.1"
        const val playServiceTagManager = "com.google.android.gms:play-services-tagmanager:18.0.1"
        const val material = "com.google.android.material:material:1.6.1"
        const val playCore = "com.google.android.play:core:1.10.3"
        const val mp4Parser = "com.googlecode.mp4parser:isoparser:1.1.22"
        const val gson = "com.google.code.gson:gson:2.9.0"
        const val safetyNet = "com.google.android.gms:play-services-safetynet:18.0.1"
        const val guava = "com.google.guava:guava:31.1-android"
    }

    object FireBase {
        const val bom = "com.google.firebase:firebase-bom:30.5.0"
        const val analytics = "com.google.firebase:firebase-analytics-ktx"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val config = "com.google.firebase:firebase-config-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val messaging = "com.google.firebase:firebase-messaging-ktx"
        const val iid = "com.google.firebase:firebase-iid:21.1.0"
        const val perf = "com.google.firebase:firebase-perf-ktx:20.1.0"
        const val auth = "com.google.firebase:firebase-auth-ktx"
    }

    object SquareUp {
        const val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:4.9.3"
        const val picasso = "com.squareup.picasso:picasso:2.71828"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val retrofit2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
        const val retrofit2Converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    }

    object Kotlin {
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
        const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
        const val coroutinesPlayService = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0}"
    }

    object Rx {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.21"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Compose {
        const val composeRunTime = "androidx.compose.runtime:runtime:${Versions.composeVersion}"
        const val composeUI = "androidx.compose.ui:ui:${Versions.composeVersion}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.composeVersion}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.composeVersion}"
        const val composeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}"
        const val composeUITooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
        const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
        const val composeCoil = "io.coil-kt:coil-compose:${Versions.composeCoil}"
        const val composePager = "com.google.accompanist:accompanist-pager:${Versions.composePager}"
        const val composeThemeAdapter = "com.google.android.material:compose-theme-adapter:${Versions.composeVersion}"
        const val composeMedia3Exo = "androidx.media3:media3-exoplayer:1.0.0-alpha03"
        const val composeMedia3UI = "androidx.media3:media3-ui:1.0.0-alpha03"
        const val iconShadow = "com.github.devlight.shadowlayout:library:1.0.2"
    }

    object intUit {
        const val sdp = "com.intuit.sdp:sdp-android:${Versions.intUiVersion}"
        const val ssp = "com.intuit.ssp:ssp-android:${Versions.intUiVersion}"
    }

    object Conviva {
        const val core = "com.conviva.sdk:conviva-core-sdk:4.0.23"
        const val exoPlayer = "com.conviva.sdk:conviva-exoplayer-sdk:4.0.16"
    }

    object ExoPlayer {
        const val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoPlayerVersion}"
        const val exoPlayerImaExtension = "com.google.android.exoplayer:extension-ima:${Versions.exoPlayerVersion}"
        const val mediaSession = "com.google.android.exoplayer:extension-mediasession:${Versions.exoPlayerVersion}"
    }

    object FaceBook {
        const val facebookSdk = "com.facebook.android:facebook-login:latest.release"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
        const val share = "com.facebook.android:facebook-share:13.2.0"
    }

    object Tus {
        const val androidClient = "io.tus.android.client:tus-android-client:0.1.10"
        const val javaClient = "io.tus.java.client:tus-java-client:0.4.5"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
        const val okhttpIntegration = "com.github.bumptech.glide:okhttp3-integration:4.11.0"
    }

    object Koin{
        const val android= "io.insert-koin:koin-android:3.3.0"
        const val core= "io.insert-koin:koin-core:${Versions.koinVersion}"
        const val fragment = "org.koin:koin-androidx-fragment:${Versions.koinVersion}"
    }

    object Room{
        const val runtime = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val ktx = "androidx.room:room-ktx:${Versions.roomVersion}"
        const val compiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    }

    object ChuckerTeam{
        const val chuker = "com.github.chuckerteam.chucker:library:${Versions.chukerVersion}"
        const val chukerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chukerVersion}"
    }

    object Test {
        const val jUnit = "junit:junit:${Versions.junitVersion}"
        const val androidXTestRunner = "androidx.test:runner:1.3.0"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:3.3.0"
        const val ext = "androidx.test.ext:junit:1.1.3"
        const val testRule = "androidx.test:rules:1.4.0"
        const val core = "androidx.test:core:1.4.0"
        const val roboelectric = "org.robolectric:robolectric:4.3"
        const val mockito = "org.mockito:mockito-core:4.6.0"
        const val mockitoInline = "org.mockito:mockito-inline:4.6.0"
        const val coroutinesTestAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val apolloTesting = "com.apollographql.apollo3:apollo-testing-support:${Versions.apolloVersion}"
        const val koinTesting = "io.insert-koin:koin-test:${Versions.koinVersion}"
        const val koinJUnitTesting = "io.insert-koin:koin-test-junit4:${Versions.koinVersion}"
        const val okhttpMockWebServer = "com.squareup.okhttp3:mockwebserver:4.10.0"
        const val pagingTest = "androidx.paging:paging-common:3.1.1"
    }

    object Sentry{
        const val sentryBom = "io.sentry:sentry-bom:6.12.1"
        const val android = "io.sentry:sentry-android"
        const val apollo3 = "io.sentry:sentry-apollo-3"
        const val okhttp = "io.sentry:sentry-android-okhttp"
    }

    object Clevertap {
        const val clevertap = "com.clevertap.android:clevertap-android-sdk:4.7.5"
        const val coreClevertap = "androidx.core:core:1.3.0"
        const val geofanceClevertap = "com.clevertap.android:clevertap-geofence-sdk:1.1.0"
        const val workClevertap = "androidx.work:work-runtime:2.7.0"
        const val concurrentClevertap = "androidx.concurrent:concurrent-futures:1.1.0"
        const val rendermaxClevertap = "com.clevertap.android:clevertap-rendermax-sdk:1.0.3"
    }

    object LeakCanary {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.10"
    }

    @JvmStatic
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase(java.util.Locale.ROOT).contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
}

