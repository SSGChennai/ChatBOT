package io.ionic.sunsmartbot;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class UnreadMessage {

    private static final String PREFS_NAME = "NativeStorage";
    private static final String KEY= "CBDBot";


    public  int getUnReadCount(Context context)
    {

        int unreadcount=0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.toString(), MODE_PRIVATE);
//        System.out.println("********---------    shared pref values...   " +  sharedPreferences.getString("CBDBot", ""));

        String value = getValue(context, KEY , null);
        if(value!="" && value!=null){
            try {
                JSONArray jsonArray = new JSONArray(value);


                for(int i=0;i<jsonArray.length();i++){
                    JSONObject explrObject = jsonArray.getJSONObject(i);
                    if(explrObject.getString("unreadCount")!=null)
                    {
                        if(Integer.parseInt(explrObject.getString("unreadCount"))!=0){
                            unreadcount++;
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return unreadcount;


    }
    String getValue(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
}
