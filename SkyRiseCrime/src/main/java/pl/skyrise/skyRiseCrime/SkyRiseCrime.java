package pl.skyrise.skyRiseCrime;

import org.bukkit.plugin.java.JavaPlugin;
import pl.skyrise.skyRiseCrime.api.ModuleManager;
import pl.skyrise.skyRiseCrime.core.CoreTabCompleter;
import pl.skyrise.skyRiseCrime.core.MessageCache;
import pl.skyrise.skyRiseCrime.core.SkyRiseCrimeCommand;
import pl.skyrise.skyRiseCrime.core.TabRegistry;
import pl.skyrise.skyRiseCrime.core.VaultHook;
import pl.skyrise.skyRiseCrime.gui.GuiListener;

import java.util.Objects;

public class SkyRiseCrime extends JavaPlugin {

    private static SkyRiseCrime instance;
    private ModuleManager moduleManager;
    private TabRegistry tabRegistry;
    private MessageCache messageCache;

    @Override
    public void onEnable() {
        instance = this;
        
        // Inicjalizacja managerów
        this.moduleManager = new ModuleManager(this);
        this.tabRegistry = new TabRegistry();
        this.messageCache = new MessageCache(2000);

        // Integracja z Vault
        VaultHook.setup();
        if (VaultHook.isEnabled()) {
            getLogger().info("✔ Vault połączony pomyślnie.");
        } else {
            getLogger().warning("✘ Vault nie został znaleziony. Niektóre funkcje mogą być ograniczone.");
        }

        // Rejestracja systemów Core
        getServer().getPluginManager().registerEvents(messageCache, this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);

        // Rejestracja głównej komendy
        Objects.requireNonNull(getCommand("skyrisecrime")).setExecutor(new SkyRiseCrimeCommand(this, tabRegistry));

        // Rejestruj moduły tutaj, np.:
        // moduleManager.register(new DrugsModule(this));
        
        // Konfiguracja TabCompletera
        CoreTabCompleter completer = new CoreTabCompleter(tabRegistry);
        Objects.requireNonNull(getCommand("skyrisecrime")).setTabCompleter(completer);
        
        for (String cmd : tabRegistry.getRegisteredCommands()) {
            if (getCommand(cmd) != null) {
                Objects.requireNonNull(getCommand(cmd)).setTabCompleter(completer);
            }
        }

        getLogger().info("========================================");
        getLogger().info(" SkyRiseCrime został pomyślnie włączony!");
        getLogger().info(" Załadowano " + moduleManager.getModuleCount() + " modułów.");
        getLogger().info("========================================");
    }

    @Override
    public void onDisable() {
        if (moduleManager != null) {
            moduleManager.disableAll();
        }
        getLogger().info("SkyRiseCrime został wyłączony.");
    }

    public static SkyRiseCrime getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public TabRegistry getTabRegistry() {
        return tabRegistry;
    }

    public MessageCache getMessageCache() {
        return messageCache;
    }
}
