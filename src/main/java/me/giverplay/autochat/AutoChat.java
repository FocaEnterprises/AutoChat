package me.giverplay.autochat;

import me.giverplay.autochat.modules.automath.AutoMath;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class AutoChat extends LabyModAddon {


  private final AutoMath autoMath;

  public AutoChat() {
    this.autoMath = new AutoMath(this);
  }

  @Override
  public void onEnable() {
    getApi().registerForgeListener(autoMath);
  }

  @Override
  protected void fillSettings(List<SettingsElement> settings) {
    autoMath.fillSettings(settings);
  }

  @Override
  public void loadConfig() {
    autoMath.loadConfig(getConfig());
  }
}
