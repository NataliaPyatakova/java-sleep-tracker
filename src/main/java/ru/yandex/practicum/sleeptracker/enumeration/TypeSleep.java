package ru.yandex.practicum.sleeptracker.enumeration;

public enum TypeSleep {
    OWL("Сова"),
    LARK("Жаворонок"),
    PIGEON("Голубь");

    private final String displayName;

    TypeSleep(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
