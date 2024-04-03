package me.giverplay.autochat.modules.autoword;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.util.IChatComponent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoWord extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("\\[Palavra] Digite a seguinte palavra no chat: (\\w+)");

  private final AutoWordConfig config;

  public AutoWord(AutoChat addon) {
    super(addon, PATTERN);
    this.config = new AutoWordConfig();
  }

  @Override
  public void fillSettings(List<SettingsElement> settings) {
    this.config.fillSettings(addon, settings);
  }

  @Override
  public void loadConfig(JsonObject config) {
    this.config.loadConfig(config);
  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    if(!config.isEnabled()) return;
    sendChat(matcher.group(1));
  }

  private void sendChat(String word) {
    int sleepTime = config.getBaseTime() + word.length() * config.getCharTime();
    ThreadUtils.delayed(() -> ChatUtils.sendChat(word), sleepTime);
  }
}
