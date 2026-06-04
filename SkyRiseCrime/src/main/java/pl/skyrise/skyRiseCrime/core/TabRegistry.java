package pl.skyrise.skyRiseCrime.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TabRegistry {

    private final Map<String, List<String>> commandArguments;

    public TabRegistry() {
        this.commandArguments = new HashMap<>();
    }

    public void registerCommand(String command, List<String> arguments) {
        commandArguments.put(command.toLowerCase(), arguments);
    }

    public List<String> getArguments(String command) {
        return commandArguments.getOrDefault(command.toLowerCase(), new ArrayList<>());
    }

    public Set<String> getRegisteredCommands() {
        return commandArguments.keySet();
    }
}
