# chatbot-android

Use the below in your sample project to compile and check the chat bot library uploaded in github.


1.	In gradle.properties file paste the below line:
authToken=jp_7ktlteibdj69kfkdg2rm9ash2i

2.	In project gradle file past the highlighted lines inside repository as given below,
allprojects {
    repositories {
       maven {
            url "https://jitpack.io"
            credentials { username authToken }
        }
    }
}

3.	In app gradle file past the below line to access chat bot library from github
implementation 'com.github.cbdmobile:chatbot-android:1.6'
