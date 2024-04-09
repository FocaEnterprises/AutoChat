package me.giverplay.autochat.modules.autofastclick;

import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoFastClick extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("\\[[Xx]]");

  public AutoFastClick(AutoChat addon) {
    super(addon, "AutoFastClick", PATTERN);
  }

  @Override
  protected void initConfig() {

  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    checkComponent(message);
  }

  private void checkComponent(IChatComponent component) {
    String text = component.getFormattedText().replace('\u00A7', '&');

    if(text.contains("&e")) {
      ChatStyle style = component.getChatStyle();

      if (style != null) {
        ClickEvent clickEvent = style.getChatClickEvent();

        if (clickEvent != null && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
          sendMessage(clickEvent.getValue());
          return;
        }
      }
    }

    component.getSiblings().forEach(this::checkComponent);
  }

  private void sendMessage(String msg) {
    ThreadUtils.delayed(() -> ChatUtils.sendChat(msg), 1000);
  }
}
