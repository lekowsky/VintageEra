package pl.skyrise.skyRiseCrime.api;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class ModuleManager {

    private final JavaPlugin plugin;
    private final Map<String, Module> activeModules;
    private final Map<String, Module> allModules;

    public ModuleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.activeModules = new LinkedHashMap<>();
        this.allModules = new LinkedHashMap<>();
    }

    /**
     * Rejestruje moduł w systemie (ale jeszcze go nie włącza).
     */
    public void register(Module module) {
        allModules.put(module.getName().toLowerCase(), module);
    }

    /**
     * Włącza zarejestrowany moduł.
     */
    public void enableModule(String name) {
        Module module = allModules.get(name.toLowerCase());
        if (module != null && !activeModules.containsKey(name.toLowerCase())) {
            try {
                module.onEnable();
                activeModules.put(name.toLowerCase(), module);
                plugin.getLogger().info("  ✔ Moduł " + module.getName() + " włączony.");
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "  ✘ Błąd podczas włączania modułu " + module.getName(), e);
            }
        }
    }

    /**
     * Wyłącza moduł.
     */
    public void disableModule(String name) {
        Module module = activeModules.remove(name.toLowerCase());
        if (module != null) {
            try {
                module.onDisable();
                plugin.getLogger().info("  ✘ Moduł " + module.getName() + " wyłączony.");
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Błąd podczas wyłączania modułu " + module.getName(), e);
            }
        }
    }

    public void disableAll() {
        for (Module module : new java.util.ArrayList<>(activeModules.values())) {
            disableModule(module.getName());
        }
    }

    public Map<String, Module> getAllModules() {
        return Collections.unmodifiableMap(allModules);
    }

    public boolean isEnabled(String name) {
        return activeModules.containsKey(name.toLowerCase());
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(String name) {
        return (T) activeModules.get(name.toLowerCase());
    }

    public int getModuleCount() {
        return activeModules.size();
    }
}
