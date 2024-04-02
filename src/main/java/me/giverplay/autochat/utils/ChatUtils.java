package me.giverplay.autochat.utils;

import net.minecraft.client.Minecraft;

public class ChatUtils {
  public static void sendChat(String message) {
    Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
  }
}
