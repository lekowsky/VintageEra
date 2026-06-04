package pl.skyrise.skyRiseCrime.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoreTabCompleter implements TabCompleter {

    private final TabRegistry tabRegistry;

    public CoreTabCompleter(TabRegistry tabRegistry) {
        this.tabRegistry = tabRegistry;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestions = tabRegistry.getArguments(command.getName());
        
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            // Jeśli to główna komenda, dodaj domyślne opcje
            if (command.getName().equalsIgnoreCase("skyrisecrime")) {
                List<String> coreOptions = new ArrayList<>();
                coreOptions.add("reload");
                coreOptions.add("list");
                coreOptions.add("enable");
                coreOptions.add("disable");
                return coreOptions.stream().filter(s -> s.startsWith(input)).collect(Collectors.toList());
            }
            return suggestions.stream().filter(s -> s.startsWith(input)).collect(Collectors.toList());
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable"))) {
            String input = args[1].toLowerCase();
            return pl.skyrise.skyRiseCrime.SkyRiseCrime.getInstance().getModuleManager().getAllModules().keySet()
                    .stream().filter(s -> s.startsWith(input)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
