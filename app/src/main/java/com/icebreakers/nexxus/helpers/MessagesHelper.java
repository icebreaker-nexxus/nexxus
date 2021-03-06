package com.icebreakers.nexxus.helpers;

import android.content.Context;
import com.icebreakers.nexxus.models.Message;
import com.icebreakers.nexxus.models.Profile;
import com.icebreakers.nexxus.models.messaging.UIMessage;
import com.icebreakers.nexxus.models.messaging.User;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by amodi on 4/13/17.
 */

public class MessagesHelper {

    public static String getMessageRowId(Profile profile1, Profile profile2) {
        if (profile1.id.compareTo(profile2.id) > 0) {
            return generateMessageId(profile1.id, profile2.id);
        } else {
            return generateMessageId(profile2.id, profile1.id);
        }
    }

    public static String getMessageRowId(String id1, String id2) {
        if (id1.compareTo(id2) > 0) {
            return generateMessageId(id1, id2);
        } else {
            return generateMessageId(id2, id1);
        }
    }

    private static String generateMessageId(String id1, String id2) {
        return id1 + "?" + id2;
    }

    public static UIMessage convertFromDbMessageModelToUIMessage(Message message, Profile profile1, Profile profile2) {
        User sender = message.senderId.equals(profile1.id) ? new User(profile1) : new User(profile2);
        UIMessage uiMessage = new UIMessage(message, sender);
        return uiMessage;
    }

    public static Message convertFromUIMessageToDbMessage(UIMessage uiMessage, Profile profile) {
        Message message = new Message();
        message.id = uiMessage.getId();
        message.senderId = profile.id;
        message.text = uiMessage.getText();
        message.timestamp = uiMessage.getCreatedAt().getTime();
        return message;
    }

    public static void sendMessage(Context context, String senderProfileId, String receiverProfileId, String messageBody) {
        Message message = new Message();
        message.timestamp = Calendar.getInstance().getTimeInMillis();
        message.text = messageBody;
        message.id = UUID.randomUUID().toString();
        message.senderId = senderProfileId;
        message.receiverId = receiverProfileId;

        ProfileHolder profileHolder = ProfileHolder.getInstance(context);
        profileHolder.saveMessage(getMessageRowId(senderProfileId, receiverProfileId), message);

    }

}
