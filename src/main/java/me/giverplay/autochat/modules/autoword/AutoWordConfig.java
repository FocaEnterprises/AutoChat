package me.giverplay.autochat.modules.autoword;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class AutoWordConfig {

  private static final String ENABLE = "AutoWordEnable";
  private static final String IGNORE_CHAT = "AutoWordIgnoreChats";
  private static final String BASE_TIME = "AutoWordBaseTime";
  private static final String CHAR_TIME = "AutoWordCharTime";

  private boolean enableAutoWord;
  private boolean ignoreChats;
  private int baseTime;
  private int charTime;

  public void fillSettings(AutoChat addon, List<SettingsElement> settings) {
    settings.add(new HeaderElement("AutoWord"));
    settings.add(new BooleanElement("Enable AutoWord", addon, new ControlElement.IconData(Material.LEVER), ENABLE, this.enableAutoWord));
    settings.add(new BooleanElement("Ignore local and global chats", addon, new ControlElement.IconData(Material.NAME_TAG), IGNORE_CHAT, this.ignoreChats));

    NumberElement baseTime = new NumberElement("Reply base time", new ControlElement.IconData(Material.WATCH), this.baseTime);
    baseTime.setConfigEntryName(BASE_TIME);
    settings.add(baseTime);

    NumberElement charTime = new NumberElement("Delay for each character", new ControlElement.IconData(Material.WATCH), this.charTime);
    charTime.setConfigEntryName(CHAR_TIME);
    settings.add(charTime);
  }

  public void loadConfig(JsonObject config) {
    this.enableAutoWord = config.has(ENABLE) && config.get(ENABLE).getAsBoolean();
    this.ignoreChats = config.has(IGNORE_CHAT) && config.get(IGNORE_CHAT).getAsBoolean();
    this.baseTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 800;
    this.charTime = config.has(CHAR_TIME) ? config.get(CHAR_TIME).getAsInt() : 200;
  }

  public boolean isEnabled() {
    return enableAutoWord;
  }

  public boolean ignoreChats() {
    return ignoreChats;
  }

  public int getBaseTime() {
    return baseTime;
  }

  public int getCharTime() {
    return charTime;
  }
}
