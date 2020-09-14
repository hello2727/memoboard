# J's 메모장
>앨범과 같이 사진과 글을 저장할 수 있는 좀 더 업그레이드 된 메모장 어플리케이션


## 2. 환경설정
- Build.gradle(module)
```
// ted permission
    implementation 'gun0912.ted:tedpermission:2.1.1'
    // Gson
    implementation 'com.google.code.gson:gson:2.8.5'
    // retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0'
    // rxJava
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.0'
    // okhttp
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    // naver map api
    implementation 'com.naver.maps.open:naver-map-api:2.1.2@aar'
    // firebase dynamic link
    implementation 'com.google.firebase:firebase-core:16.0.4'
    implementation 'com.google.firebase:firebase-dynamic-links:16.1.2'
    // glide
    implementation 'com.github.bumptech.glide:okhttp3-integration:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
```
- Manifest
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.open">

    //서버 이미지 로딩(PICASSO)
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".Details"/>
        <activity android:name=".AddActivity" />
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
