package pl.skyrise.skyRiseCrime.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.skyrise.skyRiseCrime.SkyRiseCrime;
import pl.skyrise.skyRiseCrime.utils.ColorUtil;

public class SkyRiseCrimeCommand implements CommandExecutor {

    private final SkyRiseCrime plugin;
    private final TabRegistry tabRegistry;

    public SkyRiseCrimeCommand(SkyRiseCrime plugin, TabRegistry tabRegistry) {
        this.plugin = plugin;
        this.tabRegistry = tabRegistry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyrisecrime.admin")) {
            sender.sendMessage(ColorUtil.fixColor("&cNie masz uprawnień!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtil.fixColor("&8&m-------&r &6SkyRiseCrime &8&m-------"));
            sender.sendMessage(ColorUtil.fixColor("&7Wersja: &f" + plugin.getDescription().getVersion()));
            sender.sendMessage(ColorUtil.fixColor("&7Załadowane moduły: &f" + plugin.getModuleManager().getModuleCount()));
            sender.sendMessage(ColorUtil.fixColor("&6/crime reload &8- &7Przeładowuje wszystkie moduły"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getModuleManager().getAllModules().values().forEach(m -> {
                if (plugin.getModuleManager().isEnabled(m.getName())) {
                    m.onReload();
                }
            });
            sender.sendMessage(ColorUtil.fixColor("&aPomyślnie przeładowano aktywne moduły!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ColorUtil.fixColor("&8&m-------&r &6Lista Modułów &8&m-------"));
            plugin.getModuleManager().getAllModules().values().forEach(m -> {
                String status = plugin.getModuleManager().isEnabled(m.getName()) ? "&a[WŁĄCZONY]" : "&c[WYŁĄCZONY]";
                sender.sendMessage(ColorUtil.fixColor("&7- &f" + m.getName() + " " + status));
            });
            return true;
        }

        if (args.length < 2) return false;

        String moduleName = args[1];
        if (args[0].equalsIgnoreCase("enable")) {
            if (plugin.getModuleManager().isEnabled(moduleName)) {
                sender.sendMessage(ColorUtil.fixColor("&cTen moduł jest już włączony!"));
                return true;
            }
            plugin.getModuleManager().enableModule(moduleName);
            sender.sendMessage(ColorUtil.fixColor("&aWłączono moduł: &f" + moduleName));
            return true;
        }

        if (args[0].equalsIgnoreCase("disable")) {
            if (!plugin.getModuleManager().isEnabled(moduleName)) {
                sender.sendMessage(ColorUtil.fixColor("&cTen moduł jest już wyłączony!"));
                return true;
            }
            plugin.getModuleManager().disableModule(moduleName);
            sender.sendMessage(ColorUtil.fixColor("&cWyłączono moduł: &f" + moduleName));
            return true;
        }

        return false;
    }
}
