package me.giverplay.autochat.modules.autolottery;

import me.giverplay.autochat.AutoChat;
import me.giverplay.autochat.modules.ChatModule;
import me.giverplay.autochat.utils.ChatUtils;
import me.giverplay.autochat.utils.ThreadUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoLottery extends ChatModule {
  private static final Pattern PATTERN = Pattern.compile("\\[Loteria]");
  private static final String WINNER = "O vencedor foi";
  private static final String OPEN = "Chamadas restantes";

  private final Set<Integer> entries = new HashSet<>();
  private volatile boolean isOpen;
  private volatile boolean isLooping;

  public AutoLottery(AutoChat addon) {
    super(addon, "AutoLottery", PATTERN);
  }

  @Override
  protected void initConfig() {

  }

  @Override
  public void onChat(IChatComponent message, Matcher matcher) {
    String text = message.getUnformattedText();

    if(!isOpen && text.contains(OPEN)) {
      isOpen = true;
    }

    if(text.contains(WINNER)) {
      ChatUtils.builder("[AutoLottery] Event finished")
        .color(EnumChatFormatting.YELLOW)
        .addToChat();

      isOpen = false;
      isLooping = false;
      entries.clear();
    }

    if(isOpen && !isLooping) {
      isLooping = true;
      ThreadUtils.execute(this::startResponding);
    }
  }

  private void startResponding() {
    ChatUtils.builder("[AutoLottery] Sending random entries")
      .color(EnumChatFormatting.GREEN)
      .addToChat();

    Random random = new Random();
    entries.clear();

    while(isLooping) {
      int entry = random.nextInt(101);

      while(entries.contains(entry)) {
        entry = random.nextInt(101);
      }

      entries.add(entry);

      if(!isOpen) {
        isLooping = false;
        break;
      }

      ThreadUtils.sleep(800);
      ChatUtils.sendChat("/loteria " + entry);
    }
  }
}
