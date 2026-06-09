package pl.skyrise.skyRiseCrime.api;

public interface Module {
    String getName();
    void onEnable();
    void onDisable();
    default void onReload() {}
}
