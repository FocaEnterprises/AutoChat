package me.giverplay.autochat.modules;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ChatModule {

  protected final AutoChat addon;
  protected final Pattern pattern;

  public ChatModule(AutoChat addon, Pattern pattern) {
    this.addon = addon;
    this.pattern = pattern;
  }

  @SubscribeEvent
  public final void onChatReceived(ClientChatReceivedEvent event) {
    IChatComponent message = event.message;
    Matcher matcher = pattern.matcher(message.getUnformattedText());

    if(matcher.find()) {
      onChat(message, matcher);
    }
  }

  public abstract void fillSettings(List<SettingsElement> settings);

  public abstract void loadConfig(JsonObject config);

  public abstract void onChat(IChatComponent message, Matcher matcher);
}
