package me.giverplay.autochat;

import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.modules.automath.AutoMath;
import me.giverplay.autochat.modules.autoword.AutoWord;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutoChat extends LabyModAddon {

  private final Set<ChatModule> modules = new HashSet<>();

  public AutoChat() {
    modules.add(new AutoMath(this));
    modules.add(new AutoWord(this));
  }

  @Override
  public void onEnable() {
    modules.forEach(getApi()::registerForgeListener);
  }

  @Override
  protected void fillSettings(List<SettingsElement> settings) {
    modules.forEach(module -> module.fillSettings(settings));
  }

  @Override
  public void loadConfig() {
    modules.forEach(module -> module.loadConfig(getConfig()));
  }
}
