package me.giverplay.autochat;

import me.giverplay.autochat.modules.automath.AutoMath;
import me.giverplay.autochat.modules.autoword.AutoWord;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class AutoChat extends LabyModAddon {

  private final AutoMath autoMath;
  private final AutoWord autoWord;

  public AutoChat() {
    this.autoMath = new AutoMath(this);
    this.autoWord = new AutoWord(this);
  }

  @Override
  public void onEnable() {
    getApi().registerForgeListener(autoMath);
    getApi().registerForgeListener(autoWord);
  }

  @Override
  protected void fillSettings(List<SettingsElement> settings) {
    autoMath.fillSettings(settings);
    autoWord.fillSettings(settings);
  }

  @Override
  public void loadConfig() {
    autoMath.loadConfig(getConfig());
    autoWord.loadConfig(getConfig());
  }
}
