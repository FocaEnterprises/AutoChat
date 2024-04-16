package me.giverplay.autochat.modules.autoword;

import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoWord extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("\\[Palavra] Digite a seguinte palavra no chat: (.+)");

  public AutoWord(AutoChat addon) {
    super(addon, "AutoWord", PATTERN);
    super.config = new AutoWordConfig(this);
  }

  @Override
  protected void initConfig() {
    super.config = new AutoWordConfig(this);
  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    sendChat(matcher.group(1));
  }

  private void sendChat(String word) {
    ChatUtils.builder("[AutoWord] Sending word '" + word + "'")
      .color(EnumChatFormatting.GREEN)
      .addToChat();

    int sleepTime = getConfig().getBaseTime() + word.length() * getConfig().getCharTime();
    ThreadUtils.delayed(() -> ChatUtils.sendChat(word), sleepTime);
  }

  @Override
  public AutoWordConfig getConfig() {
    return (AutoWordConfig) super.getConfig();
  }
}
