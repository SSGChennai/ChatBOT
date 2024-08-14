package io.ionic.sunsmartbot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.cordova.CordovaActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.worklight.androidgap.api.WL;
import com.worklight.androidgap.api.WLInitWebFrameworkResult;
import com.worklight.androidgap.api.WLInitWebFrameworkListener;

import com.ibm.MFPApplication;

import herbiebot.sunsmart.com.bot.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.worklight.androidgap.api.WL;
import com.worklight.common.Logger;
import com.worklight.common.WLAnalytics;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.PluginEntry;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends CordovaActivity implements WLInitWebFrameworkListener {
    private ConfigXmlParser parser;
    public static final MediaType SOAP_MEDIA_TYPE = MediaType.parse("text/xml;charset=utf-8");


    private String MobileLoginPilot = "https://www.cbd.ae/cbdbot/herbiemiddlewareservice/herbieMiddelwareservice.asmx?op=MobileLogin";
    private String MobileLoginUIT = "https://test.cbdonline.ae/cbdbot/herbiemiddelwareservice/herbieMiddelwareservice.asmx?op=MobileLogin";

    //	private String finalApi=null;
    private String finalApi = "https://www.cbd.ae/cbdbot/herbiemiddlewareservice/herbieMiddelwareservice.asmx?op=MobileLogin";
    private static final String PREFS_NAME = "NativeStorage";
    private static final String KEY = "CBDBot";

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    public String sessionId = null;
    public Boolean prodApi = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UnreadMessage unm = new UnreadMessage();

//		System.out.println("getUnReadCount :  " +  unm.getUnReadCount(MainActivity.this));

        if (ChatBotSessionHandler.getInstance().getListener() != null) {
            ChatBotSessionHandler.getInstance().getListener().onUserInteraction(this);
        }

        WL.createInstance(this);
        Logger.setContext(this);
        //WLAnalytics.init(this);
        WLAnalytics.addDeviceEventListener(WLAnalytics.DeviceEvent.NETWORK);
        WLAnalytics.addDeviceEventListener(WLAnalytics.DeviceEvent.LIFECYCLE);

        parser = new ConfigXmlParser();
        parser.parse(this);

        init();

        Bundle extras = getIntent().getExtras();

//        if(extras!=null && extras.getString("Environment")!=null && extras.getString("Environment").equalsIgnoreCase("PROD")){
//			prodApi=true;
//			finalApi=MobileLoginPilot;
//		}
//		else{
//			finalApi=MobileLoginUIT;
//		}

        if (extras != null && extras.getString("deviceId") != null) {

            if (extras.getString("sessionId") != null) {
                sessionId = extras.getString("sessionId");
            } else {
                sessionId = "0";
            }
            try {

                String soap_string = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <MobileLogin xmlns=\"http://tempuri.org/\">\n" +
                        "      <Terminalid>" + extras.getString("deviceId") + "</Terminalid>\n" +
                        "    </MobileLogin>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>";

                final OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(SOAP_MEDIA_TYPE, soap_string);


                final Request request = new Request.Builder()
                        .url(MobileLoginUIT)

                        .post(body)
                        .addHeader("Content-type", "text/xml;charset=utf-8")
                        .addHeader("Accept", "text/xml")
                        .addHeader("cache-control", "no-cache")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        String mMessage = e.getMessage().toString();
                        System.out.println("onResponse failuer " + mMessage);
                        String sendVlaue = "0";
                        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("CBDBot", '"' + sendVlaue + "~" + prodApi + '"');
                        editor.apply();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String mMessage = response.body().string();

                        System.out.println("onResponse= 1=================:" + mMessage);
                        //code = response.code();
                        getResponse(mMessage, response);

                    }

                    public void getResponse(String response, Response mainRes) {
                        try {
                            DocumentBuilder newDocumentBuilder =
                                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
                            final Document parse = newDocumentBuilder.parse(new
                                    ByteArrayInputStream(response.getBytes()));

                            String dataString = parse.getElementsByTagName("soap:Envelope").item(0).getTextContent();

                            SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            String sendVlaue = "0";

                            if (dataString.toString().contains("Index was outside the bounds of the array") || dataString.toString().contains("No records match the selection criteria of the request.")) {

                                //System.out.println("ServerServer JSONObject ============== :" + dataString.toString());

                                editor.putString("CBDBot", '"' + sendVlaue + "~" + prodApi + '"');
                                editor.apply();

                            } else {

                                editor.putString("CBDBot", '"' + dataString + "~" + sessionId + "~" + prodApi + '"');
                                editor.apply();

                            }


                            //System.out.println("CBDBot nativeStorage set  " +   sp.getString("CBDBot", "default value"));

                            WL.getInstance().initializeWebFramework(getApplicationContext(), MainActivity.this);


                            //ApiMethod(getStr[0], getStr[1], getStr[2]);


                        } catch (Exception ex) {
                            //It means RIM number dont have it will call prelogin
//							ApiMethod("0","0","0");

                            String sendVlaue = "0";
                            SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("CBDBot", '"' + sendVlaue + "~" + prodApi + '"');
                            editor.apply();


                            //System.out.println("JSONObject Exception :" + ex.toString());

                        }
                    }

                });


            } catch (Exception mMessage) {
                //It means RIM number dont have it will call prelogin


                String sendVlaue = "0";
                SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("CBDBot", '"' + sendVlaue + "~" + prodApi + '"');
                editor.apply();

                //System.out.println("Response SP Status Exception 1==================:" + mMessage);

                WL.getInstance().initializeWebFramework(getApplicationContext(), this);
            }
        } else {
            System.out.println("Normal bot");

            String sendVlaue = "0";
            SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("CBDBot", '"' + sendVlaue + "~" + prodApi + '"');

            editor.apply();
            WL.getInstance().initializeWebFramework(getApplicationContext(), this);

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        //System.out.println("MainActivity OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //System.out.println("MainActivity onResume");

        if (ChatBotSessionHandler.getInstance().getListener() != null) {
            ChatBotSessionHandler.getInstance().getListener().onUserInteraction(this);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    //Random number's
    String guid() {
        return Math.floor(Math.random() * Math.floor(10002)) + "-" + Math.floor(Math.random() * Math.floor(10002)) + "-" + Math.floor(Math.random() * Math.floor(10002));
    }

    public Boolean hasCordovaSplashscreen() {
        ArrayList<PluginEntry> plugins = parser.getPluginEntries();
        for (PluginEntry plugin : plugins) {
            if (plugin.pluginClass.equals("org.apache.cordova.splashscreen.SplashScreen")) {
                return true;
            }
        }
        return false;
    }

    /**
     * The IBM MobileFirst Platform calls this method after its initialization is complete and web resources are ready to be used.
     */
    public void onInitWebFrameworkComplete(WLInitWebFrameworkResult result) {
        if (result.getStatusCode() == WLInitWebFrameworkResult.SUCCESS) {
            final String mainHtmlFilePath = WL.getInstance().getMainHtmlFilePath();
            if (WL.getInstance().isIonicWebview() && !mainHtmlFilePath.toLowerCase().contains("/android_asset/www")) {
                try {
                    String webviewUrlPath = new URI(mainHtmlFilePath).getPath();
                    String webviewServerPath = webviewUrlPath.substring(0, webviewUrlPath.indexOf("/index.html"));
                    WL.getInstance().updateIonicBasePath(webviewServerPath, appView, this);

                } catch (Exception EX) {
                }
            } else {
                super.loadUrl(mainHtmlFilePath);
            }
        } else {
            handleWebFrameworkInitFailure(result);
        }
    }

    private void handleWebFrameworkInitFailure(WLInitWebFrameworkResult result) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setNegativeButton(R.string.close, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialogBuilder.setTitle(R.string.error);
        alertDialogBuilder.setMessage(result.getMessage());
        alertDialogBuilder.setCancelable(false).create().show();
    }

}
