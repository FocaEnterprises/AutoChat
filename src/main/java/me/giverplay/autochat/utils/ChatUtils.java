package me.giverplay.autochat.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
  public static void sendChat(String message) {
    Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
  }

  public static void addChat(String msg) {
    IChatComponent chat = new ChatComponentText(msg);
    Minecraft.getMinecraft().thePlayer.addChatMessage(chat);
  }
}
