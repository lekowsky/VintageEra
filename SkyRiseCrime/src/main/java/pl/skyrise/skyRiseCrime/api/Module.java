package pl.skyrise.skyRiseCrime.api;

public interface Module {

    /**
     * Unikalna nazwa modułu, np. "IllegalDrugs"
     */
    String getName();

    /**
     * Wywoływane przy starcie pluginu.
     * Tu rejestrujesz listenery, komendy, ładujesz config.
     */
    void onEnable();

    /**
     * Wywoływane przy wyłączaniu pluginu.
     * Tu zapisujesz dane, wyrejestrowujesz zasoby.
     */
    void onDisable();

    /**
     * Przeładowuje config modułu bez wyłączania go.
     */
    default void onReload() {}
}
