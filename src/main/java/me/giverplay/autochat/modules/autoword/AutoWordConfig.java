package me.giverplay.autochat.modules.autoword;

import com.google.gson.JsonObject;
import me.giverplay.autochat.modules.ChatModuleConfig;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class AutoWordConfig extends ChatModuleConfig {

  private static final String BASE_TIME = "AutoWordBaseTime";
  private static final String CHAR_TIME = "AutoWordCharTime";

  private int baseTime;
  private int charTime;

  public AutoWordConfig(AutoWord module) {
    super(module);
  }

  @Override
  public void fillSettings(List<SettingsElement> settings) {
    super.fillSettings(settings);

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

  @Override
  public void loadConfig(JsonObject config) {
    this.baseTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 800;
    this.charTime = config.has(CHAR_TIME) ? config.get(CHAR_TIME).getAsInt() : 200;
  }

  public int getBaseTime() {
    return baseTime;
  }

  public int getCharTime() {
    return charTime;
  }
}
