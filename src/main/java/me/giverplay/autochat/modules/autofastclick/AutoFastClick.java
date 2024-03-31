package me.giverplay.autochat.modules.autofastclick;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.logging.Logger;

public class AutoFastClick {
  @SubscribeEvent
  public void onChatReceived(ClientChatReceivedEvent event) {
    System.out.println("catarroooooooo");
    IChatComponent message = event.message;
    ChatStyle chatStyle = message.getChatStyle();

    if(chatStyle != null) {
      ClickEvent chatClickEvent = chatStyle.getChatClickEvent();

      if(chatClickEvent != null) {
        System.out.println("Tem click event");
        System.out.println("Raw: " + message.getUnformattedText());
        System.out.println("Formatted: " + message.getFormattedText().replace('\u00A7', '&'));
        System.out.println("Action " + chatClickEvent.getAction());
        System.out.println("Value + " + chatClickEvent.getValue());
        System.out.println();
        System.out.println();
      }
    }

    message.getSiblings().forEach(sib -> {
      ChatStyle st = sib.getChatStyle();

      if(st != null) {
        ClickEvent cc = st.getChatClickEvent();

        if(cc != null) {
          System.out.println("SIBLINGS Tem click event");
          System.out.println("Raw: " + sib.getUnformattedText());
          System.out.println("Formatted: " + sib.getFormattedText().replace('\u00A7', '&'));
          System.out.println("Action: " + cc.getAction());
          System.out.println("Value: " + cc.getValue());
          System.out.println();
          System.out.println();
        }
      }
    });
  }
}
