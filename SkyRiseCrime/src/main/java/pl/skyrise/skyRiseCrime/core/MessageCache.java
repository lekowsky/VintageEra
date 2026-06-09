package pl.skyrise.skyRiseCrime.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageCache implements Listener {

    private final Map<UUID, String> lastMessages;
    private final long cacheDurationMillis;

    public MessageCache(long cacheDurationMillis) {
        this.lastMessages = new ConcurrentHashMap<>();
        this.cacheDurationMillis = cacheDurationMillis;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        lastMessages.put(event.getPlayer().getUniqueId(), event.getMessage());
        // W prawdziwym systemie można by czyścić stare wpisy
    }

    public String getLastMessage(UUID uuid) {
        return lastMessages.get(uuid);
    }
}
