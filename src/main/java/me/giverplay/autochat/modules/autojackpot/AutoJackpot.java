package me.giverplay.autochat.modules.autojackpot;

import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoJackpot extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("^\\[Bol(.+)o]");
  private static final Pattern COMPLETED = Pattern.compile("^\\[Bol(.+)o] O vencedor foi (.)!");

  private boolean isOpen;

  public AutoJackpot(AutoChat addon) {
    super(addon, "AutoJackPot", PATTERN);
  }

  @Override
  protected void initConfig() {

  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    String text = message.getUnformattedText();

    if(isOpen) {
      if(COMPLETED.matcher(text).find()) {
        isOpen = false;
        return;
      }

      return;
    }

    isOpen = true;
    ThreadUtils.delayed(() -> ChatUtils.sendChat("/bolao"), 1000);
  }
}
