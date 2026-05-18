package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {

    private final String description;
    private final int result;

    public SleepAnalysisResult(String description, int result) {
        this.description = description;
        this.result = result;
    }

    @Override
    public String toString() {
        return description + ": "+ result;
    }
}
