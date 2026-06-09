package pl.skyrise.skyRiseCrime.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String fixColor(String message) {
        if (message == null) return "";
        
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String color = matcher.group(1);
            matcher.appendReplacement(sb, ChatColor.of("#" + color).toString());
        }
        matcher.appendTail(sb);
        
        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }

    public static Component mini(String message) {
        if (message == null) return Component.empty();
        return MiniMessage.miniMessage().deserialize(message);
    }
}
