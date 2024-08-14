# chatbot-android

Use the below in your sample project to compile and check the chat bot library uploaded in github.

1.import implementation in build.gradle(app) file
implementation("com.github.SSGChennai:ChatBOT:main-SNAPSHOT")

2.add permissions in AndroidManifest.xml file
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.SOME_PERMISSION" />
<uses-permission android:name="android.permission.QUERY" />

3.call package in AndroidManifest.xml file
<queries>
        <package android:name="herbiebot.sunsmart.com.bot" />
</queries>

4.add maven

allprojects {
repositories {
maven {
url = uri("https://jitpack.io")
}
}
}

5.call where evey want function give blow
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

Intent intent = getPackageManager().getLaunchIntentForPackage("herbiebot.sunsmart.com.bot");
if (intent != null) {
startActivity(intent);
} else {
Snackbar.make(view, "Not able to access package", Snackbar.LENGTH_LONG)
.setAnchorView(R.id.fab)
.setAction("Action", null).show();
}

