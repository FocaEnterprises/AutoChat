package me.giverplay.autochat.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class ClientChatBuilder {
  private ChatStyle style;
  private String text;

  private List<ClientChatBuilder> children;

  public ClientChatBuilder(String text) {
    this.text = text;
    this.style = new ChatStyle();
    this.children = new ArrayList<>();
  }

  public ClientChatBuilder() {
    this(null);
  }

  public ChatStyle getStyle() {
    return style;
  }

  public ClientChatBuilder setStyle(ChatStyle style) {
    this.style = style;
    return this;
  }

  public String getText() {
    return text;
  }

  public ClientChatBuilder setText(String text) {
    this.text = text;
    return this;
  }

  public ClientChatBuilder setChildren(List<ClientChatBuilder> children) {
    this.children = children;
    return this;
  }

  public List<ClientChatBuilder> getChildren() {
    return children;
  }

  public ClientChatBuilder addChild(ClientChatBuilder child) {
    this.children.add(child);
    return this;
  }

  public ClientChatBuilder removeChild(ClientChatBuilder child) {
    this.children.remove(child);
    return this;
  }

  public ClientChatBuilder bold() {
    this.style.setBold(true);
    return this;
  }

  public ClientChatBuilder underlined() {
    this.style.setUnderlined(true);
    return this;
  }

  public ClientChatBuilder strikethrough() {
    this.style.setStrikethrough(true);
    return this;
  }

  public ClientChatBuilder obfuscated() {
    this.style.setObfuscated(true);
    return this;
  }

  public ClientChatBuilder color(EnumChatFormatting color) {
    if(color.isColor()) this.style.setColor(color);
    return this;
  }

  public IChatComponent build() {
    IChatComponent component = new ChatComponentText(text);
    component.setChatStyle(style);
    children.stream().map(ClientChatBuilder::build).forEach(component::appendSibling);
    return component;
  }

  public void addToChat() {
    IChatComponent component = build();
    ChatUtils.addChat(component);
  }
}
