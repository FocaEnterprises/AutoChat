package me.giverplay.autochat;

import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.modules.autofastclick.AutoFastClick;
import me.giverplay.autochat.modules.autojackpot.AutoJackpot;
import me.giverplay.autochat.modules.autolottery.AutoLottery;
import me.giverplay.autochat.modules.automath.AutoMath;
import me.giverplay.autochat.modules.autoword.AutoWord;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AutoChat extends LabyModAddon {

  private final Logger logger = LogManager.getLogger("AutoChat");
  private final Set<ChatModule> modules = new HashSet<>();

  public AutoChat() {
    registerModule(new AutoMath(this));
    registerModule(new AutoWord(this));
    registerModule(new AutoJackpot(this));
    registerModule(new AutoLottery(this));
    registerModule(new AutoFastClick(this));
  }

  @Override
  public void onEnable() {
    logger.info("Enabling AutoChat");
    logger.info("Loading modules");
    modules.forEach(ChatModule::enable);
  }

  @Override
  protected void fillSettings(List<SettingsElement> settings) {
    modules.forEach(module -> module.fillSettings(settings));
  }

  @Override
  public void loadConfig() {
    modules.forEach(module -> module.loadConfig(getConfig()));
  }

  public void registerModule(ChatModule module) {
    if(modules.contains(module)) {
      throw new IllegalArgumentException("Module already registered");
    }

    modules.add(module);
  }

  public Logger getLogger() {
    return logger;
  }
}
