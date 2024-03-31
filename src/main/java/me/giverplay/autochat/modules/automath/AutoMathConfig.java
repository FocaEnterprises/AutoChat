package me.giverplay.autochat.modules.automath;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class AutoMathConfig {

  private static final String ENABLE = "AutoMathEnable";
  private static final String BASE_TIME = "AutoMathBaseReplyTime";
  private static final String RANDOM_TIME = "AutoMathRandomTimeRange";
  private static final String DECREASE_TIME = "AutoMathEasyDecreaseTime";

  private boolean enableAutoMath;
  private int baseReplyTime;
  private int randomTimeRange;
  private int easyDecrease;

  public void fillSettings(AutoChat addon, List<SettingsElement> settings) {
    settings.add(new HeaderElement("AutoMath"));
    settings.add(new BooleanElement("Enable AutoMath", addon, new ControlElement.IconData(Material.LEVER), ENABLE, this.enableAutoMath));

    NumberElement baseTime = new NumberElement("Reply base time", new ControlElement.IconData(Material.WATCH), this.baseReplyTime);
    baseTime.setConfigEntryName(BASE_TIME);
    settings.add(baseTime);

    NumberElement randomRange = new NumberElement("Random time range", new ControlElement.IconData(Material.DIODE), this.randomTimeRange);
    randomRange.setConfigEntryName(RANDOM_TIME);
    settings.add(randomRange);

    NumberElement easyDecreaseTime = new NumberElement("Time to decrease when easy", new ControlElement.IconData(Material.FEATHER), this.easyDecrease);
    easyDecreaseTime.setConfigEntryName(DECREASE_TIME);
    settings.add(easyDecreaseTime);
  }

  public void loadConfig(JsonObject config) {
    this.enableAutoMath = config.has(ENABLE) && config.get(ENABLE).getAsBoolean();
    this.randomTimeRange = config.has(RANDOM_TIME) ? config.get(RANDOM_TIME).getAsInt() : 1000;
    this.baseReplyTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 5000;
    this.easyDecrease = config.has(DECREASE_TIME) ? config.get(DECREASE_TIME).getAsInt() : 1000;
  }

  public boolean isEnabled() {
    return enableAutoMath;
  }

  public int getBaseReplyTime() {
    return baseReplyTime;
  }

  public int getRandomTimeRange() {
    return randomTimeRange;
  }

  public int getEasyDecreaseTime() {
    return easyDecrease;
  }
}
