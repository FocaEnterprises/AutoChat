package me.giverplay.autochat.modules;

import com.google.gson.JsonObject;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Material;

import java.util.List;

public class ChatModuleConfig {
  private static final String ENABLE = "Enable";

  private final ChatModule module;

  private boolean isEnabled;

  private final BooleanElement enabledElement;

  public ChatModuleConfig(ChatModule module) {
    this.module = module;

    enabledElement = new BooleanElement("Enable module", ENABLE, new ControlElement.IconData(Material.LEVER));
  }

  public void fillSettings(List<SettingsElement> settings) {
    settings.add(enabledElement);
  }

  public void loadConfig(JsonObject config) {
    this.isEnabled = config.has(ENABLE) && config.get(ENABLE).getAsBoolean();
  }

  public void saveConfig(JsonObject config) {
    config.addProperty(ENABLE, enabledElement.getCurrentValue());
  }

  public boolean isEnabled() {
    return isEnabled;
  }
}
