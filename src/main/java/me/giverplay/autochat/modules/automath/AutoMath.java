package me.giverplay.autochat.modules.automath;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ThreadUtils;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoMath extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("\\[Matem(.+)?tica] Quanto (.+)? (\\d+) ([+\\-*/xX]) (\\d+)\\?");
  private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();

  private final AutoMathConfig config;

  public AutoMath(AutoChat addon) {
    super(addon, PATTERN);
    this.config = new AutoMathConfig();
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

    try {
      int result = evaluateExpression(matcher);
      sendChat(result);
    } catch (Exception ignore) {
      IChatComponent chat = new ChatComponentText("[AutoMath] Failed to parse expression.")
        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
      Minecraft.getMinecraft().thePlayer.addChatMessage(chat);
    }
  }

  private void sendChat(final int result) {
    final String toSend = Integer.toString(result);

    int sleep = config.getBaseReplyTime() + new Random().nextInt(config.getRandomTimeRange());
    int decrease = config.getEasyDecreaseTime();

    if (result < 1000) sleep -= decrease;
    if (result < 100) sleep -= decrease;

    final int sleepTime = Math.max(sleep, 0);

    new Thread(() -> {
      ThreadUtils.sleep(sleepTime);
      Minecraft.getMinecraft().thePlayer.sendChatMessage(toSend);
    }).start();
  }

  private int evaluateExpression(Matcher matcher) {
    String op = matcher.group(4);
    int a = Integer.parseInt(matcher.group(3));
    int b = Integer.parseInt(matcher.group(5));

    return OPERATIONS.get(op).apply(a, b);
  }

  static {
    OPERATIONS.put("+", Integer::sum);
    OPERATIONS.put("-", (a, b) -> a - b);
    OPERATIONS.put("/", (a, b) -> a / b);
    OPERATIONS.put("x", (a, b) -> a * b);
    OPERATIONS.put("X", (a, b) -> a * b);
    OPERATIONS.put("*", (a, b) -> a * b);
  }
}
