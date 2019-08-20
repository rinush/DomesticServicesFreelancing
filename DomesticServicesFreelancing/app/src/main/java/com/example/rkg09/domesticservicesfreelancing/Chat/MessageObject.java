package com.example.rkg09.domesticservicesfreelancing.Chat;

/**
 * Created by rkg09 on 3/29/2019.
 */

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageObject {

    String  messageId,
            senderId,
            message;

    ArrayList<String> mediaUrlList;

    public MessageObject(String messageId, String senderId, String message, ArrayList<String> mediaUrlList){
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;

        //this.mediaUrlList = mediaUrlList;
    }

    public String getMessageId() {
        return messageId;
    }
    public String getSenderId() {
        return senderId;
    }
    public String getMessage() {
        return message;
    }
    public ArrayList<String> getMediaUrlList() {
        return mediaUrlList;
    }

}
