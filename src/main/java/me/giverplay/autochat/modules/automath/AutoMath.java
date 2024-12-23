package me.giverplay.autochat.modules.automath;

import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoMath extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("Quanto (.+)? (\\d+) ([+\\-*/xX]) (\\d+)\\?");
  private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();

  public AutoMath(AutoChat addon) {
    super(addon, "AutoMath", PATTERN);
  }

  @Override
  protected void initConfig() {
    this.config = new AutoMathConfig(this);
  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    try {
      ChatUtils.builder("[AutoMath] Parsing expression")
        .color(EnumChatFormatting.GREEN)
        .addToChat();

      int result = evaluateExpression(matcher);

      ChatUtils.builder("[AutoMath] The result is ")
        .color(EnumChatFormatting.GREEN)
        .addChild(ChatUtils.builder(Integer.toString(result))
          .color(EnumChatFormatting.WHITE))
        .addToChat();

      sendChat(result);
    } catch (Exception ignore) {
      ChatUtils.builder("[AutoMath] Failed to parse expression")
        .color(EnumChatFormatting.RED)
        .addToChat();
    }
  }

  @Override
  public AutoMathConfig getConfig() {
    return (AutoMathConfig) super.getConfig();
  }

  private void sendChat(final int result) {
    final String toSend = Integer.toString(result);

    int sleep = getConfig().getBaseReplyTime() + new Random().nextInt(getConfig().getRandomTimeRange());
    int decrease = getConfig().getEasyDecreaseTime();

    if (result < 1000) sleep -= decrease;
    if (result < 100) sleep -= decrease;

    final int sleepTime = Math.max(sleep, 0);

    ChatUtils.builder("[AutoMath] Sending result in ")
      .addChild(ChatUtils.builder(Integer.toString(sleepTime / 1000)))
      .addChild(ChatUtils.builder("s"))
      .color(EnumChatFormatting.GREEN)
      .addToChat();

    ThreadUtils.delayed(() -> ChatUtils.sendChat(toSend), sleepTime);
  }

  private int evaluateExpression(Matcher matcher) {
    String op = matcher.group(3);
    int a = Integer.parseInt(matcher.group(2));
    int b = Integer.parseInt(matcher.group(4));

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
