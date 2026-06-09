package pl.skyrise.skyRiseCrime.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.skyrise.skyRiseCrime.SkyRiseCrime;
import pl.skyrise.skyRiseCrime.api.ModuleManager;
import pl.skyrise.skyRiseCrime.utils.ColorUtil;

import java.util.List;
import java.util.Set;

public class SkyRiseCrimeCommand implements CommandExecutor {

    private final SkyRiseCrime plugin;
    private final TabRegistry tabRegistry;

    public SkyRiseCrimeCommand(SkyRiseCrime plugin, TabRegistry tabRegistry) {
        this.plugin = plugin;
        this.tabRegistry = tabRegistry;

        tabRegistry.register("skyrisecrime", (sender, args) -> {
            if (args.length == 1) {
                return TabRegistry.filter(List.of("reload", "enable", "disable", "list", "version"), args[0]);
            }
            if (args.length == 2 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable"))) {
                return TabRegistry.filter(plugin.getModuleManager().getModuleNames(), args[1]);
            }
            return List.of();
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendInfo(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> handleReload(sender, args);
            case "enable" -> handleEnable(sender, args);
            case "disable" -> handleDisable(sender, args);
            case "list" -> handleList(sender);
            case "version" -> sendInfo(sender);
            default -> sendHelp(sender);
        }
        return true;
    }

    private void handleEnable(CommandSender sender, String[] args) {
        ModuleManager mm = plugin.getModuleManager();

        if (args.length == 1) {
            sender.sendMessage(ColorUtil.mini("<#99CCFF>» Użycie: /crime enable <moduł>"));
            return;
        }

        String moduleName = args[1].toLowerCase();
        if (!mm.getModuleNames().contains(moduleName)) {
            sender.sendMessage(ColorUtil.mini("<red>» Moduł <gold>" + moduleName + "</gold> nie istnieje."));
            return;
        }

        if (mm.enable(moduleName)) {
            sender.sendMessage(ColorUtil.mini("<green>» Moduł <gold>" + moduleName + "</gold> włączony."));
        } else {
            sender.sendMessage(ColorUtil.mini("<red>» Moduł <gold>" + moduleName + "</gold> jest już włączony lub wystąpił błąd."));
        }
    }

    private void handleDisable(CommandSender sender, String[] args) {
        ModuleManager mm = plugin.getModuleManager();

        if (args.length == 1) {
            sender.sendMessage(ColorUtil.mini("<#99CCFF>» Użycie: /crime disable <moduł>"));
            return;
        }

        String moduleName = args[1].toLowerCase();
        if (!mm.getModuleNames().contains(moduleName)) {
            sender.sendMessage(ColorUtil.mini("<red>» Moduł <gold>" + moduleName + "</gold> nie istnieje."));
            return;
        }

        if (mm.disable(moduleName)) {
            sender.sendMessage(ColorUtil.mini("<green>» Moduł <gold>" + moduleName + "</gold> wyłączony."));
        } else {
            sender.sendMessage(ColorUtil.mini("<red>» Moduł <gold>" + moduleName + "</gold> jest już wyłączony lub wystąpił błąd."));
        }
    }

    private void handleReload(CommandSender sender, String[] args) {
        ModuleManager mm = plugin.getModuleManager();

        if (args.length == 1) {
            mm.reloadAll();
            sender.sendMessage(ColorUtil.mini("<green>» Przeładowano wszystkie moduły <dark_gray>(<white>" + mm.getEnabledModuleCount() + "<dark_gray>)</dark_gray>."));
            return;
        }

        String moduleName = args[1].toLowerCase();
        if (mm.reload(moduleName)) {
            sender.sendMessage(ColorUtil.mini("<green>» Moduł <gold>" + moduleName + "</gold> przeładowany."));
        } else {
            sender.sendMessage(ColorUtil.mini("<red>» Moduł <gold>" + moduleName + "</gold> nie istnieje lub jest wyłączony."));
            sender.sendMessage(ColorUtil.mini("<#99CCFF>» Dostępne: <white>" + String.join(", ", mm.getModuleNames())));
        }
    }

    private void handleList(CommandSender sender) {
        Set<String> names = plugin.getModuleManager().getModuleNames();
        if (names.isEmpty()) {
            sender.sendMessage(ColorUtil.mini("<red>» Brak załadowanych modułów."));
            return;
        }

        sender.sendMessage(ColorUtil.mini("<gold>Zarejestrowane moduły <dark_gray>(<white>" + names.size() + "<dark_gray>)</dark_gray>:"));
        for (String name : names) {
            if (plugin.getModuleManager().isEnabled(name)) {
                sender.sendMessage(ColorUtil.mini("  <green>✔ <white>" + name));
            } else {
                sender.sendMessage(ColorUtil.mini("  <red>✘ <gray>" + name));
            }
        }
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage(ColorUtil.mini("<dark_gray><strikethrough>                              "));
        sender.sendMessage(ColorUtil.mini("<gold><bold>SkyRiseCrime <dark_gray>- <#99CCFF>v" + plugin.getDescription().getVersion()));
        sender.sendMessage(ColorUtil.mini("  <#99CCFF>Moduły: <white>" + plugin.getModuleManager().getModuleCount()));
        sender.sendMessage(ColorUtil.mini("  <#99CCFF>Włączone: <green>" + plugin.getModuleManager().getEnabledModuleCount()));
        sender.sendMessage(ColorUtil.mini("  <#99CCFF>Autor: <white>" + String.join(", ", plugin.getDescription().getAuthors())));
        sender.sendMessage(ColorUtil.mini("<dark_gray><strikethrough>                              "));
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ColorUtil.mini("<dark_gray><strikethrough>                              "));
        sender.sendMessage(ColorUtil.mini("<gold><bold>SkyRiseCrime <dark_gray>- <#99CCFF>Pomoc"));
        sender.sendMessage(ColorUtil.mini("<dark_gray><strikethrough>                              "));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime list              <dark_gray>- <#99CCFF>Lista modułów"));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime enable <moduł>    <dark_gray>- <#99CCFF>Włącz moduł"));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime disable <moduł>   <dark_gray>- <#99CCFF>Wyłącz moduł"));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime reload            <dark_gray>- <#99CCFF>Przeładuj wszystko"));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime reload <moduł>    <dark_gray>- <#99CCFF>Przeładuj moduł"));
        sender.sendMessage(ColorUtil.mini("  <yellow>/crime version           <dark_gray>- <#99CCFF>Informacje"));
        sender.sendMessage(ColorUtil.mini("<dark_gray><strikethrough>                              "));
    }
}
