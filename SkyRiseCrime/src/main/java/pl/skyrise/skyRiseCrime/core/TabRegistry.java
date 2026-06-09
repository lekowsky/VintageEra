package pl.skyrise.skyRiseCrime.core;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TabRegistry {

    private final Map<String, TabProvider> providers = new HashMap<>();

    public void register(String commandLabel, TabProvider provider) {
        providers.put(commandLabel.toLowerCase(), provider);
    }

    public void unregister(String commandLabel) {
        providers.remove(commandLabel.toLowerCase());
    }

    public List<String> complete(String commandLabel, CommandSender sender, String[] args) {
        TabProvider provider = providers.get(commandLabel.toLowerCase());
        if (provider != null) {
            return provider.provide(sender, args);
        }
        return Collections.emptyList();
    }

    public Set<String> getRegisteredCommands() {
        return providers.keySet();
    }

    public static List<String> filter(Iterable<String> source, String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        java.util.stream.Stream<String> stream = java.util.stream.StreamSupport.stream(source.spliterator(), false);
        return stream
                .filter(s -> s.toLowerCase().startsWith(lowerPrefix))
                .collect(Collectors.toList());
    }

    @FunctionalInterface
    public interface TabProvider {
        List<String> provide(CommandSender sender, String[] args);
    }
}
