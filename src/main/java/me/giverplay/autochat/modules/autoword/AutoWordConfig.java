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
  private static final String BASE_TIME = "AutoWordBaseTime";
  private static final String CHAR_TIME = "AutoWordCharTime";

  private boolean enableAutoWord;
  private int baseTime;
  private int charTime;

  public void fillSettings(AutoChat addon, List<SettingsElement> settings) {
    settings.add(new HeaderElement("AutoWord"));
    settings.add(new BooleanElement("Enable AutoWord", addon, new ControlElement.IconData(Material.LEVER), ENABLE, this.enableAutoWord));

    NumberElement baseTime = new NumberElement("Reply base time", new ControlElement.IconData(Material.WATCH), this.baseTime);
    baseTime.setConfigEntryName(BASE_TIME);
    baseTime.setMinValue(0);
    baseTime.setMaxValue(10000);
    settings.add(baseTime);

    NumberElement charTime = new NumberElement("Delay for each character", new ControlElement.IconData(Material.WATCH), this.charTime);
    charTime.setConfigEntryName(CHAR_TIME);
    charTime.setMinValue(0);
    charTime.setMaxValue(10000);
    settings.add(charTime);
  }

  public void loadConfig(JsonObject config) {
    this.enableAutoWord = config.has(ENABLE) && config.get(ENABLE).getAsBoolean();
    this.baseTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 800;
    this.charTime = config.has(CHAR_TIME) ? config.get(CHAR_TIME).getAsInt() : 200;
  }

  public boolean isEnabled() {
    return enableAutoWord;
  }

  public int getBaseTime() {
    return baseTime;
  }

  public int getCharTime() {
    return charTime;
  }
}
