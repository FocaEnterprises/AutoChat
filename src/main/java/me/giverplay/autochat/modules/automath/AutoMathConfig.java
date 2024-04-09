package me.giverplay.autochat.modules.automath;

import com.google.gson.JsonObject;
import me.giverplay.autochat.modules.ChatModuleConfig;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.NumberElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class AutoMathConfig extends ChatModuleConfig {

  private static final String BASE_TIME = "AutoMathBaseReplyTime";
  private static final String RANDOM_TIME = "AutoMathRandomTimeRange";
  private static final String DECREASE_TIME = "AutoMathEasyDecreaseTime";

  private int baseReplyTime;
  private int randomTimeRange;
  private int easyDecrease;

  public AutoMathConfig(AutoMath module) {
    super(module);
  }

  @Override
  public void fillSettings(List<SettingsElement> settings) {
    super.fillSettings(settings);

    NumberElement baseTime = new NumberElement("Reply base time", new ControlElement.IconData(Material.WATCH), this.baseReplyTime);
    baseTime.setConfigEntryName(BASE_TIME);
    baseTime.setMinValue(0);
    baseTime.setMaxValue(10000);
    settings.add(baseTime);

    NumberElement randomRange = new NumberElement("Random time range", new ControlElement.IconData(Material.DIODE), this.randomTimeRange);
    randomRange.setConfigEntryName(RANDOM_TIME);
    randomRange.setMinValue(10);
    randomRange.setMaxValue(10000);
    settings.add(randomRange);

    NumberElement easyDecreaseTime = new NumberElement("Time to decrease when easy", new ControlElement.IconData(Material.FEATHER), this.easyDecrease);
    easyDecreaseTime.setConfigEntryName(DECREASE_TIME);
    easyDecreaseTime.setMinValue(0);
    easyDecreaseTime.setMaxValue(10000);
    settings.add(easyDecreaseTime);
  }

  @Override
  public void loadConfig(JsonObject config) {
    super.loadConfig(config);

    this.randomTimeRange = config.has(RANDOM_TIME) ? config.get(RANDOM_TIME).getAsInt() : 1000;
    this.baseReplyTime = config.has(BASE_TIME) ? config.get(BASE_TIME).getAsInt() : 5000;
    this.easyDecrease = config.has(DECREASE_TIME) ? config.get(DECREASE_TIME).getAsInt() : 1000;
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
