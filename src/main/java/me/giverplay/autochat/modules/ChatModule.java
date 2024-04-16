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

  protected ChatModuleConfig config;

  public ChatModule(AutoChat addon, String name, Pattern pattern) {
    this.addon = addon;
    this.name = name;
    this.pattern = pattern;
    initConfig();
  }

  @SubscribeEvent
  public final void onChatReceived(ClientChatReceivedEvent event) {
    if(config != null && !config.isEnabled()) return;

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

  public final void fillSettings(List<SettingsElement> settings) {
    config.fillSettings(settings);
  }

  public final void loadConfig(JsonObject config) {
    JsonObject section = getSection(config);
    this.config.loadConfig(section);
  }

  public final void saveConfig(JsonObject config) {
    JsonObject section = getSection(config);
    this.config.saveConfig(section);
  }

  private JsonObject getSection(JsonObject config) {
    if(!config.has(name)) {
      config.add(name, new JsonObject());
    }

    return config.getAsJsonObject(name);
  }

  public abstract void onChat(IChatComponent message, Matcher matcher);

  protected abstract void initConfig();

  public String getName() {
    return name;
  }

  public AutoChat getAddon() {
    return addon;
  }

  public ChatModuleConfig getConfig() {
    return config;
  }
}
