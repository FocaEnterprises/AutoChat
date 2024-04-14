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

  private final NumberElement baseTimeElement;
  private final NumberElement charTimeElement;

  private int baseTime;
  private int charTime;

  public AutoWordConfig(AutoWord module) {
    super(module);

    baseTimeElement = new NumberElement("Reply base time", new ControlElement.IconData(Material.WATCH));
    baseTimeElement.setMinValue(0);
    baseTimeElement.setMaxValue(10000);

    charTimeElement = new NumberElement("Delay for each character", new ControlElement.IconData(Material.WATCH));
    charTimeElement.setMinValue(0);
    charTimeElement.setMaxValue(10000);
  }

  @Override
  public void fillSettings(List<SettingsElement> settings) {
    super.fillSettings(settings);
    settings.add(baseTimeElement);
    settings.add(charTimeElement);
  }

  @Override
  public void loadConfig(JsonObject config) {
    this.baseTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 800;
    this.charTime = config.has(CHAR_TIME) ? config.get(CHAR_TIME).getAsInt() : 200;
  }

  @Override
  public void saveConfig(JsonObject config) {
    super.saveConfig(config);
    config.addProperty(BASE_TIME, baseTimeElement.getCurrentValue());
    config.addProperty(CHAR_TIME, charTimeElement.getCurrentValue());
  }

  public int getBaseTime() {
    return baseTime;
  }

  public int getCharTime() {
    return charTime;
  }
}
