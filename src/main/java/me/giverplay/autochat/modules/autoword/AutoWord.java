package me.giverplay.autochat.modules.autoword;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.utils.ThreadUtils;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoWord {
  private static final Pattern PATTERN = Pattern.compile("\\[Palavra] Digite a seguinte palavra no chat: (\\w+)");
  private static final Pattern IGNORE_PATTERN = Pattern.compile("\\[[gl]]");

  private final AutoChat addon;
  private final AutoWordConfig config;

  public AutoWord(AutoChat addon) {
    this.addon = addon;
    this.config = new AutoWordConfig();
  }

  public void loadConfig(JsonObject config) {
    this.config.loadConfig(config);
  }

  public void fillSettings(List<SettingsElement> settings) {
    this.config.fillSettings(addon, settings);
  }

  @SubscribeEvent
  public void onChatReceived(ClientChatReceivedEvent event) {
    if (!config.isEnabled()) return;

    String message = event.message.getUnformattedText();

    if (config.ignoreChats() && IGNORE_PATTERN.matcher(message).find()) return;

    Matcher matcher = PATTERN.matcher(message);

    if(!matcher.find()) return;

    sendChat(matcher.group(1));
  }

  private void sendChat(String word) {
    int sleepTime = config.getBaseTime() + word.length() * config.getCharTime();

    new Thread(() -> {
      ThreadUtils.sleep(sleepTime);
      Minecraft.getMinecraft().thePlayer.sendChatMessage(word);
    }).start();
  }
}
