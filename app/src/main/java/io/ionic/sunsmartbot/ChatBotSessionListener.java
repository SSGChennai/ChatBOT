package io.ionic.sunsmartbot;

import android.content.Context;

import java.io.Serializable;

public interface ChatBotSessionListener  extends Serializable{

    void onUserInteraction(Context context);

}
