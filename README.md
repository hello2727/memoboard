# J's 메모장
>앨범과 같이 사진과 글을 저장할 수 있는 좀 더 업그레이드 된 메모장 어플리케이션

## 결과물
---------------------------
<div>
  <img width="300", src="https://user-images.githubusercontent.com/43267195/93054095-cf862a80-f6a3-11ea-8784-44fa7567d021.jpg">
  <img width="300", src="https://user-images.githubusercontent.com/43267195/93054148-e3ca2780-f6a3-11ea-976b-4dc96fd4ad58.jpg">
  <img width="300", src="https://user-images.githubusercontent.com/43267195/93054199-f7758e00-f6a3-11ea-9d2d-89922d726371.jpg">
    
</div>


## 환경설정
----------------------------
- Build.gradle(module)
```
apply plugin: 'com.android.application'
apply plugin: 'realm-android' //realm 데이터베이스

android {
    compileSdkVersion 28
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "com.example.open"
        minSdkVersion 21
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    realm { //realm 데이터베이스
        syncEnabled = true;
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.google.android.material:material:1.1.0' //디자인
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
}
```

- Build.gradle(project)
```
buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:4.0.1'
        classpath "io.realm:realm-gradle-plugin:3.1.4" //realm 데이터베이스
        
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        mavenLocal()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
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
