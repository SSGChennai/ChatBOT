package io.ionic.sunsmartbot;

public class ChatBotSessionHandler {

    // static variable single_instance of type Singleton
    private static ChatBotSessionHandler chatBotSessionHandler = null;

    private ChatBotSessionListener listener;

    public ChatBotSessionListener getListener() {
        return listener;
    }

    public void setListener(ChatBotSessionListener listener) {
        this.listener = listener;
    }

    // static method to create instance of Singleton class
    public static ChatBotSessionHandler getInstance() {
        if (chatBotSessionHandler == null) {
            chatBotSessionHandler = new ChatBotSessionHandler();
        }
        return chatBotSessionHandler;
    }

    public void initializeSessionListener(ChatBotSessionListener listener) {
        setListener(listener);
    }
}
