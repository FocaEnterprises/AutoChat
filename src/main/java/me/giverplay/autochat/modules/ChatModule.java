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
  protected final String name;

  public ChatModule(AutoChat addon, String name, Pattern pattern) {
    this.addon = addon;
    this.name = name;
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

  public final void enable() {
    addon.getLogger().info("Enabling module " + name);
    addon.getApi().registerForgeListener(this);
  }

  public abstract void fillSettings(List<SettingsElement> settings);

  public abstract void loadConfig(JsonObject config);

  public abstract void onChat(IChatComponent message, Matcher matcher);

  public String getName() {
    return name;
  }
}
