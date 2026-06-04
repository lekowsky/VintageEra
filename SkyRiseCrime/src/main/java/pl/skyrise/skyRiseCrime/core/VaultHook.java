package pl.skyrise.skyRiseCrime.core;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private static Chat chat = null;
    private static boolean enabled = false;

    public static void setup() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            enabled = false;
            return;
        }
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
        }
        enabled = (chat != null);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static Chat getChat() {
        return chat;
    }
}
