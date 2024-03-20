package me.giverplay.autochat.modules.automath;

import com.google.gson.JsonObject;
import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.utils.ThreadUtils;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoMath {
  private static final Pattern PATTERN = Pattern.compile("\\[Matem(.+)?tica] Quanto (.+)? (\\d+) (.) (\\d+)\\?");
  private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();

  private final AutoChat addon;
  private final AutoMathConfig config;

  public AutoMath(AutoChat addon) {
    this.addon = addon;
    this.config = new AutoMathConfig();
  }

  public void loadConfig(JsonObject config) {
    this.config.loadConfig(config);
  }

  public void fillSettings(List<SettingsElement> settings) {
    this.config.fillSettings(addon, settings);
  }

  @SubscribeEvent
  public void onChatReceived(ClientChatReceivedEvent event) {
    if(!config.isEnabled()) return;

    Matcher matcher = PATTERN.matcher(event.message.getUnformattedText());

    if(!matcher.find()) return;

    try {
      int result = evaluateExpression(matcher);
      sendChat(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void sendChat(final int result) {
    final String toSend = Integer.toString(result);

    int sleep = config.getBaseReplyTime() + new Random().nextInt(config.getRandomTimeRange());

    if(result < 1000) {
      sleep -= config.getEasyDecreaseTime();
    }

    if(result < 100) {
      sleep -= config.getEasyDecreaseTime();
    }

    final int sleepTime = sleep;

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
